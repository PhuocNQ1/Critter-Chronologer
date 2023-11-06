package com.udacity.jdnd.course3.critter.Service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.Entity.Pet;

public interface PetService {

    PetDTO save(PetDTO petDto);

    PetDTO getPetById(Long id);

    List<PetDTO> getAllPets();

    List<PetDTO> getPetsByCustomerId(Long customerId);

    List<Pet> getPetsByPetIds(List<Long> petIds);

}
