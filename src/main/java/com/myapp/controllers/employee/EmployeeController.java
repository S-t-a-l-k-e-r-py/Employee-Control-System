package com.myapp.controllers.employee;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeTask;
import com.myapp.exceptions.NotFoundException;
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

    @GetMapping("/main")
    public String employeeMainPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee tempEmployee = (Employee) session.getAttribute("employee");
        Employee employee = service.getEmployeeById(tempEmployee.getId());
        employee.setTasks(service.getTasksByEmployeeId(employee.getId()));
        model.addAttribute("emp", employee);
        return "employee-pages/employee-page";
    }

    @GetMapping("/task/{id}")
    public String getTask(@PathVariable(name = "id") int id, Model model, HttpServletRequest request){
        if (checkLegalAccess(id, request)) {
            throw new NotFoundException("task not find");
        }
        EmployeeTask task = service.getTaskById(id);
        task.setTitle(task.getTitle().substring(0, 1).toUpperCase() + task.getTitle().substring(1));
        model.addAttribute("task", task);
        return "employee-pages/task-page";
    }


    @PostMapping("/complete/{id}")
    public String completeTask(@PathVariable("id") int id, HttpServletRequest request) {
        if (checkLegalAccess(id, request)) {
            throw new NotFoundException("task not find");
        }
        EmployeeTask task = service.getTaskById(id);
        task.setComplete(true);
        service.updateTask(task);
        return "redirect:/employee/main";
    }

    private boolean checkLegalAccess(int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        return service.getTaskById(id).getEmpId() != employee.getId();
    }

}
