package com.bgsbu.trackit.wisely.repository;

import com.bgsbu.trackit.wisely.entity.Programme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
    boolean existsByProgrammeCode(String programmeCode);

    Programme findByProgrammeCode(String programmeCode);
}
