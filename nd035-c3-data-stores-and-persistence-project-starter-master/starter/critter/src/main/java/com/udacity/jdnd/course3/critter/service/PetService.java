package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.PetDto;
import com.udacity.jdnd.course3.critter.entity.Pet;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface PetService {
    PetDto save(PetDto petDto);

    PetDto getPetById(Long id);

    List<PetDto> getAllPets();

    List<PetDto> getPetsByCustomerId(Long customerId);

    List<Pet> getPetsByPetIds(List<Long> petIds);
}
