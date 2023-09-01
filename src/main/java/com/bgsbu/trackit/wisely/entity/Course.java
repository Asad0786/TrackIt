package com.bgsbu.trackit.wisely.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "courses")
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_name")
    private String courseName;

    @Column(name = "credits")
    private short credits;

    @Column(name = "is_practical")
    private boolean isPractical;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "programme_id")
    private Programme programme;

//    @Column(name = "syllabus_file_path")
//    private String syllabusFilePath;

}
