package com.udacity.jdnd.course3.critter.Entity;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import com.udacity.jdnd.course3.critter.Constant.EmployeeSkill;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

    @Column(nullable = false)
    private String employeeName;

    @ElementCollection(targetClass = DayOfWeek.class)
    @CollectionTable(name = "employee_days_available", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> employeeDaysAvailable;

    @ElementCollection(targetClass = EmployeeSkill.class)
    @CollectionTable(name = "employee_skill", joinColumns = @JoinColumn(name = "employee_id"))
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> employeeSkills;

    @ManyToMany(mappedBy = "employees")
    private List<Schedule> schedules;

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Set<DayOfWeek> getEmployeeDaysAvailable() {
        return employeeDaysAvailable;
    }

    public void setEmployeeDaysAvailable(Set<DayOfWeek> employeeDaysAvailable) {
        this.employeeDaysAvailable = employeeDaysAvailable;
    }

    public Set<EmployeeSkill> getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(Set<EmployeeSkill> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
