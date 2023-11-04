package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.consts.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.EmployeeDto;
import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Service
public interface EmployeeService {
    EmployeeDto save(EmployeeDto employeeDto);

    Long saveEmployeeDaysAvailable(Set<DayOfWeek> daysAvailable, Long employeeId);

    EmployeeDto getEmployeeById(Long id);

    List<EmployeeDto> getEmployeesBySkillsAndDaysAvailable(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek);

    List<Employee> getEmployeesByEmployeeIds(List<Long> employeeIds);
}
