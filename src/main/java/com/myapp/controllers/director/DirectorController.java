package com.myapp.controllers.director;

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
import java.util.List;
import java.util.stream.Collectors;

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
        List<Employee> employees = service.getAllEmployees().stream().filter(e -> !e.getRole().equals("DIRECTOR"))
                .collect(Collectors.toList());
        model.addAttribute("director", director);
        model.addAttribute("employees", employees);
        return "director-pages/director-main-page";
    }

    @GetMapping("/employee-detail/{id}")
    public String getEmployee(@PathVariable(name = "id") int id, Model model) {
        Employee employee = service.getEmployeeById(id);
        if (employee.getRole().equals("DIRECTOR")) {
            throw new NotFoundException("employee not found");
        }
        List<EmployeeTask> tasks = service.getTasksByEmployeeId(id);
        model.addAttribute("allTasks", tasks.size());
        model.addAttribute("completedTasks", tasks.stream().filter(EmployeeTask::isComplete).count());
        model.addAttribute("failedTasks", tasks.stream().filter(EmployeeTask::isFailed).count());
        model.addAttribute("emp", employee);
        return "director-pages/director-work-page";
    }

    @PostMapping("/employee-detail/to-fire")
    public String fireEmployee(@ModelAttribute(name = "emp") Employee emp){
        Employee employee = service.getEmployeeById(emp.getId());
        if(employee.getRole().equals("DIRECTOR")){
            throw new NotFoundException("employee not found");
        }
        service.deleteEmployeeById(emp.getId());
        return "redirect:/director/main";
    }

    @PostMapping("/employee-detail/change-salary")
    public String changeSalary(@ModelAttribute(name = "emp") Employee emp){
        Employee employee = service.getEmployeeById(emp.getId());
        if(employee.getRole().equals("DIRECTOR")){
            throw new NotFoundException("employee not found");
        }
        employee.getData().setSalary(emp.getData().getSalary());
        service.updateEmployee(employee);
        return "redirect:/director/main";
    }
}
