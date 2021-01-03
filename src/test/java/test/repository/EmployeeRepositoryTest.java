package test.repository;

import com.myapp.config.AppConfig;
import com.myapp.entity.Employee;
import com.myapp.repositoty.EmployeeRepo.EmployeeRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class EmployeeRepositoryTest {

    private final EmployeeRepositoryImpl repository;

    public EmployeeRepositoryTest() {
        repository = Mockito.mock(EmployeeRepositoryImpl.class);
    }

    @Test
    public void getEmployeeByUserNameTest() {
        Employee emp = new Employee("Max", "Ivanov", "someRandomUser1234");
        Mockito.when(repository.getEmployeeByUserName("someRandomUser1234")).thenReturn(emp);

        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        Assert.assertEquals(repository.getEmployeeByUserName(employee.getUserName()), employee);
    }

    @Test
    public void deleteEmployeeByIdTest() {
        repository.deleteEmployeeById(1);
        Mockito.verify(repository, times(1)).deleteEmployeeById(1);
    }

    @Test
    public void saveEmployeeTest() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        repository.saveEmployee(employee);
        Mockito.verify(repository, times(1)).saveEmployee(employee);
    }

    @Test
    public void updateEmployeeTest() {
        Employee employee = new Employee("Max", "Ivanov", "someRandomUser1234");
        repository.updateEmployee(employee);
        Mockito.verify(repository, times(1)).updateEmployee(employee);
    }

    @Test
    public void getEmployeeByIdTest() {
        Employee emp = new Employee("Max", "Ivanov", "someRandomUser1234");
        Mockito.when(repository.getEmployeeById(1)).thenReturn(emp);

        Assert.assertEquals(repository.getEmployeeById(1), emp);
    }

    @Test
    public void getAllEmployeesTest() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee("Max", "Ivanov", "someRandomUser1234"));
        employees.add(new Employee("FirstName", "LastName", "user1234"));
        Mockito.when(repository.getAllEmployees()).thenReturn(employees);
        Assert.assertEquals(repository.getAllEmployees(), employees);
    }

}
