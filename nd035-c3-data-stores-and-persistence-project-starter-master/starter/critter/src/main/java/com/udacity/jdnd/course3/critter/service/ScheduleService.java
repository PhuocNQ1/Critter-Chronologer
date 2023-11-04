package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.ScheduleDto;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface ScheduleService {
    ScheduleDto save(ScheduleDto scheduleDto);

    List<ScheduleDto> getScheduleByPetId(Long petId);

    List<ScheduleDto> getScheduleByEmployeeId(Long employeeId);

    List<ScheduleDto> getAllSchedules();

    List<ScheduleDto> getScheduleByCustomerId(Long customerId);
}
