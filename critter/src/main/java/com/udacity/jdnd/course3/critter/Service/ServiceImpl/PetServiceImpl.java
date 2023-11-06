package com.udacity.jdnd.course3.critter.Service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Exception.PetException;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.Service.PetService;

@Transactional
@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Save a pet's information including its owner.
     *
     * @param petDTO The PetDTO object containing pet details.
     * @return A PetDTO object representing the saved pet.
     * @throws PetException If the owner (Customer) is not found with the specified
     *                      ID.
     */
    @Override
    public PetDTO save(PetDTO PetDTO) {
        Pet pet = modelMapper.map(PetDTO, Pet.class);
        Optional<Customer> customer = customerRepository.findById(PetDTO.getOwnerId());
        if (customer.isPresent()) {
            pet.setCustomer(customer.get());
            PetDTO = modelMapper.map(petRepository.save(pet), PetDTO.class);
            PetDTO.setOwnerId(customer.get().getCustomer_id());
            return PetDTO;
        } else {
            throw new PetException("Not found Customer with id:" + PetDTO.getOwnerId());
        }
    }

    /**
     * Retrieve pet information by its unique ID.
     *
     * @param id The ID of the pet to retrieve.
     * @return A PetDTO object containing pet details, including the owner's ID.
     * @throws PetException If no pet with the specified ID is found.
     */
    @Override
    public PetDTO getPetById(Long id) {
        Optional<Pet> pet = petRepository.findById(id);
        PetDTO PetDTO = null;
        if (pet.isPresent()) {
            PetDTO = modelMapper.map(pet.get(), PetDTO.class);
            if (pet.get().getCustomer() != null) {
                PetDTO.setOwnerId(pet.get().getCustomer().getCustomer_id());
            }
        } else {
            throw new PetException("Not found Pet with id:" + id);
        }

        return PetDTO;
    }

    /**
     * Retrieve a list of all pets and their details.
     *
     * @return A list of PetDTOs containing information about all pets, including
     *         owner's IDs.
     */
    @Override
    public List<PetDTO> getAllPets() {
        List<Pet> petList = petRepository.findAll();
        List<PetDTO> PetDTOList = new ArrayList<>();
        if (petList != null) {
            PetDTOList = petList.stream().map(pet -> getPetById(pet.getPet_id())).collect(Collectors.toList());
        }
        return PetDTOList;
    }

    /**
     * Retrieve a list of pets associated with a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of PetDTOs containing information about pets associated with
     *         the customer.
     * @throws PetException If no pets are found for the specified customer.
     */
    @Override
    public List<PetDTO> getPetsByCustomerId(Long customerId) {
        List<Pet> petList = petRepository.findByCustomerId(customerId);
        List<PetDTO> PetDTOList = new ArrayList<>();
        if (petList != null) {
            PetDTOList = petList.stream().map(pet -> getPetById(pet.getPet_id())).collect(Collectors.toList());
        } else {
            throw new PetException("No pets found for customer with ID: " + customerId);
        }
        return PetDTOList;
    }

    /**
     * Retrieve a list of pets by their unique IDs.
     *
     * @param petIds The list of unique IDs of pets to retrieve.
     * @return A list of PetDTOs containing information about the selected pets.
     */
    @Override
    public List<Pet> getPetsByPetIds(List<Long> petIds) {
        return petRepository.findByPetIdIn(petIds);
    }

}
