package com.myapp.service;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeAccount;
import com.myapp.entity.EmployeeTask;
import com.myapp.repositoty.EmployeeRepository;
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
    private final EmployeeRepository repository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
        encoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public List<Employee> getAll() {
        return null;
    }

    @Override
    @Transactional
    public void save(Employee employee) {
        String password = employee.getAccount().getPassword();
        String email = employee.getAccount().getEmail();
        int id = employee.getAccount().getId();
        EmployeeAccount account = new EmployeeAccount(email, encoder.encode(password));
        account.setId(id);
        employee.setAccount(account);
        repository.save(employee);
    }

    @Override
    @Transactional
    public void update(Employee employee) {
        repository.update(employee);
    }


    @Override
    @Transactional
    public Employee getById(int id) {
        Employee employee = repository.getById(id);
        if (employee == null) {
            throw new RuntimeException();
        }
        return employee;
    }


    @Override
    @Transactional
    public void deleteById(int id) {
        Employee employee = repository.getById(id);
        if (employee != null) {
            throw new RuntimeException();
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void addTask(EmployeeTask task, int employeeId) {
    }

    @Override
    @Transactional
    public Employee getByUserName(String name) {
        return repository.getByUserName(name);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = repository.getByUserName(username);
        if (employee == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        List<SimpleGrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority(employee.getRole()));
        return new User(employee.getUserName(), employee.getAccount().getPassword(), list);
    }

}
