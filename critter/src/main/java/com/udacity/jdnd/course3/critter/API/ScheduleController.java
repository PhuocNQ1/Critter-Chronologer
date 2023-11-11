package com.udacity.jdnd.course3.critter.API;

import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import com.udacity.jdnd.course3.critter.Service.ScheduleService;
import com.udacity.jdnd.course3.critter.Utils.EntityDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private static final Logger log = LogManager.getLogger(ScheduleController.class);

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private EntityDtoService dtoService;

    @GetMapping
    public ResponseEntity<List<ScheduleDTO>> getAllSchedules() {
        log.info("Request to get all schedules");

        try {
            List<Schedule> allSchedules = scheduleService.getAllSchedules();
            if (allSchedules == null) {
                log.warn("No schedules found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            log.info("Returning a list of schedules");
            List<ScheduleDTO> petsDto = dtoService.convertToScheduleDTOList(allSchedules);
            return ResponseEntity.ok(petsDto);
        } catch (Exception e) {
            log.error("Error getting all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForPet(@PathVariable long petId) {
        log.info("Request to get schedules for pet with ID: " + petId);

        try {
            List<Schedule> scheduleByPetId = scheduleService.getScheduleByPetId(petId);
            if (scheduleByPetId == null) {
                log.warn("No schedules found for pet with ID: " + petId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            log.info("Returning schedules for pet with ID: " + petId);

            List<ScheduleDTO> petsDto = dtoService.convertToScheduleDTOList(scheduleByPetId);
            return ResponseEntity.ok(petsDto);
        } catch (Exception e) {
            log.error("Error getting all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForEmployee(@PathVariable long employeeId) {
        log.info("Request to get schedules for employee with ID: " + employeeId);

        try {
            List<Schedule> scheduleByEmployeeId = scheduleService.getScheduleByEmployeeId(employeeId);
            if (scheduleByEmployeeId == null) {
                log.warn("No schedules found for employee with ID: " + employeeId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            log.info("Returning schedules for employee with ID: " + employeeId);
            List<ScheduleDTO> petsDto = dtoService.convertToScheduleDTOList(scheduleByEmployeeId);
            return ResponseEntity.ok(petsDto);
        } catch (Exception e) {
            log.error("Error getting all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ScheduleDTO>> getScheduleForCustomer(@PathVariable long customerId) {
        log.info("Request to get schedules for customer with ID: " + customerId);

        try {
            List<Schedule> scheduleByCustomerId = scheduleService.getScheduleByCustomerId(customerId);
            if (scheduleByCustomerId == null) {
                log.warn("No schedules found for customer with ID: " + customerId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            log.info("Returning schedules for customer with ID: " + customerId);
            List<ScheduleDTO> petsDto = dtoService.convertToScheduleDTOList(scheduleByCustomerId);
            return ResponseEntity.ok(petsDto);
        } catch (Exception e) {
            log.error("Error getting all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        log.info("Request to create a new schedule");

        try {
            Schedule createdSchedule = scheduleService.save(dtoService.convertToEntity(scheduleDTO));
            log.info("Schedule created: " + createdSchedule);
            return ResponseEntity.ok(dtoService.convertToDTO(createdSchedule));
        } catch (Exception e) {
            log.error("Error getting all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
