package com.udacity.jdnd.course3.critter.api;

import com.udacity.jdnd.course3.critter.dto.ScheduleDto;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleDto> createSchedule(@RequestBody ScheduleDto scheduleDTO) {
        return ResponseEntity.ok(scheduleService.save(scheduleDTO));
    }

    @GetMapping
    public ResponseEntity<List<ScheduleDto>> getAllSchedules() {
        List<ScheduleDto> scheduleDtoList = scheduleService.getAllSchedules();
        if (scheduleDtoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(scheduleDtoList);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<ScheduleDto>> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDto> scheduleDtoList = scheduleService.getScheduleByPetId(petId);
        if (scheduleDtoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(scheduleDtoList);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ScheduleDto>> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDto> scheduleDtoList = scheduleService.getScheduleByEmployeeId(employeeId);
        if (scheduleDtoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(scheduleDtoList);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ScheduleDto>> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDto> scheduleDtoList = scheduleService.getScheduleByCustomerId(customerId);
        if (scheduleDtoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(scheduleDtoList);
    }
}
