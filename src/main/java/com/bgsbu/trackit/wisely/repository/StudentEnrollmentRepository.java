package com.bgsbu.trackit.wisely.repository;

import com.bgsbu.trackit.wisely.entity.Batch;
import com.bgsbu.trackit.wisely.entity.Programme;
import com.bgsbu.trackit.wisely.entity.StudentDetails;
import com.bgsbu.trackit.wisely.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {

    List<StudentEnrollment> findAllByBatchAndProgrammeAndCurrentSemester(int batchStart, Programme programme, int semesterNumber);

    List<StudentEnrollment> findAllByBatchAndProgramme(Batch batch, Programme programme);

    boolean existsByRollNumber(String rollNumber);

    StudentEnrollment findByRollNumber(String rollNumber);

    boolean existsByStudent(StudentDetails studentDetails);
}
