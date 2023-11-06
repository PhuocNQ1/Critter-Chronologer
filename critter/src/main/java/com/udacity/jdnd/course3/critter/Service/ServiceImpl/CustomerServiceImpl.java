package com.udacity.jdnd.course3.critter.Service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Exception.CustomerException;
import com.udacity.jdnd.course3.critter.Repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.Repository.PetRepository;
import com.udacity.jdnd.course3.critter.Service.CustomerService;

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
     * @param customerDto The CustomerDTO object containing customer details.
     * @return A CustomerDTO object representing the saved customer.
     */
    @Override
    public CustomerDTO save(CustomerDTO customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        if (customerDto.getPetIds() != null) {
            List<Pet> petList = petRepository.findByPetIdIn(customerDto.getPetIds());
            customer.setPets(petList);
        }
        return modelMapper.map(customerRepository.save(customer), CustomerDTO.class);
    }

    /**
     * Retrieve customer information by their unique ID.
     *
     * @param id The ID of the customer to retrieve.
     * @return A CustomerDTO object containing customer details and associated pet
     *         IDs, if available.
     * @throws CustomerException If no customer with the specified ID is found.
     */
    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        CustomerDTO customerDto = null;
        if (customer.isPresent()) {
            customerDto = modelMapper.map(customer.get(), CustomerDTO.class);
            if (customer.get().getPets() != null) {
                customerDto.setPetIds(
                        customer.get().getPets().stream().map(Pet::getPetId).collect(Collectors.toList()));
            }
        } else {
            throw new CustomerException("Not found Customer with id:" + id);
        }
        return customerDto;
    }

    /**
     * Retrieve a list of all customers and their details.
     *
     * @return A list of CustomerDTOs containing information about all customers.
     */
    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDTO> customerDtoList = new ArrayList<>();
        if (customerList != null) {
            customerList.stream().forEach(customer -> customerDtoList.add(getCustomerById(customer.getCustomerId())));
        }
        return customerDtoList;
    }

}
