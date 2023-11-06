package com.udacity.jdnd.course3.critter.Service.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.DTO.CustomerDTO;
import com.udacity.jdnd.course3.critter.DTO.ScheduleDTO;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import com.udacity.jdnd.course3.critter.Exception.ScheduleException;
import com.udacity.jdnd.course3.critter.Repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.Service.CustomerService;
import com.udacity.jdnd.course3.critter.Service.EmployeeSevice;
import com.udacity.jdnd.course3.critter.Service.PetService;
import com.udacity.jdnd.course3.critter.Service.ScheduleService;

@Transactional
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeSevice employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Save a schedule, associating employees and pets with it.
     *
     * @param scheduleDto The ScheduleDTO object containing schedule details and
     *                    associated employees and pets.
     * @return A ScheduleDTO object representing the saved schedule.
     * @throws ScheduleException If employees or pets are not found for the
     *                           schedule.
     */
    @Override
    public ScheduleDTO save(ScheduleDTO scheduleDto) {
        List<Employee> employeeList = employeeService.getEmployeesByEmployeeIds(scheduleDto.getEmployeeIds());
        List<Pet> petList = petService.getPetsByPetIds(scheduleDto.getPetIds());
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        if (employeeList != null && petList != null) {
            schedule.setEmployees(employeeList);
            schedule.setPets(petList);
            schedule = scheduleRepository.save(schedule);
            scheduleDto.setScheduleId(schedule.getScheduleId());
        } else {
            throw new ScheduleException("Not found Employee or Pet for this Schedule!");
        }
        return scheduleDto;
    }

    /**
     * Retrieve a list of schedules associated with a specific pet by its ID.
     *
     * @param petId The ID of the pet to filter schedules by.
     * @return A list of ScheduleDTOs containing information about the schedules
     *         associated with the pet.
     */
    @Override
    public List<ScheduleDTO> getScheduleByPetId(Long petId) {
        return convertListScheduleToListScheduleDto(scheduleRepository.findByPets(petId));
    }

    /**
     * Retrieve a list of schedules associated with a specific employee by their ID.
     *
     * @param employeeId The ID of the employee to filter schedules by.
     * @return A list of ScheduleDTOs containing information about the schedules
     *         associated with the employee.
     */
    @Override
    public List<ScheduleDTO> getScheduleByEmployeeId(Long employeeId) {
        return convertListScheduleToListScheduleDto(scheduleRepository.findByEmployees(employeeId));
    }

    /**
     * Retrieve a list of all schedules.
     *
     * @return A list of ScheduleDTOs containing information about all schedules.
     */
    @Override
    public List<ScheduleDTO> getAllSchedules() {
        return convertListScheduleToListScheduleDto(scheduleRepository.findAll());
    }

    /**
     * Retrieve a list of schedules associated with a specific customer by their ID.
     *
     * @param customerId The ID of the customer to filter schedules by.
     * @return A list of ScheduleDTOs containing information about the schedules
     *         associated with the customer's pets.
     */
    @Override
    public List<ScheduleDTO> getScheduleByCustomerId(Long customerId) {
        CustomerDTO customerDto = customerService.getCustomerById(customerId);
        List<ScheduleDTO> scheduleDtoList = new ArrayList<>();
        if (customerDto != null && !customerDto.getPetIds().isEmpty()) {
            customerDto.getPetIds().forEach(petId -> scheduleDtoList.addAll(getScheduleByPetId(petId)));
        }
        return scheduleDtoList;
    }

    /**
     * Convert a list of Schedule entities to a list of ScheduleDTOs.
     *
     * @param scheduleList The list of Schedule entities to be converted.
     * @return A list of ScheduleDTOs containing information about the schedules.
     */
    private List<ScheduleDTO> convertListScheduleToListScheduleDto(List<Schedule> scheduleList) {
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

}
