package com.udacity.jdnd.course3.critter.Service.ServiceImpl;

import com.udacity.jdnd.course3.critter.Constant.EmployeeSkill;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Exception.EmployeeException;
import com.udacity.jdnd.course3.critter.Repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.Service.EmployeeSevice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Employee save(Employee employeeDto) {
        Employee e = modelMapper.map(employeeDto, Employee.class);
        return employeeRepository.saveAndFlush(e);
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
            Employee employee = e.get();
            employee.setEmployeeDaysAvailable(daysAvailable);
            employeeRepository.save(employee);
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
    public Employee getEmployeeById(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        } else {
            throw new EmployeeException("Not found Employee with id:" + id);
        }
    }

    /**
     * Retrieve a list of employees with specific skills available on a given day of
     * the week.
     *
     * @param skills    The set of skills to filter employees by.
     * @param dayOfWeek The specific day of the week when employees should be
     *                  available.
     * @return A list of EmployeeDTOs containing information about employees who
     * meet the criteria.
     */
    @Override
    public List<Employee> getEmployeesBySkillsAndDaysAvailable(Set<EmployeeSkill> skills, DayOfWeek dayOfWeek) {
        return employeeRepository.findAllByEmployeeDaysAvailableAndEmployeeSkillsIn(dayOfWeek, skills);
    }

    /**
     * Retrieve a list of employees by their unique employee IDs.
     *
     * @param employeeIds The list of unique employee IDs to retrieve employees.
     * @return A list of EmployeeDTOs containing information about the selected
     * employees.
     */
    @Override
    public List<Employee> getEmployeesByEmployeeIds(List<Long> employeeIds) {
        return employeeRepository.findByEmployeeIdIn(employeeIds);
    }

}