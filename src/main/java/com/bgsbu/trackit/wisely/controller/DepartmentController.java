package com.bgsbu.trackit.wisely.controller;

import com.bgsbu.trackit.wisely.model.DepartmentResponseDto;
import com.bgsbu.trackit.wisely.payload.DepartmentSenderDto;
import com.bgsbu.trackit.wisely.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class DepartmentController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    // http://localhost:8080/api/dept/add
    @PostMapping("/add")
    public ResponseEntity<DepartmentResponseDto> newDepartment(@RequestBody DepartmentSenderDto departmentSenderDto){

        return ResponseEntity.ok(departmentService.addNewDepartment(departmentSenderDto));
    }


    // http://localhost:8080/api/dept/list

    @GetMapping("/list")
    public ResponseEntity<List<DepartmentResponseDto>> getAllDept(){

        return ResponseEntity.ok (departmentService.getAllDepartments());
    }

    // http://localhost:8080/api/dept/1
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDept(@PathVariable("id") long deptId){

        return ResponseEntity.ok (departmentService.getDepartments(deptId));
    }

    // http://localhost:8080/api/dept/1/edit
    @PutMapping("/{id}/edit")
    public ResponseEntity<DepartmentResponseDto> editDept(@PathVariable("id") long deptId,
                                                          @RequestBody DepartmentSenderDto departmentSenderDto){

        return ResponseEntity.ok(departmentService.editDepartmentDetails(deptId, departmentSenderDto));

    }

    // http://localhost:8080/api/dept/1/delete
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteDepartment(@PathVariable("id") long deptId){

        return  ResponseEntity.ok(departmentService.deleteDepartment(deptId));

    }

}
