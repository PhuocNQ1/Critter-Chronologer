package com.udacity.jdnd.course3.critter.API;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.Service.ScheduleService;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private static final Logger log = LogManager.getLogger(ScheduleController.class);

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        log.info("Request to get all schedules");
        List<ScheduleDTO> allSchedules = scheduleService.getAllSchedules();
        if (allSchedules == null) {
            log.warn("No schedules found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning a list of schedules");
        return ResponseEntity.ok(allSchedules);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForPet(@PathVariable long petId) {
        log.info("Request to get schedules for pet with ID: " + petId);
        List<ScheduleDTO> scheduleByPetId = scheduleService.getScheduleByPetId(petId);
        if (scheduleByPetId == null) {
            log.warn("No schedules found for pet with ID: " + petId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning schedules for pet with ID: " + petId);
        return ResponseEntity.ok(scheduleByPetId);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForEmployee(@PathVariable long employeeId) {
        log.info("Request to get schedules for employee with ID: " + employeeId);
        List<ScheduleDTO> scheduleByEmployeeId = scheduleService.getScheduleByEmployeeId(employeeId);
        if (scheduleByEmployeeId == null) {
            log.warn("No schedules found for employee with ID: " + employeeId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning schedules for employee with ID: " + employeeId);
        return ResponseEntity.ok(scheduleByEmployeeId);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForCustomer(@PathVariable long customerId) {
        log.info("Request to get schedules for customer with ID: " + customerId);
        List<ScheduleDTO> schedule = scheduleService.getScheduleByCustomerId(customerId);
        if (schedule == null) {
            log.warn("No schedules found for customer with ID: " + customerId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning schedules for customer with ID: " + customerId);
        return ResponseEntity.ok(schedule);
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        log.info("Request to create a new schedule");
        ScheduleDTO createdSchedule = scheduleService.save(scheduleDTO);
        log.info("Schedule created: " + createdSchedule);
        return ResponseEntity.ok(createdSchedule);
    }
}
