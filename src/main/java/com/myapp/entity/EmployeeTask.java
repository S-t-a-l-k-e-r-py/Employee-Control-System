package com.myapp.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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

    public EmployeeTask(String taskTitle, String task, Date timeLimitation) {
        this.title = taskTitle;
        this.task = task;
        this.timeLimitation = timeLimitation;
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
