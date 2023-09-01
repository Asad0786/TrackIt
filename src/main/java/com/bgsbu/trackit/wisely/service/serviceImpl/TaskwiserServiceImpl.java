//package com.bgsbu.trackit.wisely.service.serviceImpl;
//
//import com.bgsbu.trackit.wisely.entity.Result;
//import com.bgsbu.trackit.wisely.repository.ResultRepository;
//import com.bgsbu.trackit.wisely.repository.StudentEnrollmentRepository;
//import com.bgsbu.trackit.wisely.service.TaskwiserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class TaskwiserServiceImpl implements TaskwiserService {
//
//    private final StudentEnrollmentRepository studentEnrollmentRepository;
//    private final ResultRepository resultRepository;
//
//
//    @Autowired
//    public TaskwiserServiceImpl(StudentEnrollmentRepository studentEnrollmentRepository, ResultRepository resultRepository) {
//        this.studentEnrollmentRepository = studentEnrollmentRepository;
//        this.resultRepository = resultRepository;
//    }
//
//    @Override
//    public void getReports(String rollNumber) {
//
//        if(!studentEnrollmentRepository.existsByRollNumber(rollNumber))
//            return;
//
//        if(!resultRepository.existsByRollNumber(rollNumber))
//            return;
//
//        List<Result> results = resultRepository.findByRollNumber(rollNumber);
//
//
//    }
//}
