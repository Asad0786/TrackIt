package com.bgsbu.trackit.wisely.service;

import com.bgsbu.trackit.wisely.model.StudentEnrollmentResponseDto;
import com.bgsbu.trackit.wisely.payload.StudentEnrollmentSenderDto;

import java.util.List;

public interface StudentEnrollmentService {
    StudentEnrollmentResponseDto enrollStudent(long studentId, StudentEnrollmentSenderDto studentEnrollmentSenderDto);

    List<StudentEnrollmentResponseDto> updateSemester(long progId, int batch);

    List<StudentEnrollmentResponseDto> getAllEnrollsOfProg(long progId,int batchStart);
}
