package com.bgsbu.trackit.wisely.controller;

import com.bgsbu.trackit.wisely.payload.SyllabusSenderDto;
import com.bgsbu.trackit.wisely.service.SyllabusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/programme/{pid}/syllabus")
public class SyllabusController {


    private final SyllabusService syllabusService;

    @Autowired
    public SyllabusController(SyllabusService syllabusService) {
        this.syllabusService = syllabusService;
    }

    // http://localhost:8080/api/programme/1/syllabus/new

    @PostMapping("/new")
    public ResponseEntity<?> newSyllabus(
            @PathVariable("pid") long progId,
            @RequestBody SyllabusSenderDto syllabusSenderDto){

        return ResponseEntity.ok(syllabusService.addSyllabus(progId,syllabusSenderDto));
    }

    // http://localhost:8080/api/programme/1/syllabus/list
    @GetMapping("/list")
    public ResponseEntity<?> getProgrammeSyllabus(
            @PathVariable("pid") long progId){

        return ResponseEntity.ok(syllabusService.getSyllabus(progId));
    }

    // http://localhost:8080/api/programme/1/syllabus/semester/1

    @GetMapping("/semester/{semNumber}")
    public ResponseEntity<?> getPerSemSyllabus(
            @PathVariable("pid") long progId,
            @PathVariable("semNumber")int sem){

        return ResponseEntity.ok(syllabusService.getSyllabusPerSem(progId, sem));
    }


    //htp://localhost:8080/api/programme/1/syllabus/addCourses/1
    @PutMapping("/addCourses/{syllabId}")
    public ResponseEntity<?> editCourses(
            @PathVariable("pid") long progId,
            @PathVariable("syllabId") long syllabusId,
            @RequestBody SyllabusSenderDto syllabusSenderDto){

        return ResponseEntity.ok(syllabusService.addMoreCourses(progId, syllabusId, syllabusSenderDto));

    }


}
