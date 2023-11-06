package com.udacity.jdnd.course3.critter.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;

public interface ScheduleService {

    ScheduleDTO save(ScheduleDTO scheduleDto);

    List<ScheduleDTO> getScheduleByPetId(Long petId);

    List<ScheduleDTO> getScheduleByEmployeeId(Long employeeId);

    List<ScheduleDTO> getAllSchedules();

    List<ScheduleDTO> getScheduleByCustomerId(Long customerId);

}
