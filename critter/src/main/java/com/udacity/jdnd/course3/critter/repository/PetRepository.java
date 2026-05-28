package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Pet entity.
 */
@Repository
public interface PetRepository
        extends JpaRepository<Pet, Long> {

    // Get pets by owner entity
    @Query(name = "Pet.findByOwner")
    List<Pet> listByOwner(
            @Param("owner") Customer owner
    );

    // Get pets by owner ID
    @Query(name = "Pet.findByOwnerId")
    List<Pet> listByOwnerId(
            @Param("ownerId") long ownerId
    );
}