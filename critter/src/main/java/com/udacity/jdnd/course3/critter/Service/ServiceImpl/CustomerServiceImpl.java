package com.udacity.jdnd.course3.critter.Service.ServiceImpl;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Exception.CustomerException;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Save customer information including associated pets.
     *
     * @param customer The CustomerDTO object containing customer details.
     * @return A CustomerDTO object representing the saved customer.
     */
    @Override
    public Customer save(Customer customer) {
        if (customer.getPets() != null && !customer.getPets().isEmpty()) {
            List<Long> listPetId = new ArrayList<>();
            for (Pet pet : customer.getPets()) {
                listPetId.add(pet.getPetId());
            }
            List<Pet> petList = petRepository.findByPetIdIn(listPetId);

            customer.setPets(petList);

            for (Pet pet : petList) {
                pet.setCustomer(customer);
            }
        }

        return customerRepository.saveAndFlush(customer);
    }

    /**
     * Retrieve customer information by their unique ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return A CustomerDTO object containing customer details and associated pet
     * IDs, if available.
     * @throws CustomerException If no customer with the specified ID is found.
     */
    @Override
    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new CustomerException("Not found Customer with id:" + id);
        }
    }

    /**
     * Retrieve a list of all customers and their details.
     *
     * @return A list of CustomerDTOs containing information about all customers.
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

}
