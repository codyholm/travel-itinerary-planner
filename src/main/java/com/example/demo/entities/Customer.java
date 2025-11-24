package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@Getter
@Setter

// Customer table used to store customer information
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;

    // Required fields for customer are not nullable
    @Column(name = "customer_first_name", nullable = false)
    private String firstName;

    @Column(name = "customer_last_name", nullable = false)
    private String lastName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "postal_code", nullable = false)
    private String postal_code;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "create_date", updatable = false)
    @CreationTimestamp
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;

    // Relationship fields
    // Many customers can belong to one division
    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    // One customer can have many carts
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Cart> carts;

    // Helper method to add cart to customer
    public void add(Cart cart) {
        if (cart != null) {
            if (carts == null) {
                carts = new HashSet<>();
            }
            carts.add(cart);
            cart.setCustomer(this);
        }
    }
}
