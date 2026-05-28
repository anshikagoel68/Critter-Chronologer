package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Entity class representing a pet.
 */
@Entity
@Table(name = "pet")
@NamedQueries({
    @NamedQuery(
        name = "Pet.findByOwner",
        query = "SELECT p FROM Pet p WHERE p.owner = :owner ORDER BY p.id"
    ),
    @NamedQuery(
        name = "Pet.findByOwnerId",
        query = "SELECT p FROM Pet p WHERE p.owner.id = :ownerId ORDER BY p.id"
    )
})
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Pet type
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PetType type;

    // Pet name
    @Column(nullable = false, length = 600)
    private String name;

    // Pet owner
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = true)
    private Customer owner;

    // Pet birth date
    @Column(name = "birth_date")
    private LocalDate birthDate;

    // Additional notes
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}