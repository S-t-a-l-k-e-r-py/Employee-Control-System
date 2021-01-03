package test.service;

import com.myapp.config.AppConfig;
import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeAccount;
import com.myapp.entity.EmployeeTask;
import com.myapp.repositoty.EmployeeRepo.EmployeeRepositoryImpl;
import com.myapp.repositoty.TaskRepo.TaskRepositoryImpl;
import com.myapp.service.EmployeeServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class EmployeeServiceTest {
    @Mock
    private EmployeeRepositoryImpl employeeRepository;

    @Mock
    private TaskRepositoryImpl taskRepository;

    @InjectMocks
    private EmployeeServiceImpl service;

    //EmployeeRepository TEST

    //Service getAllEmployees method test
    @Test
    public void getAllEmployeesTest() {
        employeeRepository.getAllEmployees();
        Mockito.verify(employeeRepository, times(1)).getAllEmployees();
    }

    //Service getEmployeeByUserName method tests
    @Test(expected = RuntimeException.class)
    public void getEmployeeByUserNameTest_Trow_RuntimeException() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        when(employeeRepository.getEmployeeByUserName(employee.getUserName())).thenReturn(null);
        service.getEmployeeByUserName(employee.getUserName());
    }

    @Test
    public void getEmployeeByUserNameTest() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        when(employeeRepository.getEmployeeByUserName(employee.getUserName())).thenReturn(employee);
        Assert.assertEquals(service.getEmployeeByUserName(employee.getUserName()), employee);
    }

    //Service getEmployeeById method tests
    @Test
    public void getEmployeeByIdTest() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);
        Assert.assertEquals(service.getEmployeeById(1), employee);
    }

    @Test(expected = RuntimeException.class)
    public void getEmployeeByIdTest_Throw_Exception() {
        when(employeeRepository.getEmployeeById(1)).thenReturn(null);
        service.getEmployeeById(1);
    }

    //Service deleteEmployeeById method tests
    @Test
    public void deleteEmployeeByIdTest() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);
        service.deleteEmployeeById(1);
    }

    @Test(expected = RuntimeException.class)
    public void deleteEmployeeByIdTest_Throw_Exception() {
        when(employeeRepository.getEmployeeById(1)).thenReturn(null);
        service.deleteEmployeeById(1);
    }

    //Service saveEmployee method tests
    @Test
    public void saveEmployeeTest1() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        employee.setAccount(new EmployeeAccount("someEmail@gmail.com", "1234"));
        service.saveEmployee(employee);
        Mockito.verify(employeeRepository, times(1)).saveEmployee(employee);
    }

    @Test
    public void saveEmployeeTest2() {
        service.saveEmployee(null);
        Mockito.verify(employeeRepository, times(0)).saveEmployee(null);
    }

    @Test(expected = RuntimeException.class)
    public void saveEmployeeTest1_Throw_Exception() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        service.saveEmployee(employee);
    }

    //Service updateEmployee method tests
    @Test
    public void updateEmployeeTest1() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        service.updateEmployee(employee);
        Mockito.verify(employeeRepository, times(1)).updateEmployee(employee);
    }

    @Test
    public void updateEmployeeTest2() {
        service.updateEmployee(null);
        Mockito.verify(employeeRepository, times(0)).updateEmployee(null);
    }

    //TaskRepository TEST

    //Service getTasksByEmployeeId method test
    @Test
    public void getTasksByEmployeeIdTest() {
        taskRepository.getTasksByEmployeeId(1);
        Mockito.verify(taskRepository, times(1)).getTasksByEmployeeId(1);
    }

    //Service getTasksByEmployeeId method tests
    @Test
    public void getTaskByIdTest() {
        EmployeeTask task = new EmployeeTask("title", "text", new Date());
        when(taskRepository.getTaskById(1)).thenReturn(task);
        Assert.assertEquals(service.getTaskById(1), task);
    }

    @Test(expected = RuntimeException.class)
    public void getTaskByIdTest_Throw_Exception() {
        when(taskRepository.getTaskById(1)).thenReturn(null);
        service.getTaskById(1);
    }

    //Service addTask method tests
    @Test
    public void addTaskTest() {
        EmployeeTask task = new EmployeeTask("title", "text", new Date());
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);
        service.addTask(task, 1);
    }

    @Test(expected = RuntimeException.class)
    public void addTaskTest1_Throw_Exception() {
        EmployeeTask task = new EmployeeTask("title", "text", new Date());
        when(employeeRepository.getEmployeeById(1)).thenReturn(null);
        service.addTask(task, 1);
    }

    @Test(expected = RuntimeException.class)
    public void addTaskTest2_Throw_Exception() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        when(employeeRepository.getEmployeeById(1)).thenReturn(employee);
        service.addTask(null, 1);
    }

    //Service updateTask method tests
    @Test
    public void updateTaskTest1() {
        EmployeeTask task = new EmployeeTask("title", "text", new Date());
        service.updateTask(task);
        Mockito.verify(taskRepository, times(1)).updateTask(task);
    }

    @Test
    public void updateTaskTest2() {
        service.updateTask(null);
        Mockito.verify(taskRepository, times(0)).updateTask(null);
    }

    //Service deleteTaskById method tests
    @Test
    public void deleteTaskByIdTest() {
        EmployeeTask task = new EmployeeTask("title", "text", new Date());
        when(taskRepository.getTaskById(1)).thenReturn(task);
        service.getTaskById(1);
    }

    @Test(expected = RuntimeException.class)
    public void deleteTaskByIdTest_Throw_Exception() {
        when(taskRepository.getTaskById(1)).thenReturn(null);
        service.getTaskById(1);
    }

    //Service loadUserByUsername method tests
    @Test
    public void loadUserByUsername() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        when(employeeRepository.getEmployeeByUserName(employee.getUserName())).thenReturn(employee);
        service.getEmployeeByUserName("someRandomUser1234");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_Throw_UsernameNotFoundException() {
        when(employeeRepository.getEmployeeByUserName("someRandomUser1234")).thenReturn(null);
        service.loadUserByUsername("someRandomUser1234");
    }


}
