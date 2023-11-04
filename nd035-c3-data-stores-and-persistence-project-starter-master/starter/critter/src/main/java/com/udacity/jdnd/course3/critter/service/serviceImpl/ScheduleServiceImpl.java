package com.udacity.jdnd.course3.critter.service.serviceImpl;

import com.udacity.jdnd.course3.critter.dto.CustomerDto;
import com.udacity.jdnd.course3.critter.dto.ScheduleDto;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.util.HandlerCritterException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PetService petService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ScheduleDto save(ScheduleDto scheduleDto) {
        List<Employee> employeeList = employeeService.getEmployeesByEmployeeIds(scheduleDto.getEmployeeIds());
        List<Pet> petList = petService.getPetsByPetIds(scheduleDto.getPetIds());
        Schedule schedule = modelMapper.map(scheduleDto, Schedule.class);
        if (employeeList != null && petList != null) {
            schedule.setEmployees(employeeList);
            schedule.setPets(petList);
            schedule = scheduleRepository.save(schedule);
            scheduleDto.setScheduleId(schedule.getScheduleId());
        } else {
            throw new HandlerCritterException("Not found Employee or Pet for this Schedule!");
        }
        return scheduleDto;
    }

    @Override
    public List<ScheduleDto> getScheduleByPetId(Long petId) {
        return convertListScheduleToListScheduleDto(scheduleRepository.findByPets(petId));
    }

    @Override
    public List<ScheduleDto> getScheduleByEmployeeId(Long employeeId) {
        return convertListScheduleToListScheduleDto(scheduleRepository.findByEmployees(employeeId));
    }

    @Override
    public List<ScheduleDto> getAllSchedules() {
        return convertListScheduleToListScheduleDto(scheduleRepository.findAll());
    }

    @Override
    public List<ScheduleDto> getScheduleByCustomerId(Long customerId) {
        CustomerDto customerDto = customerService.getCustomerById(customerId);
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        if (customerDto != null && !customerDto.getPetIds().isEmpty()) {
            customerDto.getPetIds().forEach(petId -> scheduleDtoList.addAll(getScheduleByPetId(petId)));
        }
        return scheduleDtoList;
    }

    private List<ScheduleDto> convertListScheduleToListScheduleDto(List<Schedule> scheduleList) {
        List<ScheduleDto> scheduleDtoList = new ArrayList<>();
        if (scheduleList != null) {
            for (Schedule schedule : scheduleList) {
                ScheduleDto scheduleDto = modelMapper.map(schedule, ScheduleDto.class);
                if (schedule.getEmployees() != null) {
                    scheduleDto.setEmployeeIds(schedule.getEmployees().stream().map(Employee::getEmployeeId).collect(Collectors.toList()));
                }
                if (schedule.getPets() != null) {
                    scheduleDto.setPetIds(schedule.getPets().stream().map(Pet::getPetId).collect(Collectors.toList()));
                }
                scheduleDtoList.add(scheduleDto);
            }
        }
        return scheduleDtoList;
    }
}
