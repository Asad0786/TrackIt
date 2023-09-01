package com.bgsbu.trackit.wisely.repository;


import com.bgsbu.trackit.wisely.entity.Course;
import com.bgsbu.trackit.wisely.entity.Programme;
import com.bgsbu.trackit.wisely.entity.Syllabus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByCourseCode(String courseCode);

    boolean existsByCourseCode(String courseCode);

    List<Course> findByProgramme(Programme programme);

    List<Course> findAllByCourseCodeIn(List<String> courseCode);
}
