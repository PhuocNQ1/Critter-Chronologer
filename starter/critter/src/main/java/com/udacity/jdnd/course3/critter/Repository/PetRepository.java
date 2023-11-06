package com.udacity.jdnd.course3.critter.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.Entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("SELECT p FROM Pet p WHERE p.customer.customer_id = :customerId")
    List<Pet> findByCustomerId(Long customerId);

    List<Pet> findByPetIdIn(List<Long> petIds);
}
