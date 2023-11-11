package com.udacity.jdnd.course3.critter.Service;

import com.udacity.jdnd.course3.critter.Entity.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule save(Schedule schedule);

    List<Schedule> getScheduleByPetId(Long petId);

    List<Schedule> getScheduleByEmployeeId(Long employeeId);

    List<Schedule> getAllSchedules();

    List<Schedule> getScheduleByCustomerId(Long customerId);

}
