package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Constant.EmployeeSkill;
import com.udacity.jdnd.course3.critter.Entity.Employee;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeSevice {

    Employee save(Employee employeeDto);

    Long saveEmployeeDaysAvailable(Set<DayOfWeek> daysAvailable, Long employeeId);

    Employee getEmployeeById(Long id);

    List<Employee> getEmployeesBySkillsAndDaysAvailable(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek);

    List<Employee> getEmployeesByEmployeeIds(List<Long> employeeIds);
}
