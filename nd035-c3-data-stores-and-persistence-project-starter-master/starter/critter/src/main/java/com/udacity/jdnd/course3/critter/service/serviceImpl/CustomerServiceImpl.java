package com.udacity.jdnd.course3.critter.service.serviceImpl;

import com.udacity.jdnd.course3.critter.dto.CustomerDto;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        if (customerDto.getPetIds() != null) {
            List<Pet> petList = petRepository.findByPetIdIn(customerDto.getPetIds());
            customer.setPets(petList);
        }
        return modelMapper.map(customerRepository.save(customer), CustomerDto.class);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        CustomerDto customerDto = null;
        if (customer.isPresent()) {
            customerDto = modelMapper.map(customer.get(), CustomerDto.class);
            if (customer.get().getPets() != null) {
                customerDto.setPetIds(customer.get().getPets().stream().map(Pet::getPetId).collect(Collectors.toList()));
            }
        }
        return customerDto;
    }

    @Override
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDto> customerDtoList = new ArrayList<>();
        if (customerList != null) {
            customerList.stream().forEach(customer -> customerDtoList.add(getCustomerById(customer.getCustomerId())));
        }
        return customerDtoList;
    }

}
