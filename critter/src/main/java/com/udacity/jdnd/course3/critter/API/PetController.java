package com.udacity.jdnd.course3.critter.API;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private PetService petService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<PetDTO>> getPets() {
        List<PetDTO> petDtoList = petService.getAllPets();
        if (petDtoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok((petDtoList));
    }

    @PostMapping
    public ResponseEntity<PetDTO> savePet(@RequestBody PetDTO petDTO) {
        PetDTO petDto = petService.save(petDTO);
        CustomerDTO customerDto = customerService.getCustomerById(petDTO.getOwnerId());
        List<PetDTO> petDtoList = petService.getPetsByCustomerId(customerDto.getId());
        if (petDtoList != null) {
            if (customerDto.getPetIds() == null) {
                customerDto.setPetIds(petDtoList.stream().map(PetDTO::getId).collect(Collectors.toList()));
            } else {
                List<Long> petIds = customerDto.getPetIds();
                petIds.addAll(petDtoList.stream().map(PetDTO::getId).collect(Collectors.toList()));
                customerDto.setPetIds(petIds);
            }
            customerService.save(customerDto);
        }
        return ResponseEntity.ok(petDto);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable long petId) {
        PetDTO petDto = petService.getPetById(petId);
        if (petDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok((petDto));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDTO>> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> petDtoList = petService.getPetsByCustomerId(ownerId);
        if (petDtoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok((petDtoList));
    }
}
