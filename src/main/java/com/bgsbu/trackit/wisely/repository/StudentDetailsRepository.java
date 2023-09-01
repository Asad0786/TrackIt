package com.bgsbu.trackit.wisely.repository;

import com.bgsbu.trackit.wisely.entity.StudentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentDetailsRepository extends JpaRepository<StudentDetails,Long> {
    StudentDetails findByEmail(String email);

    boolean existsByEmail(String email);

}
