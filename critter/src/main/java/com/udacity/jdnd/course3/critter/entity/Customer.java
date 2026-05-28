package com.udacity.jdnd.course3.critter.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer entity representing a pet owner.
 */
@Entity
@Table(name = "customer")
@NamedQueries({
    @NamedQuery(
        name = "Customer.findAll",
        query = "SELECT c FROM Customer c ORDER BY c.id"
    )
})
public class Customer {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Customer name
    @Column(nullable = false, length = 600)
    private String name;

    // Contact number
    @Column(name = "phone_number", length = 30)
    private String phoneNumber;

    // Additional customer notes
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // One customer can own many pets
    @OneToMany(
        mappedBy = "owner",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true
    )
    private List<Pet> pets = new ArrayList<>();

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    // Helper method to link pet with customer
    public void enrollPet(Pet pet) {
        pets.add(pet);
        pet.setOwner(this);
    }
}