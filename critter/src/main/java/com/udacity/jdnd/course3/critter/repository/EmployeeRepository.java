package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

/**
 * Repository interface for Employee entity.
 */
@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

    // Find employees available on a specific day
    @Query(name = "Employee.availableOn")
    List<Employee> findAvailableOn(
            @Param("dayOfWeek") DayOfWeek dayOfWeek
    );
}