package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.mapping.DataMapper;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for Schedule operations.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // Create a new schedule
    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule shell = new Schedule();
        shell.setDate(scheduleDTO.getDate());
        shell.setActivities(scheduleDTO.getActivities());

        Schedule saved = scheduleService.book(
                shell,
                scheduleDTO.getEmployeeIds(),
                scheduleDTO.getPetIds()
        );

        return DataMapper.toScheduleDTO(saved);
    }

    // Get all schedules
    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.listAll().stream()
                .map(DataMapper::toScheduleDTO)
                .collect(Collectors.toList());
    }

    // Get schedules for a pet
    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        return scheduleService.listForPet(petId).stream()
                .map(DataMapper::toScheduleDTO)
                .collect(Collectors.toList());
    }

    // Get schedules for an employee
    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        return scheduleService.listForEmployee(employeeId).stream()
                .map(DataMapper::toScheduleDTO)
                .collect(Collectors.toList());
    }

    // Get schedules for a customer
    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        return scheduleService.listForCustomer(customerId).stream()
                .map(DataMapper::toScheduleDTO)
                .collect(Collectors.toList());
    }
}