package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.consts.EmployeeSkill;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

/**
 * Represents a request to find available employees by skills. Does not map
 * to the database directly.
 */
@Data
public class EmployeeRequestDto {
    private Set<EmployeeSkill> skills;
    private LocalDate date;
}
