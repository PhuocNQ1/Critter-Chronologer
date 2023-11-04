package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(value = "select * from schedule s where s.schedule_id in (select ps.schedule_id from pet_schedule ps where ps.pet_id = :petId)", nativeQuery = true)
    List<Schedule> findByPets(Long petId);

    @Query(value = "select * from schedule s where s.schedule_id in (select es.schedule_id from employee_schedule es where es.employee_id = :employeeId)", nativeQuery = true)
    List<Schedule> findByEmployees(Long employeeId);
}
