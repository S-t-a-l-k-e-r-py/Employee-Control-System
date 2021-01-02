package test.service;

import com.myapp.config.AppConfig;
import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeAccount;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

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
}
