package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Customer entity.
 */
@Repository
public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    // CRUD methods inherited from JpaRepository
}