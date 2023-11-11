package com.udacity.jdnd.course3.critter.API;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Exception.CustomerException;
import com.udacity.jdnd.course3.critter.Exception.EmployeeException;
import com.udacity.jdnd.course3.critter.Exception.PetException;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeSevice;
import com.udacity.jdnd.course3.critter.Service.PetService;
import com.udacity.jdnd.course3.critter.Utils.EntityDtoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Autowired
    private EntityDtoService dtoService;

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        log.info("Request to get all customers");

        try {
            List<Customer> allCustomers = customerService.getAllCustomers();

            if (allCustomers.isEmpty()) {
                log.warn("No customers found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            List<CustomerDTO> customerDTOS = allCustomers.stream()
                    .map(customer -> dtoService.convertToDTO(customer))
                    .collect(Collectors.toList());

            log.info("Returning a list of customers");
            return ResponseEntity.ok(customerDTOS);
        } catch (Exception e) {
            log.error("Error getting all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/customer")
    public ResponseEntity<CustomerDTO> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        log.info("Request to save a new customer");

        try {
            Customer savedCustomer = customerService.save(dtoService.convertToEntity(customerDTO));
            log.info("Customer saved: {}", savedCustomer);

            CustomerDTO savedCustomerDTO = dtoService.convertToDTO(savedCustomer);

            return ResponseEntity.ok(savedCustomerDTO);
        } catch (Exception e) {
            log.error("Error saving customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/customer/pet/{petId}")
    public ResponseEntity<CustomerDTO> getOwnerByPet(@PathVariable long petId) {
        try {
            Pet pet = petService.getPetById(petId);

            if (pet != null) {
                Customer customer = customerService.getCustomerById(pet.getCustomer().getCustomerId());

                log.info("Returning owner information for pet with ID: {}", petId);
                return ResponseEntity.ok(dtoService.convertToDTO(customer));
            } else {
                log.warn("Pet not found with ID: {}", petId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (PetException | CustomerException e) {
            log.error("Error getting owner information for pet with ID: {}", petId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Request to save a new employee");
        try {
            Employee savedEmployee = employeeService.save(dtoService.convertToEntity(employeeDTO));

            log.info("Employee saved: " + savedEmployee);
            return ResponseEntity.ok(dtoService.convertToDTO(savedEmployee));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable long employeeId) {
        log.info("Request to get employee by ID: {}", employeeId);

        try {
            Employee employeeById = employeeService.getEmployeeById(employeeId);

            if (employeeById != null) {
                log.info("Returning employee with ID: {}", employeeId);
                return ResponseEntity.ok(dtoService.convertToDTO(employeeById));
            } else {
                log.warn("Employee not found with ID: {}", employeeId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (EmployeeException e) {
            log.error("Error getting employee with ID: {}", employeeId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/employee/availability")
    public ResponseEntity<List<EmployeeDTO>> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        log.info("Request to find employees by skills and availability");
        try {
            List<Employee> employeeList = employeeService.getEmployeesBySkillsAndDaysAvailable(
                    employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());

            if (employeeList.isEmpty()) {
                log.warn("No employees found for the specified criteria");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            List<EmployeeDTO> employeeDTOList = employeeList.stream()
                    .map(employee -> dtoService.convertToDTO(employee))
                    .collect(Collectors.toList());

            log.info("Returning a list of employees");
            return ResponseEntity.ok(employeeDTOList);
        } catch (Exception e) {
            log.error("Error finding employees", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        log.info("Request to set availability for employee with ID: {}", employeeId);
        try {
            employeeService.saveEmployeeDaysAvailable(daysAvailable, employeeId);
            log.info("Availability set for employee with ID: {}", employeeId);
        } catch (EmployeeException e) {
            log.error("Error setting availability for employee with ID: {}", employeeId, e);
        }
    }

}
