package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity class representing a schedule.
 */
@Entity
@Table(name = "schedule")
@NamedQueries({
    @NamedQuery(
        name = "Schedule.forEmployee",
        query = "SELECT DISTINCT s FROM Schedule s " +
                "JOIN s.employees e WHERE e = :emp ORDER BY s.id"
    ),
    @NamedQuery(
        name = "Schedule.forPet",
        query = "SELECT DISTINCT s FROM Schedule s " +
                "JOIN s.pets p WHERE p = :pet ORDER BY s.id"
    ),
    @NamedQuery(
        name = "Schedule.forCustomer",
        query = "SELECT DISTINCT s FROM Schedule s " +
                "JOIN s.pets p " +
                "WHERE p.owner.id = :customerId ORDER BY s.id"
    )
})
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Employees assigned to the schedule
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "schedule_employee",
        joinColumns = @JoinColumn(name = "schedule_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> employees = new HashSet<>();

    // Pets assigned to the schedule
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "schedule_pet",
        joinColumns = @JoinColumn(name = "schedule_id"),
        inverseJoinColumns = @JoinColumn(name = "pet_id")
    )
    private Set<Pet> pets = new HashSet<>();

    // Schedule date
    @Column(name = "event_date", nullable = false)
    private LocalDate date;

    // Activities for the schedule
    @ElementCollection(targetClass = EmployeeSkill.class,
            fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
        name = "schedule_activity",
        joinColumns = @JoinColumn(name = "schedule_id")
    )
    @Column(name = "activity", nullable = false, length = 40)
    private Set<EmployeeSkill> activities =
            EnumSet.noneOf(EmployeeSkill.class);

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = new HashSet<>(employees);
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = new HashSet<>(pets);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }
}