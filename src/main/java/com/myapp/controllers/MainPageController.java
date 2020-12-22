package com.myapp.controllers;

import com.myapp.entity.Employee;
import com.myapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@Controller
public class MainPageController {
    final EmployeeService service;

    @Autowired
    public MainPageController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String toMainPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("employee");
        Employee employee = service.getByUserName(userName);
        employee.setTasks(service.getTasksByEmployeeId(employee.getId()));
        model.addAttribute("emp",employee);
        return "employee-page";
    }

    @GetMapping("/employees")
    public String getMain() {
        return "main";
    }

    @GetMapping("/add")
    public String add() {
        return "main";
    }

}
