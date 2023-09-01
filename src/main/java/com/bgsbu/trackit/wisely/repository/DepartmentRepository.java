package com.bgsbu.trackit.wisely.repository;

import com.bgsbu.trackit.wisely.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
