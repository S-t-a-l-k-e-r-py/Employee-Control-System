package com.myapp.repositoty;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeTask;

import java.util.List;

public interface EmployeeRepository {
    List<EmployeeTask> getTasksByEmployeeId(int id);

    List<Employee> getAll();

    Employee getByUserName(String name);

    Employee getById(int id);

    void deleteById(int id);

    void save(Employee employee);

    void update(Employee employee);

    void addTask(EmployeeTask task, int employeeId);


}
