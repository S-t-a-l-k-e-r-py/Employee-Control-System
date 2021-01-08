package test.adminController;

import com.myapp.config.AppConfig;
import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeTask;
import com.myapp.exceptions.NotFoundException;
import com.myapp.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
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
@ContextConfiguration(classes = AppConfig.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class adminControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Mock
    private EmployeeService service;

    MockMvc mockMvc;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        MockitoAnnotations.openMocks(this);
    }

    //employeeMainPage method tests
    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"ADMIN"})
    public void get_main_with_auth_user_Except_status_200() throws Exception {
        mockMvc.perform(get("/admin/main")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", password = "1234")
    public void get_main_with_noAuth_user_Except_status_403() throws Exception {
        mockMvc.perform(get("/admin/main")).andExpect(status().isForbidden());
    }

    //getEmployee method test
    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"ADMIN"})
    public void getEmployee_Except_Success() throws Exception {
        Employee admin = new Employee("Max", "Ivanov", "someRandomUser1234");
        admin.setId(2);
        when(service.getEmployeeById(1)).thenReturn(new Employee("dsfdf", "sfdf", "sdfsd"));
        mockMvc.perform(get("/admin/employee-detail/{id}", 1).sessionAttr("admin", admin)).andExpect(status().isOk())
                .andExpect(model().attributeExists("emp"));
    }

    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"ADMIN"})
    public void getEmployee_Excepted_NotFoundException() throws Exception {
        Employee admin = new Employee("Max", "Ivanov", "someRandomUser1234");
        admin.setId(3);
        mockMvc.perform(get("/admin/employee-detail/{id}", 3).sessionAttr("admin", admin)).andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    //editTask method test
    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"ADMIN"})
    public void editTask_Except_Success() throws Exception {
        Employee admin = new Employee("Max", "Ivanov", "someRandomUser1234");
        admin.setId(1);
        mockMvc.perform(get("/admin/employee-detail/edit-task/{id}", 2).sessionAttr("admin", admin)).andExpect(status().isOk())
                .andExpect(model().attributeExists("task"));
    }

    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"ADMIN"})
    public void editTask_Excepted_NotFoundException() throws Exception {
        Employee admin = new Employee("Max", "Ivanov", "someRandomUser1234");
        admin.setId(1);
        mockMvc.perform(get("/admin/employee-detail/edit-task/{id}", 1).sessionAttr("admin", admin)).andExpect(status().isOk())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    //saveTask method test
    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"ADMIN"})
    public void saveTask_Except_Success() throws Exception {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        employee.setId(1);
        mockMvc.perform(post("/admin/employee-detail/save-task", 1).with(csrf())
                .sessionAttr("employee", employee)
                .flashAttr("task", new EmployeeTask("title", "text", new Date())))
                .andExpect(status().is3xxRedirection());
    }

    //deleteTask method test
    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"ADMIN"})
    public void deleteTask_Except_Success() throws Exception {
        EmployeeTask task = new EmployeeTask("title", "text", new Date());
        task.setId(1);
        mockMvc.perform(post("/admin/employee-detail/delete-task").with(csrf())
                .flashAttr("task", task))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user", password = "1234", roles = {"ADMIN"})
    public void deleteTask_Excepted_NotFoundException() throws Exception {
        EmployeeTask task = new EmployeeTask("title", "text", new Date());
        task.setId(0);
        mockMvc.perform(post("/admin/employee-detail/delete-task").with(csrf())
                .flashAttr("task", task))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }
}
