package com.example.demo.services;

import com.example.demo.dao.CartRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.dao.ExcursionRepository;
import com.example.demo.dao.VacationRepository;
import com.example.demo.dto.CartItemDTO;
import com.example.demo.dto.PurchaseDTO;
import com.example.demo.entities.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service implementation for checkout operations.
 * Validates division-country linkage, excursion-vacation linkage,
 * computes totals server-side, and provides structured error responses.
 */
@Service
public class CheckoutServiceImpl implements CheckoutService {

    private static final Logger log = LoggerFactory.getLogger(CheckoutServiceImpl.class);

    private final CartRepository cartRepository;
    private final DivisionRepository divisionRepository;
    private final VacationRepository vacationRepository;
    private final ExcursionRepository excursionRepository;

    public CheckoutServiceImpl(
            CartRepository cartRepository,
            DivisionRepository divisionRepository,
            VacationRepository vacationRepository,
            ExcursionRepository excursionRepository) {
        this.cartRepository = cartRepository;
        this.divisionRepository = divisionRepository;
        this.vacationRepository = vacationRepository;
        this.excursionRepository = excursionRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(PurchaseDTO purchase) {
        // Validate division and division-country linkage
        Division division = divisionRepository.findById(purchase.getCustomer().getDivision().getId())
                .orElse(null);
        
        if (division == null) {
            log.warn("Division not found: {}", purchase.getCustomer().getDivision().getId());
            return PurchaseResponse.error("DIVISION_NOT_FOUND", 
                "Division not found. Please select a valid state/province.");
        }

        // Validate division belongs to specified country
        Long expectedCountryId = purchase.getCustomer().getDivision().getCountry_id();
        if (division.getCountry() == null || !division.getCountry().getId().equals(expectedCountryId)) {
            log.warn("Division {} does not belong to country {}", division.getId(), expectedCountryId);
            return PurchaseResponse.error("DIVISION_COUNTRY_MISMATCH",
                "The selected state/province does not belong to the selected country.");
        }

        // Build and validate cart items
        Set<CartItem> cartItems = new HashSet<>();
        BigDecimal serverComputedTotal = BigDecimal.ZERO;

        for (CartItemDTO itemDTO : purchase.getCartItems()) {
            Vacation vacation = vacationRepository.findById(itemDTO.getVacation().getId())
                    .orElse(null);
            
            if (vacation == null) {
                log.warn("Vacation not found: {}", itemDTO.getVacation().getId());
                return PurchaseResponse.error("VACATION_NOT_FOUND",
                    "One or more vacations in your cart are no longer available.");
            }

            // Validate and resolve excursions
            Set<Excursion> excursions = new HashSet<>();
            if (itemDTO.getExcursions() != null && !itemDTO.getExcursions().isEmpty()) {
                Set<Long> excursionIds = itemDTO.getExcursions().stream()
                        .map(ref -> ref.getId())
                        .collect(Collectors.toSet());

                for (Long excursionId : excursionIds) {
                    Excursion excursion = excursionRepository.findById(excursionId)
                            .orElse(null);
                    
                    if (excursion == null) {
                        log.warn("Excursion not found: {}", excursionId);
                        return PurchaseResponse.error("EXCURSION_NOT_FOUND",
                            "One or more excursions are no longer available.");
                    }

                    // Validate excursion belongs to vacation
                    if (excursion.getVacation() == null || 
                        !excursion.getVacation().getId().equals(vacation.getId())) {
                        log.warn("Excursion {} does not belong to vacation {}", 
                            excursionId, vacation.getId());
                        return PurchaseResponse.error("EXCURSION_VACATION_MISMATCH",
                            "Selected excursions must belong to the vacation package.");
                    }

                    excursions.add(excursion);
                }
            }

            // Create cart item
            CartItem cartItem = new CartItem();
            cartItem.setVacation(vacation);
            cartItem.setExcursions(excursions);
            cartItems.add(cartItem);

            // Compute total server-side
            serverComputedTotal = serverComputedTotal.add(calculateItemTotal(vacation, excursions));
        }

        // Validate client-submitted total matches server computation (within tolerance)
        BigDecimal clientTotal = purchase.getCart().getPackage_price();
        if (clientTotal != null && serverComputedTotal.compareTo(clientTotal) != 0) {
            BigDecimal tolerance = new BigDecimal("0.01");
            if (serverComputedTotal.subtract(clientTotal).abs().compareTo(tolerance) > 0) {
                log.warn("Total mismatch - client: {}, server: {}", clientTotal, serverComputedTotal);
                return PurchaseResponse.error("TOTAL_MISMATCH",
                    "Cart total has changed. Please refresh and try again.");
            }
        }

        // Build customer entity
        Customer customer = new Customer();
        customer.setFirstName(purchase.getCustomer().getFirstName());
        customer.setLastName(purchase.getCustomer().getLastName());
        customer.setAddress(purchase.getCustomer().getAddress());
        customer.setPostal_code(purchase.getCustomer().getPostal_code());
        customer.setPhone(purchase.getCustomer().getPhone());
        customer.setDivision(division);

        // Build cart entity
        Cart cart = new Cart();
        cart.setPackage_price(serverComputedTotal);
        cart.setParty_size(purchase.getCart().getParty_size());
        cart.setStatus(StatusType.ordered);
        cart.setOrderTrackingNumber(generateOrderTrackingNumber());
        cart.setCustomer(customer);

        // Link cart items to cart
        cartItems.forEach(item -> item.setCart(cart));
        cart.setCartItems(cartItems);

        // Persist
        cartRepository.save(cart);

        log.info("Order placed successfully: {}", cart.getOrderTrackingNumber());
        return PurchaseResponse.success(cart.getOrderTrackingNumber());
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }

    private BigDecimal calculateItemTotal(Vacation vacation, Set<Excursion> excursions) {
        BigDecimal total = vacation.getTravel_price() != null 
            ? vacation.getTravel_price() 
            : BigDecimal.ZERO;

        if (excursions != null) {
            for (Excursion excursion : excursions) {
                BigDecimal price = excursion.getExcursion_price();
                if (price != null) {
                    total = total.add(price);
                }
            }
        }

        return total;
    }
}