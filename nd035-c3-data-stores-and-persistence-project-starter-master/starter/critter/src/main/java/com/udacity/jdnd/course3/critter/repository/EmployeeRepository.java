package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.consts.EmployeeSkill;
import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findAllByEmployeeDaysAvailableAndEmployeeSkillsIn(DayOfWeek dayAvailable, Set<EmployeeSkill> skills);

    List<Employee> findByEmployeeIdIn(List<Long> employeeIds);
}

