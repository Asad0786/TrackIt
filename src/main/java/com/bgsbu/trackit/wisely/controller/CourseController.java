package com.bgsbu.trackit.wisely.controller;

import com.bgsbu.trackit.wisely.payload.CourseSenderDto;
import com.bgsbu.trackit.wisely.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/programme/{progId}/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // http://localhost:8080/api/programme/1/course/add
    @PostMapping("/add")
    public ResponseEntity<?> newCourse(
            @PathVariable("progId") long progId,
            @RequestBody CourseSenderDto courseSenderDto){

        return ResponseEntity.ok(courseService.addNewCourse(progId,courseSenderDto));
    }

    // http://localhost:8080/api/programme/1/course/all
    @GetMapping("/all")
    public ResponseEntity<?> getAllCourses(
            @PathVariable("progId") long progrId){

        return ResponseEntity.ok(courseService.getAllCourses(progrId));
    }

    // http://localhost:8080/api/programme/1/course/1
    @GetMapping("/{courId}")
    public ResponseEntity<?> getCourse(
            @PathVariable("progId") long progrId,
            @PathVariable("courId") long courseId){

        return ResponseEntity.ok(courseService.getCourse(progrId, courseId));
    }

    // http://localhost:8080/api/programme/1/course/1/edit
    @PutMapping("/{courId}/edit")
    public ResponseEntity<?> editCourse(
            @PathVariable("progId") long progrId,
            @PathVariable("courId") long courseId,
            @RequestBody CourseSenderDto courseSenderDto){

        return ResponseEntity.ok(courseService.editCourse(progrId, courseId, courseSenderDto));
    }
}
