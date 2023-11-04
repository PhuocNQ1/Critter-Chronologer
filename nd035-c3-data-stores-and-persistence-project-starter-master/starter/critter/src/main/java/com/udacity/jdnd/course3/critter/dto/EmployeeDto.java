package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.consts.EmployeeSkill;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.Set;

/**
 * Represents the form that employee request and response data takes. Does not map
 * to the database directly.
 */
@Data
public class EmployeeDto {
    private long employeeId;
    private String employeeName;
    private Set<EmployeeSkill> employeeSkills;
    private Set<DayOfWeek> employeeDaysAvailable;
}
