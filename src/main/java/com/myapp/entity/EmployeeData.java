package com.myapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "employee_data")
public class EmployeeData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "salary")
    private int salary;
    @Column(name = "age")
    private int age;


    public EmployeeData() {
    }

    public EmployeeData(int salary, int age) {
        this.salary = salary;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "EmployeeData{" +
                "id=" + id +
                ", salary=" + salary +
                ", age=" + age +
                '}';
    }
}
