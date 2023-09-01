package com.bgsbu.trackit.wisely.controller;

import com.bgsbu.trackit.wisely.payload.ResultSenderDto;
import com.bgsbu.trackit.wisely.service.ResultServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/result")
public class ResultController {

    private final ResultServices resultServices;

    @Autowired
    public ResultController(ResultServices resultServices) {
        this.resultServices = resultServices;
    }

    // http://localhost:8080/api/result/declare
    @PostMapping("/declare")
    public ResponseEntity<?> declareResult(
            @RequestBody ResultSenderDto resultSenderDto){

        System.out.println(resultSenderDto.getInternalMarks());
        return ResponseEntity.ok(resultServices.declareResult(resultSenderDto));

    }

    // http://localhost:8080/api/result/show/
    @GetMapping("/show")
    public ResponseEntity<?> getAllSemesterResults(
            @RequestBody ResultSenderDto resultSenderDto){

        return ResponseEntity.ok(resultServices.getResult(resultSenderDto));

    }

    // http://localhost:8080/api/result/show/1
    @GetMapping("/show/{semesterNumber}")
    public ResponseEntity<?> getPerSemesterResult(
            @PathVariable("semesterNumber") int semesterNumber,
            @RequestBody ResultSenderDto resultSenderDto){

        return ResponseEntity.ok(resultServices.getPerSemesterResult(semesterNumber,resultSenderDto));

    }

    //http://localhost:8080/api/result/Stats/01-MCA-2021
    @GetMapping("/Stats/{rollNumber}")
    public ResponseEntity<?> getReports(
            @PathVariable("rollNumber")String rollNumber){



        return  ResponseEntity.ok(resultServices.getReports(rollNumber));
    }

    //http://localhost:8080/api/result/update
    @PutMapping("/update")
    public ResponseEntity<?> updateResult(
            @RequestBody ResultSenderDto resultSenderDto){

        System.out.println(resultSenderDto.getExternalMarks());
        return ResponseEntity.ok(resultServices.updateResult(resultSenderDto));

    }

}
