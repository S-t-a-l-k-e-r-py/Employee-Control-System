package com.myapp.controllers;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeAccount;
import com.myapp.entity.EmployeeData;
import com.myapp.entity.Role;
import com.myapp.service.EmployeeService;
import com.myapp.validation.TempEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/reg")
public class RegistrationController {

    private final EmployeeService service;

    @Autowired
    public RegistrationController(EmployeeService service) {

        this.service = service;
    }

    @GetMapping("/getRegistration")
    public String getLoginPage(Model model) {
        model.addAttribute("emp", new TempEmployee());
        return "registration-pages/reg-page";
    }

    @PostMapping("/postRegistration")
    public String processRegistrationForm(
            @Valid @ModelAttribute("emp") TempEmployee employee,
            BindingResult theBindingResult,
            Model theModel) {
        String userName = employee.getUserName();
        if (theBindingResult.hasErrors()) {
            return "registration-pages/reg-page";
        }
        Employee temp = service.getEmployeeByUserName(userName);
        if (temp != null) {
            theModel.addAttribute("emp", employee);
            theModel.addAttribute("registrationError", "error");
            return "registration-pages/reg-page";
        }
        service.saveEmployee(createEmployeeToSave(employee));
        return "registration-pages/reg-success";
    }

    private Employee createEmployeeToSave(TempEmployee tempEmployee) {

        Employee employee = new Employee(
                tempEmployee.getFirstName().substring(0, 1).toUpperCase() + tempEmployee.getFirstName().substring(1).toLowerCase(),
                tempEmployee.getLastName().substring(0, 1).toUpperCase() + tempEmployee.getLastName().substring(1).toLowerCase(),
                tempEmployee.getUserName());

        EmployeeAccount account =
                new EmployeeAccount(tempEmployee.getEmail(), tempEmployee.getPassword());
        EmployeeData data =
                new EmployeeData(0, Integer.parseInt(tempEmployee.getAge()));

        employee.setAccount(account);
        employee.setData(data);
        employee.setRole(Role.ROLE_EMPLOYEE.toString());
        return employee;
    }


}
