package com.bgsbu.trackit.wisely.controller;

import com.bgsbu.trackit.wisely.payload.SemesterCourseSenderDto;
import com.bgsbu.trackit.wisely.service.SemesterCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programme/{pId}/semester")
public class SemesterCourseController {

    private final SemesterCoursesService semesterCoursesService;

    @Autowired
    public SemesterCourseController(SemesterCoursesService semesterCoursesService) {
        this.semesterCoursesService = semesterCoursesService;
    }

    // http://localhost:8080/api/programme/1/semester/add
    @PostMapping("/add")
    public ResponseEntity<?> defineCoursesPerSemester(
            @PathVariable("pId") long progId,
            @RequestBody SemesterCourseSenderDto semesterCourseSenderDto){

        return ResponseEntity.ok(semesterCoursesService.defineCourses(progId,semesterCourseSenderDto));
    }


    // http://localhost:8080/api/programme/1/semester/list
    @GetMapping("/list")
    public ResponseEntity<?> getAllCoursesSemesters(
            @PathVariable("pId") long progId){

        return ResponseEntity.ok(semesterCoursesService.getAllCoursesSemesters(progId));
    }



}
