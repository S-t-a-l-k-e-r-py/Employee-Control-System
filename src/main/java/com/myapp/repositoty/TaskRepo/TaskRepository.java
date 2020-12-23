package com.myapp.repositoty.TaskRepo;

import com.myapp.entity.EmployeeTask;

import java.util.List;

public interface TaskRepository {

    List<EmployeeTask> getTasksByEmployeeId(int id);

    EmployeeTask getTaskById(int id);

    void addTask(EmployeeTask task, int employeeId);

    void updateTask(EmployeeTask task);
}
