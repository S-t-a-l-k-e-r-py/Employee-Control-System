package com.myapp.config.security;

import com.myapp.entity.Employee;
import com.myapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
public class AuthenticationHandler implements AuthenticationSuccessHandler {
    private final EmployeeService service;

    @Autowired
    public AuthenticationHandler(EmployeeService service) {
        this.service = service;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String userName = authentication.getName();
        Employee user = service.getEmployeeByUserName(userName);
        HttpSession session = request.getSession();
        switch (user.getRole()) {
            case "EMPLOYEE":
                session.setAttribute("employee", user);
                response.sendRedirect(request.getContextPath() + "/employee/main");
                break;
            case "ADMIN":
                session.setAttribute("admin", user);
                response.sendRedirect(request.getContextPath() + "/admin/main");
                break;
            case "DIRECTOR":
                session.setAttribute("director", user);
                response.sendRedirect(request.getContextPath() + "/director/main");
                break;
        }
    }
}
