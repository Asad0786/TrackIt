package com.bgsbu.trackit.wisely.service.serviceImpl;

import com.bgsbu.trackit.wisely.entity.Batch;
import com.bgsbu.trackit.wisely.entity.Programme;
import com.bgsbu.trackit.wisely.entity.StudentEnrollment;
import com.bgsbu.trackit.wisely.exception.BadRequestsException;
import com.bgsbu.trackit.wisely.exception.DuplicateDataException;
import com.bgsbu.trackit.wisely.exception.ResourceNotFoundException;
import com.bgsbu.trackit.wisely.model.StudentEnrollmentResponseDto;
import com.bgsbu.trackit.wisely.payload.StudentEnrollmentSenderDto;
import com.bgsbu.trackit.wisely.repository.BatchRepository;
import com.bgsbu.trackit.wisely.repository.ProgrammeRepository;
import com.bgsbu.trackit.wisely.repository.StudentDetailsRepository;
import com.bgsbu.trackit.wisely.repository.StudentEnrollmentRepository;
import com.bgsbu.trackit.wisely.service.StudentEnrollmentService;
import com.bgsbu.trackit.wisely.utility.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentEnrollmentImpl implements StudentEnrollmentService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final StudentDetailsRepository studentDetailsRepository;
    private final ProgrammeRepository programmeRepository;
    private final BatchRepository batchRepository;

    private final EmailService emailService;

    @Autowired
    public StudentEnrollmentImpl(StudentEnrollmentRepository studentEnrollmentRepository, StudentDetailsRepository studentDetailsRepository, ProgrammeRepository programmeRepository, BatchRepository batchRepository, EmailService emailService) {
        this.studentEnrollmentRepository = studentEnrollmentRepository;
        this.studentDetailsRepository = studentDetailsRepository;
        this.programmeRepository = programmeRepository;
        this.batchRepository = batchRepository;
        this.emailService = emailService;
    }

    @Override
    public StudentEnrollmentResponseDto enrollStudent(long studentId, StudentEnrollmentSenderDto studentEnrollmentSenderDto) {

        if(!studentDetailsRepository.existsById(studentId))
            throw new ResourceNotFoundException("Student with this id is not registered");

       // System.out.println(studentDetailsRepository.findById(studentId).get().getEmail());
       // System.out.println(studentEnrollmentSenderDto.getStudentEmail());

        if(!studentDetailsRepository.findById(studentId).get().getEmail().equals(studentEnrollmentSenderDto.getStudentEmail()) )
            throw new BadRequestsException("Please send correct email id associated with this email");
        if(!programmeRepository.existsByProgrammeCode(studentEnrollmentSenderDto.getProgrammeCode()))
            throw new ResourceNotFoundException("Programme doesn't exists");
        if(!batchRepository.existsByBatchStart(studentEnrollmentSenderDto.getBatchStart()))
            throw new ResourceNotFoundException("batch is defined yet");

       if(studentEnrollmentRepository.existsByStudent(studentDetailsRepository.findById(studentId).get()))
           throw new DuplicateDataException("Student is already enrolled");




        StudentEnrollment studentEnrollment = new StudentEnrollment();

        studentEnrollment.setProgramme(programmeRepository.findByProgrammeCode(studentEnrollmentSenderDto.getProgrammeCode()));
        studentEnrollment.setStudent(studentDetailsRepository.findById(studentId).get());
        studentEnrollment.setBatch(batchRepository.findByBatchStart(studentEnrollmentSenderDto.getBatchStart()));

        studentEnrollment = studentEnrollmentRepository.save(studentEnrollment);

        studentEnrollment.setRollNumber(studentEnrollment.generateRollNumber());

        studentEnrollment = studentEnrollmentRepository.save(studentEnrollment);

        emailService.sendEmail(studentEnrollment.getStudent().getEmail(),"Admission Confirmed","Dear "+ studentEnrollment.getStudent().getFirstName()+" "+studentEnrollment.getStudent().getLastName() + ",\n" +
                "\n" +
                "Congratulations on your successful admission to XYZ UNI! We are delighted to welcome you to our [Program Name] program.\n" +
                "\n" +
                "Please find attached your enrollment details and important dates. Should you have any questions, feel free to reach out to our admissions team at [Admissions Contact Email/Phone].\n" +
                "\n" +
                "We look forward to your academic journey with us.\n" +
                "\n" +
                "Best regards,\n" +
                "XYZ UNI ");
        return mapToDto(studentEnrollment);

    }

    @Override
    public List<StudentEnrollmentResponseDto> updateSemester(long progId, int batchStart) {

        if(!programmeRepository.existsById(progId))
            throw new ResourceNotFoundException("Programme not found");
        if(!batchRepository.existsByBatchStart(batchStart))
            throw new ResourceNotFoundException("batch not found");

        Programme programme = programmeRepository.findById(progId).get();
        Batch batch = batchRepository.findByBatchStart(batchStart);

        List<StudentEnrollment> allByBatchAndProgramme = studentEnrollmentRepository.findAllByBatchAndProgramme(batch, programme);

        List<StudentEnrollment> updatedEnrollment = new ArrayList<>();

        for (StudentEnrollment enrollment : allByBatchAndProgramme) {
            enrollment.setCurrentSemester(enrollment.getCurrentSemester()+ 1);
            StudentEnrollment save = studentEnrollmentRepository.save(enrollment);
            updatedEnrollment.add(save);
        }

        return updatedEnrollment.stream().map(this::mapToDto).collect(Collectors.toList());



    }

    @Override
    public List<StudentEnrollmentResponseDto> getAllEnrollsOfProg(long progId, int batchStart) {

        if(!programmeRepository.existsById(progId))
            throw new ResourceNotFoundException("Programme not found");

        if(!batchRepository.existsByBatchStart(batchStart))
            throw new ResourceNotFoundException("batch not found");

        Programme programme = programmeRepository.findById(progId).get();
        Batch batch = batchRepository.findByBatchStart(batchStart);

        List<StudentEnrollment> studentsEnrollments = studentEnrollmentRepository.findAllByBatchAndProgramme(batch, programme);

        return studentsEnrollments.stream().map(this::mapToDto).collect(Collectors.toList());


    }

    private StudentEnrollmentResponseDto mapToDto(StudentEnrollment studentEnrollment) {

        StudentEnrollmentResponseDto studentEnrollmentResponseDto =new StudentEnrollmentResponseDto();
        studentEnrollmentResponseDto.setBatch(
                String.valueOf(studentEnrollment.getBatch().getBatchStart())+"-"+String.valueOf(studentEnrollment.getBatch().getBatchEnd()));

        studentEnrollmentResponseDto.setName(studentEnrollment.getStudent().getFirstName()+" "+studentEnrollment.getStudent().getLastName());
        studentEnrollmentResponseDto.setEmail(studentEnrollment.getStudent().getEmail());
        studentEnrollmentResponseDto.setRollNumber(studentEnrollment.getRollNumber());
        studentEnrollmentResponseDto.setCurrentSemester(studentEnrollment.getCurrentSemester());
        studentEnrollmentResponseDto.setProgrammeCode(studentEnrollment.getProgramme().getProgrammeCode());
        return studentEnrollmentResponseDto;
    }
}
