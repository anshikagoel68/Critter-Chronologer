package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.mapping.DataMapper;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REST controller for User resources (both Customer and Employee).
 *
 * DTO↔Entity conversion is handled entirely by {@link DataMapper};
 * business logic lives exclusively in the service layer.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    // ── Customer endpoints ───────────────────────────────────────────────────

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer entity = DataMapper.fromCustomerDTO(customerDTO);
        Customer saved  = customerService.registerCustomer(entity);
        return DataMapper.toCustomerDTO(saved);
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        return customerService.listAllCustomers().stream()
                .map(DataMapper::toCustomerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        Customer owner = customerService.fetchOwnerByPetId(petId);
        return DataMapper.toCustomerDTO(owner);
    }

    // ── Employee endpoints ───────────────────────────────────────────────────

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee entity = DataMapper.fromEmployeeDTO(employeeDTO);
        Employee saved  = employeeService.registerEmployee(entity);
        return DataMapper.toEmployeeDTO(saved);
    }

    /** Note: spec uses POST (not GET) for single-employee retrieval. */
    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return DataMapper.toEmployeeDTO(employeeService.fetchById(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable,
                                @PathVariable long employeeId) {
        employeeService.updateAvailability(employeeId, daysAvailable);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO request) {
        return employeeService
                .findSuitableEmployees(request.getDate(), request.getSkills())
                .stream()
                .map(DataMapper::toEmployeeDTO)
                .collect(Collectors.toList());
    }
}
