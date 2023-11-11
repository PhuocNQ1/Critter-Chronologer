package com.udacity.jdnd.course3.critter.Service.ServiceImpl;

import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Exception.PetException;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.Service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    /**
     * Save a pet's information including its owner.
     *
     * @param pet The PetDTO object containing pet details.
     * @return A PetDTO object representing the saved pet.
     * @throws PetException If the owner (Customer) is not found with the specified
     *                      ID.
     */
    @Override
    public Pet save(Pet pet) {
        Optional<Customer> customer = customerRepository.findById(pet.getCustomer().getCustomerId());
        if (customer.isPresent()) {
            pet.setCustomer(customer.get());
            return petRepository.saveAndFlush(pet);
        } else {
            throw new PetException("Not found Customer with id:" + pet.getCustomer().getCustomerId());
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
    public Pet getPetById(Long id) {
        Optional<Pet> pet = petRepository.findById(id);
        if (pet.isPresent()) {
            return pet.get();
        } else {
            throw new PetException("Not found Pet with id:" + id);
        }
    }

    /**
     * Retrieve a list of all pets and their details.
     *
     * @return A list of PetDTOs containing information about all pets, including
     * owner's IDs.
     */
    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    /**
     * Retrieve a list of pets associated with a specific customer.
     *
     * @param customerId The ID of the customer.
     * @return A list of PetDTOs containing information about pets associated with
     * the customer.
     * @throws PetException If no pets are found for the specified customer.
     */
    @Override
    public List<Pet> getPetsByCustomerId(Long customerId) {
        return petRepository.findByCustomerId(customerId);
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
