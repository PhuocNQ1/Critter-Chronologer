package com.udacity.jdnd.course3.critter.service.serviceImpl;

import com.udacity.jdnd.course3.critter.consts.EmployeeSkill;
import com.udacity.jdnd.course3.critter.dto.EmployeeDto;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import com.udacity.jdnd.course3.critter.util.HandlerCritterException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public EmployeeDto save(EmployeeDto employeeDto) {
        Employee employee = modelMapper.map(employeeDto, Employee.class);
        return modelMapper.map(employeeRepository.save(employee), EmployeeDto.class);
    }

    @Override
    public Long saveEmployeeDaysAvailable(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            Employee employee1 = employee.get();
            employee1.setEmployeeDaysAvailable(daysAvailable);
            employeeRepository.save(employee1);
        } else {
            throw new HandlerCritterException("Not found Employee with id:" + employeeId);
        }
        return employeeId;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        EmployeeDto employeeDto = null;
        if (employee.isPresent()) {
            employeeDto = modelMapper.map(employee.get(), EmployeeDto.class);
        }
        return employeeDto;
    }

    @Override
    public List<EmployeeDto> getEmployeesBySkillsAndDaysAvailable(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {
        List<Employee> employeeList = employeeRepository.findAllByEmployeeDaysAvailableAndEmployeeSkillsIn(dayOfWeek, skills);
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        if (employeeList != null) {
            employeeList.stream().distinct().forEach(employee -> employeeDtoList.add(getEmployeeById(employee.getEmployeeId())));
        }
        return employeeDtoList;
    }

    @Override
    public List<Employee> getEmployeesByEmployeeIds(List<Long> employeeIds) {
        return employeeRepository.findByEmployeeIdIn(employeeIds);
    }
}
