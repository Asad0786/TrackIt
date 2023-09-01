//package com.bgsbu.trackit.wisely.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name="student_semester_courses")
//public class StudentSemesterCoursesEnrollment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
//
//    @ManyToOne
//    @JoinColumn(name = "enrolled_student_id")
//    private StudentEnrollment studentEnrollment;
//
//
//    @ManyToOne
//    @JoinColumn(name = "semester_courses_id")
//    private SemesterCourses semesterCourses;
//}
