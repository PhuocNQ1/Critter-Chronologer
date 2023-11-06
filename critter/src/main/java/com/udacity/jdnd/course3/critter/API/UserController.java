package com.udacity.jdnd.course3.critter.API;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.Exception.CustomerException;
import com.udacity.jdnd.course3.critter.Exception.EmployeeException;
import com.udacity.jdnd.course3.critter.Exception.PetException;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeSevice;
import com.udacity.jdnd.course3.critter.Service.PetService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into
 * separate user and customer controllers would be fine too, though that is not
 * part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeSevice employeeService;

    @Autowired
    private PetService petService;

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        log.info("Request to get all customers");
        List<CustomerDTO> allCustomers = customerService.getAllCustomers();
        if (allCustomers == null) {
            log.warn("No customers found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning a list of customers");
        return ResponseEntity.ok(allCustomers);
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        log.info("Request to save a new customer");
        CustomerDTO savedCustomer = customerService.save(customerDTO);
        log.info("Customer saved: " + savedCustomer);
        return ResponseEntity.ok(savedCustomer);
    }

    @GetMapping("/customer/pet/{petId}")
    public ResponseEntity<CustomerDTO> getOwnerByPet(@PathVariable long petId) {
        log.info("Request to get owner by pet ID: " + petId);
        PetDTO petDto = null;
        CustomerDTO customerDto = null;
        try {
            petDto = petService.getPetById(petId);
            customerDto = customerService.getCustomerById(petDto.getOwnerId());
        } catch (PetException | CustomerException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning owner information for pet with ID: " + petId);
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Request to save a new employee");
        EmployeeDTO savedEmployee = employeeService.save(employeeDTO);
        log.info("Employee saved: " + savedEmployee);
        return ResponseEntity.ok(savedEmployee);
    }

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable long employeeId) {
        log.info("Request to get employee by ID: " + employeeId);
        EmployeeDTO employeeById = null;
        try {
            employeeById = employeeService.getEmployeeById(employeeId);
        } catch (EmployeeException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning employee with ID: " + employeeId);
        return ResponseEntity.ok(employeeById);
    }

    @GetMapping("/employee/availability")
    public ResponseEntity<List<EmployeeDTO>> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        log.info("Request to find employees by skills and availability");
        List<EmployeeDTO> employeeDtoList = employeeService.getEmployeesBySkillsAndDaysAvailable(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        if (employeeDtoList == null) {
            log.warn("No employees found for the specified criteria");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.info("Returning a list of employees");
        return ResponseEntity.ok(employeeDtoList);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        log.info("Request to set availability for employee with ID: " + employeeId);
        try {
            employeeService.saveEmployeeDaysAvailable(daysAvailable, employeeId);
            log.info("Availability set for employee with ID: " + employeeId);
        } catch (EmployeeException e) {
            log.error(e.getMessage());
        }
    }

}
