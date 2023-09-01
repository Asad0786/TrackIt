package com.bgsbu.trackit.wisely.repository;

import com.bgsbu.trackit.wisely.entity.Course;
import com.bgsbu.trackit.wisely.entity.Programme;
import com.bgsbu.trackit.wisely.entity.Syllabus;
import com.bgsbu.trackit.wisely.model.SyllabusResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SyllabusRepository extends JpaRepository<Syllabus,Long> {

    List<Syllabus> findByProgramme(Programme programme);

    Syllabus findBySemesterNumber(int sem);

    boolean existsBySemesterNumber(int sem);

    Syllabus findByCourse(Course course);
}
