package com.udacity.jdnd.course3.critter.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.Entity.Pet;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

    /**
     * JPQL Query
     * @param customerId
     * @return
     */
    @Query("SELECT p FROM Pet p WHERE p.customer.customerId = :customerId")
    List<Pet> findByCustomerId(Long customerId);

    /**
     * JPA Query Methods
     * @param petIds
     * @return
     */
    List<Pet> findByPetIdIn(List<Long> petIds);
}
