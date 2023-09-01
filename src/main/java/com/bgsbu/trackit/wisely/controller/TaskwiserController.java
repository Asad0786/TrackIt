//package com.bgsbu.trackit.wisely.controller;
//
//import com.bgsbu.trackit.wisely.service.TaskwiserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/taskwiser")
//public class TaskwiserController {
//
//    private final TaskwiserService taskwiserService;
//
//    @Autowired
//    public TaskwiserController(TaskwiserService taskwiserService) {
//        this.taskwiserService = taskwiserService;
//    }
//
//    @GetMapping("/get/{rollNumber}")
//    public ResponseEntity<?> getReports(@PathVariable("")String rollNumber){
//
//        taskwiserService.getReports(rollNumber);
//        return  ResponseEntity.ok("");
//    }
//
//}
