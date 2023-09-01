package com.bgsbu.trackit.wisely.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "student_enrollment_id")
    private StudentEnrollment studentEnrollment;

    @Column(name = "roll_number")
    private String rollNumber;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "internal_marks")
    private Integer internalMarks;

    @Column(name = "external_marks")
    private Integer externalMarks;

}
