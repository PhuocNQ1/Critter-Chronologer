package com.udacity.jdnd.course3.critter.Utils;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.DTO.PetDTO;
import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.PetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntityDtoService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;


    public List<PetDTO> convertToPetDTOList(List<Pet> entityList) {
        return entityList.stream()
                .map(entity -> convertToDTO(entity))
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> convertToEmployeeDTOList(List<Employee> entityList) {
        return entityList.stream()
                .map(entity -> convertToDTO(entity))
                .collect(Collectors.toList());
    }

    /**
     * Convert a list of Schedule entities to a list of ScheduleDTOs.
     *
     * @param scheduleList The list of Schedule entities to be converted.
     * @return A list of ScheduleDTOs containing information about the schedules.
     */
    public List<ScheduleDTO> convertToScheduleDTOList(List<Schedule> scheduleList) {
        List<ScheduleDTO> dtos = new ArrayList<>();
        if (scheduleList != null) {
            for (Schedule schedule : scheduleList) {
                ScheduleDTO scheduleDto = modelMapper.map(schedule, ScheduleDTO.class);
                if (schedule.getEmployees() != null) {
                    scheduleDto.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getEmployeeId)
                            .collect(Collectors.toList()));
                }
                if (schedule.getPets() != null) {
                    scheduleDto.setPetIds(schedule.getPets().stream().map(Pet::getPetId).collect(Collectors.toList()));
                }
                dtos.add(scheduleDto);
            }
        }
        return dtos;
    }


    public Pet convertToEntity(PetDTO petDTO) {
        Pet pet = new Pet();
        pet.setPetId(petDTO.getPetId());
        pet.setPetType(petDTO.getPetType());
        pet.setPetName(petDTO.getPetName());
        pet.setPetBirthDate(petDTO.getPetBirthDate());
        pet.setPetNotes(petDTO.getPetNotes());

        if (petDTO.getOwnerId() != 0) {
            Customer customer = customerService.getCustomerById(petDTO.getOwnerId());
            pet.setCustomer(customer);
        }
        return pet;
    }

    public PetDTO convertToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        petDTO.setPetId(pet.getPetId());
        petDTO.setPetType(pet.getPetType());
        petDTO.setPetName(pet.getPetName());
        petDTO.setPetBirthDate(pet.getPetBirthDate());
        petDTO.setPetNotes(pet.getPetNotes());

        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getCustomerId());
        }

        return petDTO;
    }

    public Customer convertToEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDTO.getCustomerId());
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setCustomerPhoneNumber(customerDTO.getCustomerPhoneNumber());
        customer.setCustomerNotes(customerDTO.getCustomerNotes());

        List<Pet> pets = new ArrayList<>();
        if (customerDTO.getPetIds() != null) {
            for (Long petId : customerDTO.getPetIds()) {
                Pet pet = new Pet();
                pet.setPetId(petId);
                pet.setCustomer(customer);
                pets.add(pet);
            }
        }
        customer.setPets(pets);

        return customer;
    }

    public CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setCustomerName(customer.getCustomerName());
        customerDTO.setCustomerPhoneNumber(customer.getCustomerPhoneNumber());
        customerDTO.setCustomerNotes(customer.getCustomerNotes());

        List<Long> petIds = customer.getPets()
                .stream()
                .map(Pet::getPetId)
                .collect(Collectors.toList());
        customerDTO.setPetIds(petIds);

        return customerDTO;
    }

    public Employee convertToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setEmployeeDaysAvailable(employeeDTO.getEmployeeDaysAvailable());
        employee.setEmployeeSkills(employeeDTO.getEmployeeSkills());

        return employee;
    }

    public EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setEmployeeDaysAvailable(employee.getEmployeeDaysAvailable());
        employeeDTO.setEmployeeSkills(employee.getEmployeeSkills());

        return employeeDTO;
    }

    public ScheduleDTO convertToDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setScheduleId(schedule.getScheduleId());
        scheduleDTO.setEmployeeIds(schedule.getEmployees().stream()
                .map(Employee::getEmployeeId)
                .collect(Collectors.toList()));
        scheduleDTO.setPetIds(schedule.getPets().stream()
                .map(Pet::getPetId)
                .collect(Collectors.toList()));
        scheduleDTO.setScheduleDate(schedule.getScheduleDate());
        scheduleDTO.setActivities(schedule.getActivities());

        return scheduleDTO;
    }

    public Schedule convertToEntity(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setScheduleId(scheduleDTO.getScheduleId());

        List<Employee> employees = scheduleDTO.getEmployeeIds().stream()
                .map(id -> {
                    Employee employee = new Employee();
                    employee.setEmployeeId(id);
                    return employee;
                })
                .collect(Collectors.toList());
        schedule.setEmployees(employees);

        List<Pet> pets = scheduleDTO.getPetIds().stream()
                .map(id -> {
                    Pet pet = new Pet();
                    pet.setPetId(id);
                    return pet;
                })
                .collect(Collectors.toList());
        schedule.setPets(pets);

        schedule.setScheduleDate(scheduleDTO.getScheduleDate());
        schedule.setActivities(scheduleDTO.getActivities());

        return schedule;
    }

}
