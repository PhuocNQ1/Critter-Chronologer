package com.udacity.jdnd.course3.critter.dto;

import com.udacity.jdnd.course3.critter.consts.EmployeeSkill;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Represents the form that schedule request and response data takes. Does not map
 * to the database directly.
 */
@Data
public class ScheduleDto {
    private long scheduleId;
    private List<Long> employeeIds;
    private List<Long> petIds;
    private LocalDate scheduleDate;
    private Set<EmployeeSkill> activities;
}
