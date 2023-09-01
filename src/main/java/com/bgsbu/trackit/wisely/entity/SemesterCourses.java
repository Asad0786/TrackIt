package com.bgsbu.trackit.wisely.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "semester_courses")
public class SemesterCourses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToMany
    @JoinTable(
            name = "semester_course_mapping",
            joinColumns = @JoinColumn(name = "semester_courses_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

    @ManyToOne
    @JoinColumn(name = "programme_id")
    private Programme programme;

    @Column(name = "semester")
    private int semesterNumber;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;


}
