package com.bgsbu.trackit.wisely.controller;

import com.bgsbu.trackit.wisely.entity.Batch;
import com.bgsbu.trackit.wisely.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchController {
    private final BatchRepository batchRepository;

    @Autowired
    public BatchController(BatchRepository batchRepository) {
        this.batchRepository = batchRepository;
    }

    // http://localhost:8080/api/batch/add
    @PostMapping("/add")
    public ResponseEntity<?> newBatch(@RequestBody Batch batch){
        return ResponseEntity.ok(batchRepository.save(batch));
    }

}
