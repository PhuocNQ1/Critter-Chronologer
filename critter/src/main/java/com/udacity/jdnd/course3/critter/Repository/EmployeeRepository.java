package com.udacity.jdnd.course3.critter.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udacity.jdnd.course3.critter.Constant.EmployeeSkill;
import com.udacity.jdnd.course3.critter.Entity.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByDaysAvailableAndSkillsIn(DayOfWeek dayAvailable, Set<EmployeeSkill> skills);

    List<Employee> findByEmployeeIdIn(List<Long> employeeIds);

}
