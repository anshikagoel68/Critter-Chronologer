package com.udacity.jdnd.course3.critter.mapping;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class for Entity ↔ DTO conversion.
 */
public final class DataMapper {

    private DataMapper() {
    }

    // Customer mapping

    public static CustomerDTO toCustomerDTO(Customer customer) {

        CustomerDTO dto = new CustomerDTO();

        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setPhoneNumber(customer.getPhoneNumber());
        dto.setNotes(customer.getNotes());

        List<Pet> pets = customer.getPets();

        dto.setPetIds(
                pets == null
                        ? Collections.emptyList()
                        : pets.stream()
                        .map(Pet::getId)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    public static Customer fromCustomerDTO(CustomerDTO dto) {

        Customer customer = new Customer();

        customer.setName(dto.getName());
        customer.setPhoneNumber(dto.getPhoneNumber());
        customer.setNotes(dto.getNotes());

        return customer;
    }

    // Employee mapping

    public static EmployeeDTO toEmployeeDTO(Employee employee) {

        EmployeeDTO dto = new EmployeeDTO();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSkills(employee.getSkills());

        dto.setDaysAvailable(
                employee.getDaysAvailable() == null ||
                        employee.getDaysAvailable().isEmpty()
                        ? null
                        : employee.getDaysAvailable()
        );

        return dto;
    }

    public static Employee fromEmployeeDTO(EmployeeDTO dto) {

        Employee employee = new Employee();

        employee.setName(dto.getName());

        Optional.ofNullable(dto.getSkills())
                .ifPresent(employee::setSkills);

        Optional.ofNullable(dto.getDaysAvailable())
                .ifPresent(employee::setDaysAvailable);

        return employee;
    }

    // Pet mapping

    public static PetDTO toPetDTO(Pet pet) {

        PetDTO dto = new PetDTO();

        dto.setId(pet.getId());
        dto.setType(pet.getType());
        dto.setName(pet.getName());
        dto.setBirthDate(pet.getBirthDate());
        dto.setNotes(pet.getNotes());

        Optional.ofNullable(pet.getOwner())
                .ifPresent(owner -> dto.setOwnerId(owner.getId()));

        return dto;
    }

    public static Pet fromPetDTO(PetDTO dto) {

        Pet pet = new Pet();

        pet.setType(dto.getType());
        pet.setName(dto.getName());
        pet.setBirthDate(dto.getBirthDate());
        pet.setNotes(dto.getNotes());

        return pet;
    }

    // Schedule mapping

    public static ScheduleDTO toScheduleDTO(Schedule schedule) {

        ScheduleDTO dto = new ScheduleDTO();

        dto.setId(schedule.getId());
        dto.setDate(schedule.getDate());
        dto.setActivities(schedule.getActivities());

        dto.setEmployeeIds(
                schedule.getEmployees() == null
                        ? Collections.emptyList()
                        : schedule.getEmployees().stream()
                        .map(Employee::getId)
                        .collect(Collectors.toList())
        );

        dto.setPetIds(
                schedule.getPets() == null
                        ? Collections.emptyList()
                        : schedule.getPets().stream()
                        .map(Pet::getId)
                        .collect(Collectors.toList())
        );

        return dto;
    }
}