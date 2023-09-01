package com.bgsbu.trackit.wisely.repository;

import com.bgsbu.trackit.wisely.entity.Course;
import com.bgsbu.trackit.wisely.entity.Result;
import com.bgsbu.trackit.wisely.entity.StudentEnrollment;
import com.bgsbu.trackit.wisely.model.ResultResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Long> {
    boolean existsByRollNumber(String rollNumber);

    List<Result> findByRollNumber(String rollNumber);

    boolean existsByRollNumberAndCourse(String rollNumber, Course byCourseCode);

    List<Result> findAllByCourseInAndStudentEnrollment(List<Course> courses, StudentEnrollment byRollNumber);

    Result findByCourseAndStudentEnrollment(Course byCourseCode, StudentEnrollment byRollNumber);

    boolean existsByCourseAndStudentEnrollment(Course byCourseCode, StudentEnrollment byRollNumber);
}
