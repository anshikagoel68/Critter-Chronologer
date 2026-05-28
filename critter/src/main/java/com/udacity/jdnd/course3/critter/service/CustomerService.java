package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for Customer operations.
 */
@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepo;
    private final PetRepository petRepo;

    public CustomerService(CustomerRepository customerRepo, PetRepository petRepo) {

        this.customerRepo = customerRepo;
        this.petRepo = petRepo;
    }

    // Save customer
    public Customer registerCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    // Get all customers
    @Transactional(readOnly = true)
    public List<Customer> listAllCustomers() {
        return customerRepo.findAll();
    }

    // Get customer by pet ID
    @Transactional(readOnly = true)
    public Customer fetchOwnerByPetId(long petId) {

        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", petId));

        return pet.getOwner();
    }

    // Get customer by ID
    @Transactional(readOnly = true)
    public Customer fetchById(long customerId) {

        return customerRepo.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                                "Customer",
                                customerId
                        ));
    }
}