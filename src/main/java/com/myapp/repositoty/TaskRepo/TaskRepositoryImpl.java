package com.myapp.repositoty.TaskRepo;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public TaskRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addTask(EmployeeTask task, int employeeId) {
        Session session = sessionFactory.getCurrentSession();
        Employee employee = session.get(Employee.class, employeeId);
        employee.addTask(task);
        session.saveOrUpdate(employee);
    }

    @Override
    public void updateTask(EmployeeTask task) {
        Session session = sessionFactory.getCurrentSession();
        session.update(task);
    }
    @Override
    public void deleteTaskById(int id){
        Session session = sessionFactory.getCurrentSession();
        EmployeeTask task = session.get(EmployeeTask.class,id);
        session.delete(task);
    }

    @Override
    public EmployeeTask getTaskById(int id) {
        Session session = sessionFactory.getCurrentSession();
        EmployeeTask task = session.get(EmployeeTask.class, id);
        checkTask(task);
        return task;
    }

    @Override
    public List<EmployeeTask> getTasksByEmployeeId(int id) {
        Session session = sessionFactory.getCurrentSession();
        Query<EmployeeTask> query =
                session.createQuery("SELECT task FROM EmployeeTask task  WHERE task.empId=:id", EmployeeTask.class);
        query.setParameter("id", id);
        try {
            query.getResultList().forEach(this::checkTask);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
        private void checkTask(EmployeeTask task) {

        if((!task.isComplete() && ! task.isFailed()) && task.getTimeLimitation().getTime()<= new Date().getTime()){
            task.setComplete(false);
            task.setFailed(true);
        }
    }
}
