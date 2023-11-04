package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.CustomerDto;
import com.udacity.jdnd.course3.critter.util.HandlerCritterException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CustomerService {
    CustomerDto save(CustomerDto customerDto);

    CustomerDto getCustomerById(Long id) throws HandlerCritterException;

    List<CustomerDto> getAllCustomers();
}
