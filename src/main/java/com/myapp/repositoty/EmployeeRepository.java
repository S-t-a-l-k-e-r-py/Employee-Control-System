package com.myapp.repositoty;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeTask;

import java.util.List;

public interface EmployeeRepository {
    List<Employee> getAll();
    void save(Employee employee);
    void update(Employee employee);
    Employee getByUserName(String name);
    Employee getById(int id);
    void deleteById(int id);
    void addTask(EmployeeTask task, int employeeId);



}
