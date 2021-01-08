package com.myapp.controllers.admin;

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
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    final EmployeeService service;

    @Autowired
    public AdminController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/main")
    public String adminMainPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee tempAdmin = (Employee) session.getAttribute("admin");
        Employee admin = service.getEmployeeById(tempAdmin.getId());
        List<Employee> employees = service.getAllEmployees();
        employees.forEach(employee -> employee.setRole(employee.getRole().substring(5)));
        model.addAttribute("admin", admin);
        model.addAttribute("employees", employees);
        return "admin-pages/admin-page";
    }

    @GetMapping("/employee-detail/{id}")
    public String getEmployee(@PathVariable(name = "id") int id, Model model, HttpServletRequest request) {
        if (checkLegalAccess(id, request)) {
            throw new NotFoundException("employee not found");
        }
        Employee employee = service.getEmployeeById(id);
        HttpSession session = request.getSession();
        session.setAttribute("employee", employee);
        List<EmployeeTask> tasks = service.getTasksByEmployeeId(id);
        tasks.forEach(task -> task.setTitle(task.getTitle().substring(0, 1).toUpperCase() + task.getTitle().substring(1)));
        employee.setTasks(tasks);
        model.addAttribute("emp", employee);
        return "admin-pages/admin-control-page";
    }

    @GetMapping("/employee-detail/edit-task/{id}")
    public String editTask(@PathVariable(name = "id") int id, Model model, HttpServletRequest request) {
        if (checkLegalAccess(id, request)) {
            throw new NotFoundException("task not found");
        }
        EmployeeTask task;
        if (id == 0) {
            task = new EmployeeTask();
        } else {
            task = service.getTaskById(id);
        }
        model.addAttribute("task", task);
        return "admin-pages/edit-task";
    }

    @PostMapping("/employee-detail/save-task")
    public String saveTask(@ModelAttribute(name = "task") EmployeeTask task, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(task.getTimeLimitation().getTime()<new Date().getTime()){
            return "redirect:/admin/main";
        }
        Employee employee = (Employee) session.getAttribute("employee");
        task.setEmpId(employee.getId());
        if (task.getId() != 0) {
            service.updateTask(task);
        } else {
            service.addTask(task, employee.getId());
        }
        return "redirect:/admin/main";
    }

    @PostMapping("/employee-detail/delete-task")
    public String deleteTask(@ModelAttribute(name = "task") EmployeeTask task) {
        if (task.getId() == 0) {
            throw new NotFoundException("task not found");
        }
        service.deleteTaskById(task.getId());
        return "redirect:/admin/main";
    }

    private boolean checkLegalAccess(int id, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee admin = (Employee) session.getAttribute("admin");
        return admin.getId() == id;
    }
}
