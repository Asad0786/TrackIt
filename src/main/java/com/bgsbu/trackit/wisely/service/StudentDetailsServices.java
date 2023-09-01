package com.bgsbu.trackit.wisely.service;

import com.bgsbu.trackit.wisely.model.StudentDetailsResponseDto;
import com.bgsbu.trackit.wisely.payload.StudentDetailsSenderDto;

public interface StudentDetailsServices {
    StudentDetailsResponseDto registerStudent(StudentDetailsSenderDto studentDetailsSenderDto);

    StudentDetailsResponseDto getSudentDetails(String email);
}
