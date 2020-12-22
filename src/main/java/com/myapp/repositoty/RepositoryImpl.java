package com.myapp.repositoty;

import com.myapp.entity.Employee;
import com.myapp.entity.EmployeeTask;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RepositoryImpl implements EmployeeRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Employee> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT emp FROM Employee emp", Employee.class).getResultList();
    }

    @Override
    public void save(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        System.out.println(employee);
        session.save(employee);
    }

    @Override
    public void update(Employee employee) {
        Session session = sessionFactory.getCurrentSession();
        session.update(employee);
    }

    @Override
    public Employee getByUserName(String name) {
        Session session = sessionFactory.getCurrentSession();
        Query<Employee> query =
                session.createQuery("SELECT emp FROM Employee emp  WHERE emp.userName=:name", Employee.class);
        query.setParameter("name",name);
        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Employee getById(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Employee.class, id);
    }

    @Override
    public void deleteById(int id) {
        Session session = sessionFactory.getCurrentSession();
        Employee employee = session.get(Employee.class, id);
        session.delete(employee);
    }

    @Override
    public void addTask(EmployeeTask task, int employeeId) {
        Session session = sessionFactory.getCurrentSession();
        Employee employee = session.get(Employee.class, employeeId);
        employee.addTask(task);
        session.saveOrUpdate(employee);
    }
}
