package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter
@Setter

// Cart table used to store cart information
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;

    @Column(name = "package_price")
    private BigDecimal package_price;

    @Column(name = "party_size")
    private Integer party_size;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusType status;

    @Column(name = "order_tracking_number")
    private String orderTrackingNumber;

    @Column(name = "create_date", updatable = false)
    @CreationTimestamp
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;


    // Relationship fields
    // Many carts can belong to one customer
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // One cart can have many cart items
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart", orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    // Helper method to add cart item to cart
    public void add(CartItem item) {
        if (item != null) {
            if (cartItems == null) {
                cartItems = new HashSet<>();
            }
            cartItems.add(item);
            item.setCart(this);
        }
    }

}