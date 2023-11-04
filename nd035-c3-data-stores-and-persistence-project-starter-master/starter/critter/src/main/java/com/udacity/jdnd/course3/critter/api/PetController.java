package com.udacity.jdnd.course3.critter.api;

import com.udacity.jdnd.course3.critter.dto.CustomerDto;
import com.udacity.jdnd.course3.critter.dto.PetDto;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping
    public ResponseEntity<PetDto> savePet(@RequestBody PetDto petDTO) {
        PetDto petDto = petService.save(petDTO);
        CustomerDto customerDto = customerService.getCustomerById(petDTO.getOwnerId());
        List<PetDto> petDtoList = petService.getPetsByCustomerId(customerDto.getCustomerId());
        if (petDtoList != null) {
            if (customerDto.getPetIds() == null) {
                customerDto.setPetIds(petDtoList.stream().map(PetDto::getPetId).collect(Collectors.toList()));
            } else {
                List<Long> petIds = customerDto.getPetIds();
                petIds.addAll(petDtoList.stream().map(PetDto::getPetId).collect(Collectors.toList()));
                customerDto.setPetIds(petIds);
            }
            customerService.save(customerDto);
        }
        return ResponseEntity.ok(petDto);
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDto> getPet(@PathVariable long petId) {
        PetDto petDto = petService.getPetById(petId);
        if (petDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok((petDto));
    }

    @GetMapping
    public ResponseEntity<List<PetDto>> getPets() {
        List<PetDto> petDtoList = petService.getAllPets();
        if (petDtoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok((petDtoList));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PetDto>> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDto> petDtoList = petService.getPetsByCustomerId(ownerId);
        if (petDtoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok((petDtoList));
    }
}
