package com.myapp.controllers.director;

import com.myapp.entity.Employee;
import com.myapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/director")
public class DirectorController {
    final EmployeeService service;

    @Autowired
    public DirectorController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/main")
    public String directorMainPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee tempDirector = (Employee) session.getAttribute("director");
        Employee director = service.getEmployeeById(tempDirector.getId());
        List<Employee> employees = service.getAllEmployees();
        employees.forEach(employee -> employee.setRole(employee.getRole().substring(5)));
        model.addAttribute("director", director);
        model.addAttribute("employees", employees);
        return "director-pages/director-page";
    }
}
