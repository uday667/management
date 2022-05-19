package com.springboot.employeeManagement;


import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.springboot.employeeManagement.exceptions.EmployeeExistedException;
import com.springboot.employeeManagement.exceptions.EmployeeNotFoundException;

@RestController
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * @return It returns list of employees based on salary in descending order, If two employees salaries are same it fetches data in ascending order based on their names.
     */
    @GetMapping("/employees")
    Iterable<Employee> getAllEmployees() {
        return employeeRepository.findAll(Sort.by(Sort.Order.desc("salary"), Sort.Order.asc("name")));
    }

    /**
     * @param newEmployee New Employee to be added into database.
     * @return Newly added employee otherwise throws EmployeeExistedException.
     */
    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee newEmployee) {
        if (employeeRepository.existsById(newEmployee.getId()))
            throw new EmployeeExistedException();
        else return employeeRepository.save(newEmployee);
    }

    /**
     * @param id Id of the Employee to fetch.
     * @return it id exists return employee otherwise it throws EmployeeNotFoundException.
     */
    @GetMapping("/employees/{id}")
    Employee getEmployee(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    /**
     * @param id id of the employee to delete employee data in database.
     */
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

    /**
     * Updates employee salary as path variable.
     *
     * @param id Id of the employee.
     * @param salary New salary for this employee.
     * @return returns ok response with updated salary otherwise returns EmployeeNotFoundException
     */
    @PutMapping("/employees/upSalary/{id}/{salary}")
    ResponseEntity<Employee> updateSalary(@PathVariable long id, @PathVariable int salary) {
        Employee employee = getEmployee(id);
        employee.setSalary(salary);
        employeeRepository.save(employee);
        return ResponseEntity.ok(employee);
    }
}