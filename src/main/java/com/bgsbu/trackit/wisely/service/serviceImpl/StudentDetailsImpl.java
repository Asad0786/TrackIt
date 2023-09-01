package com.bgsbu.trackit.wisely.service.serviceImpl;

import com.bgsbu.trackit.wisely.entity.StudentDetails;
import com.bgsbu.trackit.wisely.exception.ResourceNotFoundException;
import com.bgsbu.trackit.wisely.model.StudentDetailsResponseDto;
import com.bgsbu.trackit.wisely.payload.StudentDetailsSenderDto;
import com.bgsbu.trackit.wisely.repository.StudentDetailsRepository;
import com.bgsbu.trackit.wisely.service.StudentDetailsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StudentDetailsImpl implements StudentDetailsServices {

    private final StudentDetailsRepository studentDetailsRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentDetailsImpl(StudentDetailsRepository studentDetailsRepository, PasswordEncoder passwordEncoder) {
        this.studentDetailsRepository = studentDetailsRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public StudentDetailsResponseDto registerStudent(StudentDetailsSenderDto studentDetailsSenderDto) {

        StudentDetails studentDetails = new StudentDetails() ;

        studentDetails.setFirstName(studentDetailsSenderDto.getFirstName());
        studentDetails.setLastName(studentDetailsSenderDto.getLastName());
        studentDetails.setEmail(studentDetailsSenderDto.getEmail());
        studentDetails.setPassword(passwordEncoder.encode(studentDetailsSenderDto.getPassword()));

        studentDetails = studentDetailsRepository.save(studentDetails);

        return mapToDto(studentDetails);
    }

    @Override
    public StudentDetailsResponseDto getSudentDetails(String email) {
        if(!studentDetailsRepository.existsByEmail(email))
            throw new ResourceNotFoundException("Student with this email is not registered");

        StudentDetails byEmail = studentDetailsRepository.findByEmail(email);


        return mapToDto(byEmail);
    }

    private StudentDetailsResponseDto mapToDto(StudentDetails studentDetails) {

        StudentDetailsResponseDto studentDetailsResponseDto = new StudentDetailsResponseDto();

        studentDetailsResponseDto.setId(studentDetails.getId());
        studentDetailsResponseDto.setEmail(studentDetails.getEmail());
        studentDetailsResponseDto.setFirstName(studentDetails.getFirstName());
        studentDetailsResponseDto.setLastName(studentDetails.getLastName());

        return studentDetailsResponseDto;

    }
}
