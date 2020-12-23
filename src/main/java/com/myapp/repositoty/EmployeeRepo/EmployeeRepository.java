package com.myapp.repositoty.EmployeeRepo;

import com.myapp.entity.Employee;

import java.util.List;

public interface EmployeeRepository {

    List<Employee> getAllEmployees();

    Employee getEmployeeByUserName(String name);

    Employee getEmployeeById(int id);

    void deleteEmployeeById(int id);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);
}
