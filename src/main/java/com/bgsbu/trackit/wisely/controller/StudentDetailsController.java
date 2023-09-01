package com.bgsbu.trackit.wisely.controller;

import com.bgsbu.trackit.wisely.payload.StudentDetailsSenderDto;
import com.bgsbu.trackit.wisely.repository.StudentDetailsRepository;
import com.bgsbu.trackit.wisely.service.StudentDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
public class StudentDetailsController {

    private final StudentDetailsServices studentDetailsServices;
    private final StudentDetailsRepository studentDetailsRepository;

    @Autowired
    public StudentDetailsController(StudentDetailsServices studentDetailsServices, StudentDetailsRepository studentDetailsRepository) {
        this.studentDetailsServices = studentDetailsServices;
        this.studentDetailsRepository = studentDetailsRepository;
    }

    // http://localhost:8080/api/student/register
    @PostMapping("/register")
    public ResponseEntity<?> newRegister(
            @RequestBody StudentDetailsSenderDto studentDetailsSenderDto){

        if(studentDetailsRepository.existsByEmail(studentDetailsSenderDto.getEmail()))
            return ResponseEntity.ok("User with this email already exists");

        return ResponseEntity.ok(studentDetailsServices.registerStudent(studentDetailsSenderDto));

    }

    @GetMapping("/getDetails")
    public ResponseEntity<?> getDetails(
            @RequestParam("email") String email){

        return ResponseEntity.ok(studentDetailsServices.getSudentDetails(email));

    }



}
