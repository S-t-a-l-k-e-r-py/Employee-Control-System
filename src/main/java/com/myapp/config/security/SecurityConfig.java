package com.myapp.config.security;

import com.myapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final EmployeeService service;
    private final AuthenticationHandler authenticationHandler;

    @Autowired
    public SecurityConfig(EmployeeService service, AuthenticationHandler handler) {
        this.service = service;
        this.authenticationHandler = handler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                    .antMatchers("/employee/**").hasRole("EMPLOYEE")
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/director/**").hasRole("DIRECTOR")
                .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticateUser")
                    .successHandler(authenticationHandler)
                    .permitAll()
                .and()
                    .rememberMe().tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(10))
                    .key("someAuthenticationKey")
                    .userDetailsService(service)
                .and()
                    .logout()
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                .and()
                    .exceptionHandling().accessDeniedPage("/access-denied");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(service);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

}
