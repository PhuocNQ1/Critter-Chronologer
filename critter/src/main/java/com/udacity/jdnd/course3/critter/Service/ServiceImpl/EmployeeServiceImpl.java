package com.udacity.jdnd.course3.critter.Service.ServiceImpl;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.Constant.EmployeeSkill;
import com.udacity.jdnd.course3.critter.DTO.EmployeeDTO;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Exception.EmployeeException;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.Service.EmployeeSevice;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeSevice {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Save Employee
     */
    @Override
    public EmployeeDTO save(EmployeeDTO employeeDto) {
        Employee e = modelMapper.map(employeeDto, Employee.class);
        return modelMapper.map(employeeRepository.save(e), EmployeeDTO.class);
    }

    /**
     * Save the list of days on which an employee is available for work.
     *
     * @param daysAvailable The list of days of the week when the employee is
     *                      available for work.
     * @param employeeId    The ID of the employee whose information needs to be
     *                      updated.
     * @return The ID of the employee after updating the information.
     * @throws EmployeeException If no employee with the corresponding ID is found.
     */
    @Override
    public Long saveEmployeeDaysAvailable(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Optional<Employee> e = employeeRepository.findById(employeeId);
        if (e.isPresent()) {
            Employee e1 = e.get();
            e1.setEmployeeDaysAvailable(daysAvailable);
            employeeRepository.save(e1);
        } else {
            throw new EmployeeException("Not found Employee with id:" + employeeId);
        }
        return employeeId;
    }

    /**
     * Retrieve an employee by their unique ID.
     *
     * @param id The ID of the employee to retrieve.
     * @return The EmployeeDTO containing information about the employee.
     * @throws EmployeeException If no employee with the corresponding ID is found.
     */
    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Optional<Employee> e = employeeRepository.findById(id);
        Employee e1 = null;
        if (e.isPresent()) {
            e1 = e.get();
        } else {
            throw new EmployeeException("Not found Employee with id:" + id);
        }
        return modelMapper.map(e1, EmployeeDTO.class);
    }

    /**
     * Retrieve a list of employees with specific skills available on a given day of
     * the week.
     *
     * @param skills    The set of skills to filter employees by.
     * @param dayOfWeek The specific day of the week when employees should be
     *                  available.
     * @return A list of EmployeeDTOs containing information about employees who
     *         meet the criteria.
     */
    @Override
    public List<EmployeeDTO> getEmployeesBySkillsAndDaysAvailable(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {
        List<Employee> employeeList = employeeRepository.findAllByEmployeeDaysAvailableAndEmployeeSkillsIn(dayOfWeek, skills);
        List<EmployeeDTO> employeeDtoList = new ArrayList<>();
        if (employeeList != null) {
            employeeList.stream().distinct()
                    .forEach(employee -> employeeDtoList.add(getEmployeeById(employee.getEmployeeId())));
        }
        return employeeDtoList;
    }

    /**
     * Retrieve a list of employees by their unique employee IDs.
     *
     * @param employeeIds The list of unique employee IDs to retrieve employees.
     * @return A list of EmployeeDTOs containing information about the selected
     *         employees.
     */
    @Override
    public List<Employee> getEmployeesByEmployeeIds(List<Long> employeeIds) {
        return employeeRepository.findByEmployeeIdIn(employeeIds);
    }

}