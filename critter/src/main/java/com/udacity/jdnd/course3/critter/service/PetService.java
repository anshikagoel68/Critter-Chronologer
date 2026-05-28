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
 * Service class for Pet operations.
 */
@Service
@Transactional
public class PetService {

    private final PetRepository petRepo;
    private final CustomerRepository customerRepo;

    public PetService(PetRepository petRepo, CustomerRepository customerRepo) {
        this.petRepo = petRepo;
        this.customerRepo = customerRepo;
    }

    // Save pet and assign owner
    public Pet registerPet(Pet pet, long ownerId) {

        Customer owner = customerRepo.findById(ownerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Customer", ownerId));

        owner.enrollPet(pet);

        Pet saved = petRepo.save(pet);

        customerRepo.save(owner);

        return saved;
    }

    // Get pet by ID
    @Transactional(readOnly = true)
    public Pet fetchById(long petId) {
        return petRepo.findById(petId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Pet", petId));
    }

    // Get all pets
    @Transactional(readOnly = true)
    public List<Pet> listAll() {
        return petRepo.findAll();
    }

    // Get pets by owner ID
    @Transactional(readOnly = true)
    public List<Pet> listByOwner(long ownerId) {
        return petRepo.listByOwnerId(ownerId);
    }
}