package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "vacations")
@Getter
@Setter

// Vacation table used to identify the vacation type of customer
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vacation_id")
    private Long id;

    @Column(name = "vacation_title")
    private String vacation_title;

    @Column(name = "description")
    private String description;

    @Column(name = "travel_fare_price")
    private BigDecimal travel_price;

    @Column(name = "image_url")
    private String image_URL;

    @Column(name = "create_date")
    @CreationTimestamp
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;

    // Relationship fields
    // One vacation can have many excursions
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vacation", fetch = FetchType.LAZY)
    private Set<Excursion> excursions;

    // One vacation can be in many carts
    @OneToMany(mappedBy = "vacation", fetch = FetchType.LAZY)
    private Set<CartItem> cartItems;
}
