package com.myapp.controllers;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeTask;
import com.myapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller

@RequestMapping("/employee")
public class EmployeeController {
    final EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String employeePage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("employee");
        Employee employee = service.getEmployeeByUserName(userName);
        employee.setTasks(service.getTasksByEmployeeId(employee.getId()));
        model.addAttribute("emp", employee);
        return "employee-page";
    }

    @GetMapping("/main")
    public String employeeMainPage(Model model,HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee tempEmployee = (Employee) session.getAttribute("emp");
        Employee employee = service.getEmployeeById(tempEmployee.getId());
        employee.setTasks(service.getTasksByEmployeeId(employee.getId()));
        model.addAttribute("emp", employee);
        return "employee-page";
    }

    @GetMapping("/task/{id}")
    public String getTask(@PathVariable(name = "id") int id, Employee employee, Model model,HttpServletRequest request) {
        if (employee.getId() == 0) {
            return "access-denied";
        }
        HttpSession session = request.getSession();
        session.setAttribute("emp",employee);

        EmployeeTask task = service.getTaskById(id);
        task.setTitle(task.getTitle().substring(0, 1).toUpperCase() + task.getTitle().substring(1));
        model.addAttribute("task", task);
        model.addAttribute("emp",employee);
        return "task-page";
    }

    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable("id") int id, Employee employee,HttpServletRequest request) {
        if (employee.getId() == 0) {
            return "access-denied";
        }
        HttpSession session = request.getSession();
        session.setAttribute("emp",employee);

        EmployeeTask task = service.getTaskById(id);
        task.setComplete(true);
        return "redirect:/employee/main";
    }
}
