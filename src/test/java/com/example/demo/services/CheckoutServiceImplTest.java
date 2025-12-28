package com.example.demo.services;

import com.example.demo.dao.*;
import com.example.demo.dto.*;
import com.example.demo.entities.Country;
import com.example.demo.entities.Division;
import com.example.demo.entities.Excursion;
import com.example.demo.entities.Vacation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CheckoutServiceImplTest {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private DivisionRepository divisionRepository;

    @Autowired
    private VacationRepository vacationRepository;

    @Autowired
    private ExcursionRepository excursionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Test
    void reusesExistingCustomerByEmail() {
        Country country = createCountry("U.S");
        Division division = createDivision(country, "California");

        Vacation vacation = createVacation("Test Vacation", new BigDecimal("100.00"));
        Excursion excursion = createExcursion(vacation, "Test Excursion", new BigDecimal("25.00"));

        String email = "repeat.customer@example.com";

        PurchaseResponse first = checkoutService.placeOrder(buildPurchase(
                division,
                country.getId(),
                vacation,
                List.of(excursion),
                email,
                "123 First St",
                "Seattle"
        ));

        assertThat(first.isSuccess()).isTrue();
        assertThat(first.getOrderTrackingNumber()).isNotBlank();
        assertThat(customerRepository.count()).isEqualTo(1);
        assertThat(cartRepository.count()).isEqualTo(1);

        PurchaseResponse second = checkoutService.placeOrder(buildPurchase(
                division,
                country.getId(),
                vacation,
                List.of(excursion),
                email,
                "999 Updated Ave",
                "Portland"
        ));

        assertThat(second.isSuccess()).isTrue();
        assertThat(customerRepository.count()).isEqualTo(1);
        assertThat(cartRepository.count()).isEqualTo(2);

        var customer = customerRepository.findByEmailIgnoreCase(email).orElseThrow();
        assertThat(customer.getAddress()).isEqualTo("999 Updated Ave");
        assertThat(customer.getCity()).isEqualTo("Portland");
    }

    @Test
    void rejectsDivisionCountryMismatch() {
        Country us = createCountry("U.S");
        Country canada = createCountry("Canada");
        Division division = createDivision(us, "Alaska");

        Vacation vacation = createVacation("Test Vacation", new BigDecimal("100.00"));

        PurchaseResponse response = checkoutService.placeOrder(buildPurchase(
                division,
                canada.getId(),
                vacation,
                List.of(),
                "mismatch@example.com",
                "123 First St",
                "Seattle"
        ));

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getErrorCode()).isEqualTo("DIVISION_COUNTRY_MISMATCH");
        assertThat(customerRepository.count()).isZero();
        assertThat(cartRepository.count()).isZero();
    }

    @Test
    void rejectsExcursionVacationMismatch() {
        Country country = createCountry("U.S");
        Division division = createDivision(country, "Colorado");

        Vacation vacationA = createVacation("Vacation A", new BigDecimal("100.00"));
        Vacation vacationB = createVacation("Vacation B", new BigDecimal("100.00"));
        Excursion excursionB = createExcursion(vacationB, "Excursion B", new BigDecimal("25.00"));

        PurchaseResponse response = checkoutService.placeOrder(buildPurchase(
                division,
                country.getId(),
                vacationA,
                List.of(excursionB),
                "excursion-mismatch@example.com",
                "123 First St",
                "Seattle"
        ));

        assertThat(response.isSuccess()).isFalse();
        assertThat(response.getErrorCode()).isEqualTo("EXCURSION_VACATION_MISMATCH");
        assertThat(customerRepository.count()).isZero();
        assertThat(cartRepository.count()).isZero();
    }

    private Country createCountry(String name) {
        Country country = new Country();
        country.setCountry_name(name);
        return countryRepository.save(country);
    }

    private Division createDivision(Country country, String divisionName) {
        Division division = new Division();
        division.setDivision_name(divisionName);
        division.setCountry(country);
        return divisionRepository.save(division);
    }

    private Vacation createVacation(String title, BigDecimal travelPrice) {
        Vacation vacation = new Vacation();
        vacation.setVacation_title(title);
        vacation.setDescription("Test description");
        vacation.setTravel_price(travelPrice);
        vacation.setImage_URL("https://example.com/vacation.jpg");
        return vacationRepository.save(vacation);
    }

    private Excursion createExcursion(Vacation vacation, String title, BigDecimal price) {
        Excursion excursion = new Excursion();
        excursion.setVacation(vacation);
        excursion.setExcursion_title(title);
        excursion.setExcursion_price(price);
        excursion.setImage_URL("https://example.com/excursion.jpg");
        return excursionRepository.save(excursion);
    }

    private PurchaseDTO buildPurchase(
            Division division,
            Long expectedCountryId,
            Vacation vacation,
            List<Excursion> excursions,
            String email,
            String address,
            String city
    ) {
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Test");
        customer.setLastName("User");
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setPostal_code("12345");
        customer.setPhone("1234567890");

        DivisionDTO divisionDTO = new DivisionDTO();
        divisionDTO.setId(division.getId());
        divisionDTO.setCountry_id(expectedCountryId);
        divisionDTO.setDivision_name(division.getDivision_name());
        customer.setDivision(divisionDTO);

        CartDTO cart = new CartDTO();
        cart.setParty_size(1);
        cart.setStatus("ordered");

        BigDecimal expectedTotal = vacation.getTravel_price();
        for (Excursion excursion : excursions) {
            expectedTotal = expectedTotal.add(excursion.getExcursion_price());
        }
        cart.setPackage_price(expectedTotal);

        CartItemDTO cartItem = new CartItemDTO();
        VacationRefDTO vacationRef = new VacationRefDTO();
        vacationRef.setId(vacation.getId());
        cartItem.setVacation(vacationRef);

        cartItem.setExcursions(excursions.stream().map(excursion -> {
            ExcursionRefDTO ref = new ExcursionRefDTO();
            ref.setId(excursion.getId());
            return ref;
        }).toList());

        PurchaseDTO purchase = new PurchaseDTO();
        purchase.setCustomer(customer);
        purchase.setCart(cart);
        purchase.setCartItems(List.of(cartItem));

        return purchase;
    }
}

