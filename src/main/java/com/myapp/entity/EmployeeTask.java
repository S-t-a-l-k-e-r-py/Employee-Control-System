package com.myapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "employee_task")
public class EmployeeTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "task")
    private String task;
    @Column(name = "time")
    private String timeLimitation;
    @Column(name = "iscomplete")
    private boolean isComplete;
    @Column(name = "isfailed")
    private boolean isFailed;


    public EmployeeTask() {
        checkToComplete();
    }

    public EmployeeTask(String taskTitle, String task, String timeLimitation) {
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

    public String getTimeLimitation() {
        return timeLimitation;
    }

    public void setTimeLimitation(String timeLimitation) {
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
    private void checkToComplete(){
//        if(!isComplete){
//            isComplete = false?
//        }
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
