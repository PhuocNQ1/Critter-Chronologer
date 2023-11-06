package com.udacity.jdnd.course3.critter.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.Constant.EmployeeSkill;
import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.Entity.Employee;

@Service
public interface EmployeeSevice {

    EmployeeDTO save(EmployeeDTO employeeDto);

    Long saveEmployeeDaysAvailable(Set<DayOfWeek> daysAvailable, Long employeeId);

    EmployeeDTO getEmployeeById(Long id);

    List<EmployeeDTO> getEmployeesBySkillsAndDaysAvailable(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek);

    List<Employee> getEmployeesByEmployeeIds(List<Long> employeeIds);
}
