package com.bgsbu.trackit.wisely.repository;

import com.bgsbu.trackit.wisely.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Long> {
    boolean existsByBatchStart(int batchStart);

    Batch findByBatchStart(int batchStart);
}
