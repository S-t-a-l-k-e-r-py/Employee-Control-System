package com.myapp.service;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeAccount;
import com.myapp.entity.EmployeeTask;
import com.myapp.repositoty.EmployeeRepo.EmployeeRepository;
import com.myapp.repositoty.TaskRepo.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, TaskRepository taskRepository) {
        this.employeeRepository = repository;
        this.taskRepository = taskRepository;
        encoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    @Transactional
    public Employee getEmployeeByUserName(String name) {
        Employee employee = employeeRepository.getEmployeeByUserName(name);
        if (employee == null) {
            throw new RuntimeException(String.format("Employee with UserName: %s not found", name));
        }
        return employee;
    }

    @Override
    @Transactional
    public Employee getEmployeeById(int id) {
        Employee employee = employeeRepository.getEmployeeById(id);
        if (employee == null) {
            throw new RuntimeException(String.format("Employee with id: %s not found", id));
        }
        return employee;
    }

    @Override
    @Transactional
    public void deleteEmployeeById(int id) {
        Employee employee = employeeRepository.getEmployeeById(id);
        if (employee == null) {
            throw new RuntimeException(String.format("Employee with id: %s not found", id));
        }
        employeeRepository.deleteEmployeeById(id);
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        if (employee != null) {
            if (employee.getAccount() == null) {
                throw new RuntimeException("Employee account not find");
            }
            String password = employee.getAccount().getPassword();
            String email = employee.getAccount().getEmail();
            int id = employee.getAccount().getId();
            EmployeeAccount account = new EmployeeAccount(email, encoder.encode(password));
            account.setId(id);
            employee.setAccount(account);
            employeeRepository.saveEmployee(employee);
        }
    }

    @Override
    @Transactional
    public void updateEmployee(Employee employee) {
        if (employee != null) {
            employeeRepository.updateEmployee(employee);
        }
    }

    // taskRepository methods

    @Override
    @Transactional
    public List<EmployeeTask> getTasksByEmployeeId(int id) {
        return taskRepository.getTasksByEmployeeId(id);
    }

    @Override
    @Transactional
    public EmployeeTask getTaskById(int id) {
        EmployeeTask task = taskRepository.getTaskById(id);
        if (task == null) {
            throw new RuntimeException("Task not found");
        }
        return task;
    }

    @Override
    @Transactional
    public void addTask(EmployeeTask task, int employeeId) {
        taskRepository.addTask(task, employeeId);
    }

    @Override
    @Transactional
    public void updateTask(EmployeeTask task) {
        taskRepository.updateTask(task);
    }

    @Override
    @Transactional
    public void deleteTaskById(int id) {
        taskRepository.deleteTaskById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.getEmployeeByUserName(username);
        if (employee == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(employee.getRole()));
        return new User(employee.getUserName(), employee.getAccount().getPassword(), list);
    }

}
