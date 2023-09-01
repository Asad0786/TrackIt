package com.bgsbu.trackit.wisely.repository;

import com.bgsbu.trackit.wisely.entity.Batch;
import com.bgsbu.trackit.wisely.entity.Programme;
import com.bgsbu.trackit.wisely.entity.SemesterCourses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SemesterCoursesRepository extends JpaRepository<SemesterCourses, Long> {
    List<SemesterCourses> findByProgramme(Programme programme);
    SemesterCourses findByProgrammeAndSemesterNumberAndBatch(Programme programme, int semesterNumber, Batch batch);
    SemesterCourses findByCourses_CourseCode(String courseCode);
}
