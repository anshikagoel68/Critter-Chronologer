package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.mapping.DataMapper;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for Pet operations.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    // Create a new pet
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        Pet incoming = DataMapper.fromPetDTO(petDTO);

        Pet saved = petService.registerPet(
                incoming,
                petDTO.getOwnerId()
        );

        return DataMapper.toPetDTO(saved);
    }

    // Get pet by ID
    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        return DataMapper.toPetDTO(
                petService.fetchById(petId)
        );
    }

    // Get all pets
    @GetMapping
    public List<PetDTO> getPets() {

        return petService.listAll().stream()
                .map(DataMapper::toPetDTO)
                .collect(Collectors.toList());
    }

    // Get pets by owner ID
    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return petService.listByOwner(ownerId).stream()
                .map(DataMapper::toPetDTO)
                .collect(Collectors.toList());
    }
}