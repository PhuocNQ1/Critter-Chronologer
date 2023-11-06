package com.udacity.jdnd.course3.critter.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;

@Service
public interface CustomerService {

    CustomerDTO save(CustomerDTO customerDto);

    CustomerDTO getCustomerById(Long id);

    List<CustomerDTO> getAllCustomers();
}
