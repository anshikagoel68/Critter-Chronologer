package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for Schedule entity.
 */
@Repository
public interface ScheduleRepository
        extends JpaRepository<Schedule, Long> {

    // Get schedules for an employee
    @Query(name = "Schedule.forEmployee")
    List<Schedule> findByEmployee(
            @Param("emp") Employee employee
    );

    // Get schedules for a pet
    @Query(name = "Schedule.forPet")
    List<Schedule> findByPet(
            @Param("pet") Pet pet
    );

    // Get schedules for a customer
    @Query(name = "Schedule.forCustomer")
    List<Schedule> findByCustomerId(
            @Param("customerId") long customerId
    );
}