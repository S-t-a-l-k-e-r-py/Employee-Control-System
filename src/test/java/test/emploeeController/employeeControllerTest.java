package test.emploeeController;

import com.myapp.config.AppConfig;
import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeTask;
import com.myapp.exceptions.NotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This test not working now
 * (Because service doesn't mock in controller at runtime)
 * I will repair it soon
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class employeeControllerTest {

    @Autowired
    private WebApplicationContext context;

    MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    //employeeMainPage method tests
    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"EMPLOYEE"})
    public void get_main_with_auth_user_Except_status_200() throws Exception {
        mockMvc.perform(get("/employee/main")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "1234")
    public void get_main_with_noAuth_user_Except_status_403() throws Exception {
        mockMvc.perform(get("/employee/main")).andExpect(status().isForbidden());
    }

    //getTask method tests
    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"EMPLOYEE"})
    public void getTask_Except_Success() throws Exception {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        EmployeeTask task = new EmployeeTask("1", "text", new Date());
        task.setEmpId(2);
        employee.setId(2);
        mockMvc.perform(get("/employee/task/{id}", 2).sessionAttr("employee", employee)).andExpect(status().isOk())
                .andExpect(model().attributeExists("task"));
    }

    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"EMPLOYEE"})
    public void getTask_Excepted_NotFoundException() throws Exception {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        EmployeeTask task = new EmployeeTask("title", "text", new Date());
        task.setEmpId(2);
        employee.setId(1);
        mockMvc.perform(get("/employee/task/{id}", 2).sessionAttr("employee", employee)).andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    //completeTask method tests
    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"EMPLOYEE"})
    public void completeTask_Excepted_Redirection() throws Exception {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        EmployeeTask task = new EmployeeTask("title", "text", new Date());
        task.setEmpId(2);
        employee.setId(2);
        mockMvc.perform(post("/employee/complete/{id}", 2).with(csrf()).sessionAttr("employee", employee))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"EMPLOYEE"})
    public void completeTask_Excepted_NotFoundException() throws Exception {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        EmployeeTask task = new EmployeeTask("title", "text", new Date());
        task.setEmpId(2);
        employee.setId(1);
        mockMvc.perform(post("/employee/complete/{id}", 2).with(csrf()).sessionAttr("employee", employee))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));

    }


}
