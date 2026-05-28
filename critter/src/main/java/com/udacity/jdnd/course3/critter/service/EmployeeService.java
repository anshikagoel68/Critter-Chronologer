package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for Employee operations.
 */
@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepo;

    public EmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    // Save employee
    public Employee registerEmployee(Employee employee) {
        return employeeRepo.save(employee);
    }

    // Get employee by ID
    @Transactional(readOnly = true)
    public Employee fetchById(long employeeId) {
        return employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", employeeId));
    }

    // Update employee availability
    public void updateAvailability(long employeeId, Set<DayOfWeek> availableDays) {
        Employee emp = fetchById(employeeId);
        emp.setDaysAvailable(availableDays);
        employeeRepo.save(emp);
    }

    // Find employees matching date and skills
    @Transactional(readOnly = true)
    public List<Employee> findSuitableEmployees(LocalDate requestedDate,Set<EmployeeSkill> requiredSkills) {

        DayOfWeek targetDay = requestedDate.getDayOfWeek();

        List<Employee> workingThatDay = employeeRepo.findAvailableOn(targetDay);

        return workingThatDay.stream()
                .filter(emp -> emp.getSkills().containsAll(requiredSkills))
                .collect(Collectors.toList());
    }
}