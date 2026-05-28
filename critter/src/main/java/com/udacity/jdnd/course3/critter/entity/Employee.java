package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.util.EnumSet;
import java.util.Set;

/**
 * Entity class representing an employee.
 */
@Entity
@Table(name = "employee")
@NamedQueries({
    @NamedQuery(
        name = "Employee.findAll",
        query = "SELECT e FROM Employee e ORDER BY e.id"
    ),
    @NamedQuery(
        name = "Employee.availableOn",
        query = "SELECT e FROM Employee e " +
                "WHERE :dayOfWeek MEMBER OF e.daysAvailable ORDER BY e.id"
    )
})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 600)
    private String name;

    // Employee skills
    @ElementCollection(targetClass = EmployeeSkill.class,
            fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "employee_skill",
            joinColumns = @JoinColumn(name = "employee_id")
    )
    @Column(name = "skill", nullable = false, length = 40)
    private Set<EmployeeSkill> skills =
            EnumSet.noneOf(EmployeeSkill.class);

    // Employee availability
    @ElementCollection(targetClass = DayOfWeek.class,
            fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "employee_availability",
            joinColumns = @JoinColumn(name = "employee_id")
    )
    @Column(name = "day_of_week", nullable = false, length = 15)
    private Set<DayOfWeek> daysAvailable =
            EnumSet.noneOf(DayOfWeek.class);

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

    public Set<EmployeeSkill> getSkills() {
        return skills;
    }

    public void setSkills(Set<EmployeeSkill> skills) {
        this.skills = skills;
    }

    public Set<DayOfWeek> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayOfWeek> days) {
        this.daysAvailable = days;
    }
}