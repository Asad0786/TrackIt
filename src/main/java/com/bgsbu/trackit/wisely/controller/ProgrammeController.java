package com.bgsbu.trackit.wisely.controller;

import com.bgsbu.trackit.wisely.payload.ProgrammeSenderDto;
import com.bgsbu.trackit.wisely.service.ProgrammeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dept/{deptId}/programme")
public class ProgrammeController {

    private final ProgrammeService programmeService;

    @Autowired
    public ProgrammeController(ProgrammeService programmeService) {

        this.programmeService = programmeService;
    }


    //http://localhost:8080/api/dept/1/programme/new
    @PostMapping("/new")
    public ResponseEntity<?> newProgramme(
            @PathVariable("deptId") long deptId,
            @RequestBody ProgrammeSenderDto programmeSenderDto){

        return ResponseEntity.ok(programmeService.addProgramme(deptId, programmeSenderDto));
    }

    // http://localhost:8080/api/dept/1/programme/list
    @GetMapping("/list")
    public ResponseEntity<?> getAllProgrammes(
            @PathVariable("deptId") long deptId){

        return ResponseEntity.ok(programmeService.getAllProgramme(deptId));
    }


    // http://localhost:8080/api/dept/1/programme/1
    @GetMapping("/{id}")
    public ResponseEntity<?> getProgramme(
            @PathVariable("deptId") long deptId,
            @PathVariable("id") long programmeId){

        return ResponseEntity.ok(programmeService.getProgramme(deptId, programmeId));
    }


    // http://localhost:8080/api/dept/1/programme/1/edit
    @PutMapping("/{pid}/edit")
    public  ResponseEntity<?> editProgramme(
            @PathVariable("deptId") long deptId,
            @PathVariable("pid") long programmeId,
            @RequestBody ProgrammeSenderDto programmeSenderDto){

        return  ResponseEntity.ok(programmeService.editProgramme(deptId,programmeId, programmeSenderDto));


    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteDepartment(
            @PathVariable("deptId") long deptId,
            @PathVariable("id") long programmeId){

        return  ResponseEntity.ok(programmeService.deleteProgramme(deptId,programmeId));

    }


}
