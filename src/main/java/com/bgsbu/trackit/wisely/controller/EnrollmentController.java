package com.bgsbu.trackit.wisely.controller;

import com.bgsbu.trackit.wisely.payload.StudentDetailsSenderDto;
import com.bgsbu.trackit.wisely.payload.StudentEnrollmentSenderDto;
import com.bgsbu.trackit.wisely.service.StudentEnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enroll")
public class EnrollmentController {

    private final StudentEnrollmentService studentEnrollmentService;

    @Autowired
    public EnrollmentController(StudentEnrollmentService studentEnrollmentService) {
        this.studentEnrollmentService = studentEnrollmentService;
    }

    // http://localhost:8080/api/enroll/1

    @PostMapping("/{studentId}")
    public ResponseEntity<?> newEnrollment(
            @PathVariable("studentId") long studentId,
            @RequestBody StudentEnrollmentSenderDto studentEnrollmentSenderDto){

        return ResponseEntity.ok(studentEnrollmentService.enrollStudent(studentId,studentEnrollmentSenderDto));


    }

    // http://localhost:8080/api/enroll/promoteSemester/2/2021
    @PutMapping("/promoteSemester/{progId}/{batch}")
    public  ResponseEntity<?> updateSemester(
            @PathVariable("progId") long progId,
            @PathVariable("batch") int batch){

        return ResponseEntity.ok(studentEnrollmentService.updateSemester(progId, batch));
    }

    // http://localhost:8080/api/enroll/2/2021/getenrolls
    @GetMapping("/{progId}/{batch}/getenrolls")
    public ResponseEntity<?> getAllEnrolledStudentInProgramme(
            @PathVariable("progId") long progId,
            @PathVariable("batch") int batchStart){

        return ResponseEntity.ok(studentEnrollmentService.getAllEnrollsOfProg(progId, batchStart));

    }

}
