package com.udacity.jdnd.course3.critter.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.Entity.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * SQL Native Query
     * @param petId
     * @return
     */
    @Query(value = "select * from schedule s where s.schedule_id in (select ps.schedule_id from pet_schedule ps where ps.pet_id = :petId)", nativeQuery = true)
    List<Schedule> findByPets(Long petId);

    /**
     * SQL Native Query
     * @param employeeId
     * @return
     */
    @Query(value = "select * from schedule s where s.schedule_id in (select es.schedule_id from employee_schedule es where es.employee_id = :employeeId)", nativeQuery = true)
    List<Schedule> findByEmployees(Long employeeId);
}
