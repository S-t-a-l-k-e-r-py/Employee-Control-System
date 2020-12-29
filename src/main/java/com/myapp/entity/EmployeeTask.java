package com.myapp.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "employee_task")
public class EmployeeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "task")
    private String task;
    @Column(name = "time")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date timeLimitation;
    @Column(name = "emp_id")
    private int empId;
    @Column(name = "iscomplete")
    private boolean isComplete;
    @Column(name = "isfailed")
    private boolean isFailed;


    public EmployeeTask() {
    }

    public EmployeeTask(String taskTitle, String task, Date timeLimitation) {
        this.title = taskTitle;
        this.task = task;
        this.timeLimitation = timeLimitation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Date getTimeLimitation() {
        return timeLimitation;
    }

    public void setTimeLimitation(Date timeLimitation) {
        this.timeLimitation = timeLimitation;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isFailed() {
        return isFailed;
    }

    public void setFailed(boolean failed) {
        isFailed = failed;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    @Override
    public String toString() {
        return "EmployeeTask{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", task='" + task + '\'' +
                ", timeLimitation='" + timeLimitation + '\'' +
                '}';
    }
}
