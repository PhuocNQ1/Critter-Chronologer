package com.udacity.jdnd.course3.critter.API;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.PetService;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private static final Logger log = LogManager.getLogger(PetController.class);

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<PetDTO>> getPets() {
        log.info("Request to get all pets");
        List<PetDTO> pets = petService.getAllPets();
        if (pets == null) {
            log.warn("No pets found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning a list of pets");
        return ResponseEntity.ok((pets));
    }

    @PostMapping
    public ResponseEntity<PetDTO> savePet(@RequestBody PetDTO petDTO) {
        log.info("Request to save a new pet");
        PetDTO petDto = petService.save(petDTO);
        log.info("Pet saved: " + petDto);
        CustomerDTO customerById = customerService.getCustomerById(petDTO.getOwnerId());
        List<PetDTO> pets = petService.getPetsByCustomerId(customerById.getCustomerId());
        if (pets != null) {
            if (customerById.getPetIds() == null) {
                customerById.setPetIds(pets.stream().map(PetDTO::getPetId).collect(Collectors.toList()));
            } else {
                List<Long> petIds = customerById.getPetIds();
                petIds.addAll(pets.stream().map(PetDTO::getPetId).collect(Collectors.toList()));
                customerById.setPetIds(petIds);
            }
            customerService.save(customerById);
        }
        log.info("Pet owner information updated: " + customerById);
        return ResponseEntity.ok(petDto);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable long petId) {
        log.info("Request to get pet by ID: " + petId);
        PetDTO petDto = petService.getPetById(petId);
        if (petDto == null) {
            log.warn("Pet not found with ID: " + petId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning pet: " + petDto);
        return ResponseEntity.ok((petDto));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDTO>> getPetsByOwner(@PathVariable long ownerId) {
        log.info("Request to get pets by owner ID: " + ownerId);
        List<PetDTO> petDtoList = petService.getPetsByCustomerId(ownerId);
        if (petDtoList == null) {
            log.warn("No pets found for owner with ID: " + ownerId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning pets for owner with ID: " + ownerId);
        return ResponseEntity.ok((petDtoList));
    }
}
