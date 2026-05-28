package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.exception.ResourceNotFoundException;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for Schedule operations.
 */
@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final EmployeeRepository employeeRepo;
    private final PetRepository petRepo;

    public ScheduleService(ScheduleRepository scheduleRepo,
                           EmployeeRepository employeeRepo,
                           PetRepository petRepo) {

        this.scheduleRepo = scheduleRepo;
        this.employeeRepo = employeeRepo;
        this.petRepo = petRepo;
    }

    // Create and save schedule
    public Schedule book(Schedule schedule,
                         List<Long> employeeIds,
                         List<Long> petIds) {

        List<Employee> employees = employeeIds.stream()
                .map(id -> employeeRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee", id)))
                .collect(Collectors.toList());

        List<Pet> pets = petIds.stream()
                .map(id -> petRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Pet", id)))
                .collect(Collectors.toList());

        schedule.setEmployees(employees);
        schedule.setPets(pets);

        return scheduleRepo.save(schedule);
    }

    // Get all schedules
    @Transactional(readOnly = true)
    public List<Schedule> listAll() {
        return scheduleRepo.findAll();
    }

    // Get schedules for a pet
    @Transactional(readOnly = true)
    public List<Schedule> listForPet(long petId) {

        Pet pet = petRepo.findById(petId)
                .orElseThrow(() -> new ResourceNotFoundException("Pet", petId));

        return scheduleRepo.findByPet(pet);
    }

    // Get schedules for an employee
    @Transactional(readOnly = true)
    public List<Schedule> listForEmployee(long employeeId) {

        Employee emp = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee", employeeId));

        return scheduleRepo.findByEmployee(emp);
    }

    // Get schedules for a customer
    @Transactional(readOnly = true)
    public List<Schedule> listForCustomer(long customerId) {
        return scheduleRepo.findByCustomerId(customerId);
    }
}