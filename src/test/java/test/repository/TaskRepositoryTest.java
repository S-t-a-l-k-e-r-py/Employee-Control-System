package test.repository;

import com.myapp.config.AppConfig;
import com.myapp.entity.EmployeeTask;
import com.myapp.repositoty.TaskRepo.TaskRepositoryImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.times;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class TaskRepositoryTest {

    private final TaskRepositoryImpl repository;

    public TaskRepositoryTest() {
        repository = Mockito.mock(TaskRepositoryImpl.class);
    }

    @Test
    public void getTasksByEmployeeIdTest() {
        List<EmployeeTask> tasks = new ArrayList<>();
        tasks.add(new EmployeeTask("title1", "task1", new Date()));
        tasks.add(new EmployeeTask("title2", "task2", new Date()));
        tasks.add(new EmployeeTask("title3", "task3", new Date()));
        Mockito.when(repository.getTasksByEmployeeId(1)).thenReturn(tasks);
        Assert.assertEquals(repository.getTasksByEmployeeId(1), tasks);
    }

    @Test
    public void getTaskByIdTest() {
        repository.getTaskById(1);
        Mockito.verify(repository, times(1)).getTaskById(1);
    }

    @Test
    public void deleteTaskByIdTest() {
        repository.deleteTaskById(1);
        Mockito.verify(repository, times(1)).deleteTaskById(1);
    }

    @Test
    public void updateTaskTest() {
        EmployeeTask task = new EmployeeTask("title1", "task1", new Date());
        repository.updateTask(task);
        Mockito.verify(repository, times(1)).updateTask(task);
    }

    @Test
    public void addTaskTest() {
        EmployeeTask task = new EmployeeTask("title1", "task1", new Date());
        repository.addTask(task, 1);
        Mockito.verify(repository, times(1)).addTask(task, 1);
    }

}

