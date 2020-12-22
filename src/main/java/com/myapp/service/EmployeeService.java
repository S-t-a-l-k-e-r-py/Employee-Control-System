package com.myapp.service;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeTask;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {
    List<EmployeeTask> getTasksByEmployeeId(int id);

    List<Employee> getAll();

    Employee getByUserName(String name);

    Employee getById(int id);

    void save(Employee employee);

    void update(Employee employee);

    void deleteById(int id);

    void addTask(EmployeeTask task, int employeeId);
}
