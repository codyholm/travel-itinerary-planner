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
@Table(name = "divisions")
@Getter
@Setter

// Division table used to group customers into divisions
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Long id;

    @Column(name = "division")
    private String division_name;

    @Column(name = "create_date", updatable = false)
    @CreationTimestamp
    private Date create_date;

    @Column(name = "last_update")
    @UpdateTimestamp
    private Date last_update;

    // Relationship fields
    // Many divisions can belong to one country
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    // One division can belong to many customers
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "division", fetch = FetchType.LAZY)
    private Set<Customer> customers = new HashSet<>();

    @Column(name = "country_id", insertable = false, updatable = false)
    private Long country_id;

    // Helper method to set country_id
    public void setCountry(Country country) {
        this.country = country;
        setCountry_id(country.getId());

    }
}
