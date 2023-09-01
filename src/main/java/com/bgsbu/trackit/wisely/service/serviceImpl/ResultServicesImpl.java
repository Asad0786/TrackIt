package com.bgsbu.trackit.wisely.service.serviceImpl;

import com.bgsbu.trackit.wisely.entity.*;
import com.bgsbu.trackit.wisely.exception.BadRequestsException;
import com.bgsbu.trackit.wisely.exception.ResourceNotFoundException;
import com.bgsbu.trackit.wisely.model.ResultResponseDto;
import com.bgsbu.trackit.wisely.payload.ResultSenderDto;
import com.bgsbu.trackit.wisely.repository.*;
import com.bgsbu.trackit.wisely.service.ResultServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResultServicesImpl implements ResultServices {

    private final ResultRepository resultRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final CourseRepository courseRepository;
    private final SyllabusRepository syllabusRepository;
    private final SemesterCoursesRepository semesterCoursesRepository;


    @Autowired
    public ResultServicesImpl(ResultRepository resultRepository, StudentEnrollmentRepository studentEnrollmentRepository, CourseRepository courseRepository, SyllabusRepository syllabusRepository, SemesterCoursesRepository semesterCoursesRepository) {
        this.resultRepository = resultRepository;
        this.studentEnrollmentRepository = studentEnrollmentRepository;
        this.courseRepository = courseRepository;
        this.syllabusRepository = syllabusRepository;
        this.semesterCoursesRepository = semesterCoursesRepository;
    }

    @Override
    public ResultResponseDto declareResult(ResultSenderDto resultSenderDto) {

        if(!studentEnrollmentRepository.existsByRollNumber(resultSenderDto.getRollNumber()))
            throw new ResourceNotFoundException("Student is not enrolled or wrong Roll Number");


        StudentEnrollment studentEnrollment = studentEnrollmentRepository.findByRollNumber(resultSenderDto.getRollNumber());

        Syllabus bySemesterNumber = syllabusRepository.findBySemesterNumber(studentEnrollment.getCurrentSemester());

        List<String> coursesCodes = bySemesterNumber.getCourse().stream().map(Course::getCourseCode).collect(Collectors.toList());

        System.out.println(coursesCodes);
//        if(!coursesCodes.contains(resultSenderDto.getCourseCode()))
//            throw new BadRequestsException("Student is not enrolled in this course");

        SemesterCourses semesterCourses = semesterCoursesRepository.findByCourses_CourseCode(resultSenderDto.getCourseCode());
        if(semesterCourses.getSemesterNumber() >studentEnrollment.getCurrentSemester())
            throw new BadRequestsException("Student is not yet enrolled in this Semester ");

        if(resultRepository.existsByRollNumberAndCourse(resultSenderDto.getRollNumber(),courseRepository.findByCourseCode(resultSenderDto.getCourseCode())))
            throw new BadRequestsException("Result is already declared, please edit it");



        Result result = new Result();

        result.setCourse(courseRepository.findByCourseCode(resultSenderDto.getCourseCode()));
        result.setInternalMarks(resultSenderDto.getInternalMarks());
        result.setStudentEnrollment(studentEnrollmentRepository.findByRollNumber(resultSenderDto.getRollNumber()));
        result.setExternalMarks(resultSenderDto.getExternalMarks());
        result.setRollNumber(resultSenderDto.getRollNumber());


        result = resultRepository.save(result);

        return mapToDto(result);

    }

    @Override
    public List<ResultResponseDto> getResult(ResultSenderDto resultSenderDto) {

        if(!studentEnrollmentRepository.existsByRollNumber(resultSenderDto.getRollNumber()))
            throw new ResourceNotFoundException("Student is not enrolled or wrong Roll Number");

        if(!resultRepository.existsByRollNumber(resultSenderDto.getRollNumber()))
            throw new BadRequestsException("Result is not yet declared for this student");

        List<Result> results = resultRepository.findByRollNumber(resultSenderDto.getRollNumber());

        return results.stream().map(this::mapToDto).collect(Collectors.toList());

    }

    @Override
    public List<ResultResponseDto> getPerSemesterResult(int semesterNumber, ResultSenderDto resultSenderDto) {

        if(!studentEnrollmentRepository.existsByRollNumber(resultSenderDto.getRollNumber()))
            throw new ResourceNotFoundException("Student is not enrolled or wrong Roll Number");

        StudentEnrollment byRollNumber = studentEnrollmentRepository.findByRollNumber(resultSenderDto.getRollNumber());
        System.out.println(byRollNumber.getRollNumber());

        if(byRollNumber.getCurrentSemester()<semesterNumber)
            throw new BadRequestsException("Student is not yet enrolled in this Semester ");

        System.out.println(semesterNumber);
        SemesterCourses semesterCourses = semesterCoursesRepository.findByProgrammeAndSemesterNumberAndBatch(byRollNumber.getProgramme(),semesterNumber,byRollNumber.getBatch());

        if(semesterCourses.getCourses() == null)
           throw new BadRequestsException("Test case");

        System.out.println(semesterCourses.getCourses().stream().map(Course::getCourseCode).collect(Collectors.toList()));

        List<Result> results = resultRepository.findAllByCourseInAndStudentEnrollment(semesterCourses.getCourses(),byRollNumber);

        if(results.isEmpty())
            throw new BadRequestsException("Result is not yet declared, Please contact service provider");


        return results.stream().map(this::mapToDto).collect(Collectors.toList());

    }

    @Override
    public String getReports(String rollNumber) {

        StudentEnrollment byRollNumber = studentEnrollmentRepository.findByRollNumber(rollNumber);
        int currentSemester = byRollNumber.getCurrentSemester();
        
        if(currentSemester == 1)
            throw new BadRequestsException("Student is in first semester only");;
        ResultSenderDto resultSenderDto = new ResultSenderDto();
        resultSenderDto.setRollNumber(rollNumber);
        Batch batch = byRollNumber.getBatch();
        int i =1;

        Map<String, Integer> internalMarksMap = new HashMap<>();
        Map<String, Integer> externalMarksMap = new HashMap<>();
        while(i <= currentSemester)
        {
            System.out.println(i);
            System.out.println(resultSenderDto.getRollNumber());

            List<ResultResponseDto> results = getPerSemesterResult(i, resultSenderDto);
            int internalMarks=0;
            int externalMarks=0;
            for (ResultResponseDto result: results){
                System.out.println(result.getInternalMarks());
                if(result.getInternalMarks()!=null)
                    internalMarks += result.getInternalMarks();
                if(result.getExternalMarks()!=null)
                    externalMarks +=result.getExternalMarks();
            }
            internalMarksMap.put(String.valueOf(i), internalMarks);
            externalMarksMap.put(String.valueOf(i), externalMarks);
            i=i+1;
        }

        System.out.println(internalMarksMap);
        System.out.println(externalMarksMap);

        for (i = internalMarksMap.size() ; i > 0; i--) {

            Integer i1 = internalMarksMap.get(String.valueOf(i));
            Integer i2 = internalMarksMap.get(String.valueOf(i - 1));
            Integer e1 = externalMarksMap.get(String.valueOf(i));
            Integer e2 = externalMarksMap.get(String.valueOf(i - 1));

            if (i1 != null && i2 != null) {
                int difference = i1 - i2;
                if(difference < 0 )
                    return "For Internals/Performance is decreased by : " + difference;
                else if(difference>0)
                    return "Performance is increased by : " + difference;
                else
                    return "Performance is neutral  ";
            }

            if(e1 !=null && e2 !=null && e1 !=0 && e2!=0    ){
                int difference=e1 -e2 ;
                if(difference < 0 )
                    return "For Externals/ Performance is decreased by : " + difference;
                else if(difference>0)
                    return "Performance is increased by : " + difference;
                else
                    return "Performance is neutral ";

            }

        }

        return "Error";


    }

    @Override
    public ResultResponseDto updateResult(ResultSenderDto resultSenderDto) {

        StudentEnrollment byRollNumber = studentEnrollmentRepository.findByRollNumber(resultSenderDto.getRollNumber());
        Course byCourseCode = courseRepository.findByCourseCode(resultSenderDto.getCourseCode());
        if(!resultRepository.existsByCourseAndStudentEnrollment(byCourseCode, byRollNumber))
            throw new BadRequestsException("Declare result first");

        Result byCourseAndStudentEnrollment = resultRepository.findByCourseAndStudentEnrollment(byCourseCode, byRollNumber);

        byCourseAndStudentEnrollment.setInternalMarks(resultSenderDto.getInternalMarks());
        byCourseAndStudentEnrollment.setExternalMarks(resultSenderDto.getExternalMarks());

        byCourseAndStudentEnrollment = resultRepository.save(byCourseAndStudentEnrollment);

        return mapToDto(byCourseAndStudentEnrollment);
    }

    private ResultResponseDto mapToDto(Result result) {

        ResultResponseDto resultResponseDto = new ResultResponseDto();

        resultResponseDto.setRollNumber(result.getRollNumber());
        resultResponseDto.setEmail(result.getStudentEnrollment().getStudent().getEmail());
        resultResponseDto.setInternalMarks(result.getInternalMarks());
        resultResponseDto.setExternalMarks(result.getExternalMarks());
        resultResponseDto.setCourseCode(result.getCourse().getCourseCode());
        Syllabus byCourse = syllabusRepository.findByCourse(result.getCourse());
        resultResponseDto.setSemester(byCourse.getSemesterNumber());
        return resultResponseDto;

    }
}
