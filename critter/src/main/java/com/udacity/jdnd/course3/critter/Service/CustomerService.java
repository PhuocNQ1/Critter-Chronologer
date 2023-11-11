package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer save(Customer customerDto);

    Customer getCustomerById(Long id);

    List<Customer> getAllCustomers();
}
