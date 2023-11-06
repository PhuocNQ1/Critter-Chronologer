package com.udacity.jdnd.course3.critter.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.Entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
