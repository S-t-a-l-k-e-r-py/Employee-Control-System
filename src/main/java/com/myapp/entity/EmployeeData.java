package com.myapp.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
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

    public EmployeeData(int salary, int age) {
        this.salary = salary;
        this.age = age;
    }

}
