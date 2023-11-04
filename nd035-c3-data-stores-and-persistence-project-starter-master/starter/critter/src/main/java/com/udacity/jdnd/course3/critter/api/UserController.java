package com.udacity.jdnd.course3.critter.api;

import com.udacity.jdnd.course3.critter.dto.CustomerDto;
import com.udacity.jdnd.course3.critter.dto.EmployeeDto;
import com.udacity.jdnd.course3.critter.dto.EmployeeRequestDto;
import com.udacity.jdnd.course3.critter.dto.PetDto;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public ResponseEntity<CustomerDto> saveCustomer(@RequestBody CustomerDto customerDTO) {
        return ResponseEntity.ok(customerService.save(customerDTO));
    }

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        List<CustomerDto> customerDtoList = customerService.getAllCustomers();
        if (customerDtoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(customerDtoList);
    }

    @GetMapping("/customer/pet/{petId}")
    public ResponseEntity<CustomerDto> getOwnerByPet(@PathVariable long petId) {
        PetDto petDto = petService.getPetById(petId);
        if (petDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        CustomerDto customerDto = customerService.getCustomerById(petDto.getOwnerId());
        if (customerDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping("/employee")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDTO) {
        return ResponseEntity.ok(employeeService.save(employeeDTO));
    }

    @PostMapping("/employee/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable long employeeId) {
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        if (employeeDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employeeDto);
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.saveEmployeeDaysAvailable(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public ResponseEntity<List<EmployeeDto>> findEmployeesForService(@RequestBody EmployeeRequestDto employeeDTO) {
        List<EmployeeDto> employeeDtoList = employeeService.getEmployeesBySkillsAndDaysAvailable(employeeDTO.getSkills(), employeeDTO.getDate().getDayOfWeek());
        if (employeeDtoList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(employeeDtoList);
    }

}
