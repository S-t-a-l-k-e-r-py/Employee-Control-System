package com.myapp.service;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeTask;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    List<EmployeeTask> getTasksByEmployeeId(int id);

    List<Employee> getAllEmployees();

    EmployeeTask getTaskById(int id);

    Employee getEmployeeByUserName(String name);

    Employee getEmployeeById(int id);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployeeById(int id);

    void addTask(EmployeeTask task, int employeeId);

    void updateTask(EmployeeTask task);

    void deleteTaskById(int id);

}
