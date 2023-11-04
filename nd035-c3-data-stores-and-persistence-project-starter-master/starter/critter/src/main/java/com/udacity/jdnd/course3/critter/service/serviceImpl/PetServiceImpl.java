package com.udacity.jdnd.course3.critter.service.serviceImpl;

import com.udacity.jdnd.course3.critter.dto.PetDto;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.util.HandlerCritterException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PetDto save(PetDto petDto) {
        Pet pet = modelMapper.map(petDto, Pet.class);
        Optional<Customer> customer = customerRepository.findById(petDto.getOwnerId());
        if (customer.isPresent()) {
            pet.setCustomer(customer.get());
            petDto = modelMapper.map(petRepository.save(pet), PetDto.class);
            petDto.setOwnerId(customer.get().getCustomerId());
            return petDto;
        } else {
            throw new HandlerCritterException("Not found Customer with id:" + petDto.getOwnerId());
        }
    }

    @Override
    public PetDto getPetById(Long id) {
        Optional<Pet> pet = petRepository.findById(id);
        PetDto petDto = null;
        if (pet.isPresent()) {
            petDto = modelMapper.map(pet.get(), PetDto.class);
            if (pet.get().getCustomer() != null) {
                petDto.setOwnerId(pet.get().getCustomer().getCustomerId());
            }
        }
        return petDto;
    }

    @Override
    public List<PetDto> getAllPets() {
        List<Pet> petList = petRepository.findAll();
        List<PetDto> petDtoList = new ArrayList<>();
        if (petList != null) {
            petDtoList = petList.stream().map(pet -> getPetById(pet.getPetId())).collect(Collectors.toList());
        }
        return petDtoList;
    }

    @Override
    public List<PetDto> getPetsByCustomerId(Long customerId) {
        List<Pet> petList = petRepository.findByCustomerId(customerId);
        List<PetDto> petDtoList = new ArrayList<>();
        if (petList != null) {
            petDtoList = petList.stream().map(pet -> getPetById(pet.getPetId())).collect(Collectors.toList());
        }
        return petDtoList;
    }

    @Override
    public List<Pet> getPetsByPetIds(List<Long> petIds) {
        return petRepository.findByPetIdIn(petIds);
    }
}
