package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Entity.Pet;

import java.util.List;

public interface PetService {

    Pet save(Pet petDto);

    Pet getPetById(Long id);

    List<Pet> getAllPets();

    List<Pet> getPetsByCustomerId(Long customerId);

    List<Pet> getPetsByPetIds(List<Long> petIds);

}
