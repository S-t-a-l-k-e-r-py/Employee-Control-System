package com.myapp.controllers;

import com.myapp.repositoty.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainPageController {
    final EmployeeRepository repository;

    @Autowired
    public MainPageController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String toMainPage() {
        return "main";
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
