package com.example.demo.services;


import com.example.demo.dao.CartItemRepository;
import com.example.demo.dao.CartRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;


//Service implementation for checkout service
@Service
public class CheckoutServiceImpl implements CheckoutService {

    // Declaration of repositories used
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // Constructor to inject repositories
    @Autowired
    public CheckoutServiceImpl(CartRepository cartRepository,
                               CartItemRepository cartItemRepository)
    {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        // Variables for Cart and Customer objects
        Cart cart = purchase.getCart();
        Customer customer = purchase.getCustomer();

        // Adds purchase items to CartItem set.
        Set<CartItem> cartItems = purchase.getCartItems();

        // Validation for customer fields before purchase, returns error message if null.
        if (customer == null) {
            return PurchaseResponse.error("Customer is required.");
        }
        // Checks input for required strings, returns error message if null or empty.
        if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
            return PurchaseResponse.error("First name is required.");
        }
        if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
            return PurchaseResponse.error("Last name is required.");
        }
        if (customer.getAddress() == null || customer.getAddress().trim().isEmpty()) {
            return PurchaseResponse.error("Address is required.");
        }
        if (customer.getPostal_code() == null || customer.getPostal_code().trim().isEmpty()) {
            return PurchaseResponse.error("Postal code is required.");
        }
        if (customer.getPhone() == null || customer.getPhone().trim().isEmpty()) {
            return PurchaseResponse.error("Phone number is required.");
        }

        // Checks if cartItems is null or empty. If so, return error message.
        if (cartItems == null || cartItems.isEmpty() || cart == null) {
            return PurchaseResponse.error("No items in cart. Unable to place order.");
        }

        // Sets cart id to null to prepare for update
        cart.setId(null);

        // Adds cart items to cart object.
        cartItems.forEach(cartItem -> cartItem.setCart(cart));

        // Generate a order tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        // Message for testing purposes - System.out.println(orderTrackingNumber);

        // Sets the order tracking number to cart object
        cart.setOrderTrackingNumber(orderTrackingNumber);
        // Message for testing purposes - System.out.println(cart.getOrderTrackingNumber());

        // Sets the status type of cart to ordered
        cart.setStatus(StatusType.ordered);
        // Message for testing purposes - System.out.println(cart.getStatus());

        // Sets the saved customer object to cart as well as cart items
        cart.setCustomer(customer);
        cart.setCartItems(cartItems);

        // Saves cart to cart repository.
        cartRepository.save(cart);

        // Returns purchase response with order tracking number
        return PurchaseResponse.success(orderTrackingNumber);
    }

    // Helper method to generate an order tracking number
    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}