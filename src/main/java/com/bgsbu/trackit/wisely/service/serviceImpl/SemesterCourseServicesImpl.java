package com.bgsbu.trackit.wisely.service.serviceImpl;

import com.bgsbu.trackit.wisely.entity.*;
import com.bgsbu.trackit.wisely.exception.BadRequestsException;
import com.bgsbu.trackit.wisely.exception.ResourceNotFoundException;
import com.bgsbu.trackit.wisely.model.SemesterCoursesResponseDto;
import com.bgsbu.trackit.wisely.payload.SemesterCourseSenderDto;
import com.bgsbu.trackit.wisely.repository.*;
import com.bgsbu.trackit.wisely.service.SemesterCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SemesterCourseServicesImpl implements SemesterCoursesService {

    private final SemesterCoursesRepository semesterCoursesRepository;
    private final ProgrammeRepository programmeRepository;
    private final CourseRepository courseRepository;
    private final SyllabusRepository syllabusRepository;
    private final BatchRepository batchRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;

    @Autowired
    public SemesterCourseServicesImpl(SemesterCoursesRepository semesterCoursesRepository, ProgrammeRepository programmeRepository, CourseRepository courseRepository, SyllabusRepository syllabusRepository, BatchRepository batchRepository, StudentEnrollmentRepository studentEnrollmentRepository) {
        this.semesterCoursesRepository = semesterCoursesRepository;
        this.programmeRepository = programmeRepository;
        this.courseRepository = courseRepository;
        this.syllabusRepository = syllabusRepository;
        this.batchRepository = batchRepository;
        this.studentEnrollmentRepository = studentEnrollmentRepository;
    }

    @Override
    public SemesterCoursesResponseDto defineCourses(long progId, SemesterCourseSenderDto semesterCourseSenderDto) {

        if(!programmeRepository.existsById(progId)){
            throw new ResourceNotFoundException("Programme doesn't exist");
        }
        Programme programme = programmeRepository.findById(progId).get();

        List<String> coursesCodes = semesterCourseSenderDto.getCoursesCodes();

        for(String courseCode: coursesCodes){
            if(!courseRepository.existsByCourseCode(courseCode)) {
                throw new ResourceNotFoundException("Course doesn't exist");
            }

            if(programme != courseRepository.findByCourseCode(courseCode).getProgramme()){
                throw new BadRequestsException("courses does not fall under same programme");
            }
        }

        Syllabus bySemesterNumber = syllabusRepository.findBySemesterNumber(semesterCourseSenderDto.getSemesterNumber());

        Set<String> courseCodesInSyllabus = bySemesterNumber.getCourse().stream()
                .map(Course::getCourseCode)
                .collect(Collectors.toSet());

        if(!courseCodesInSyllabus.containsAll(coursesCodes)){
            throw new ResourceNotFoundException("Course is not defined in the Syllabus ");
        }


        List<Course> allCourses = courseRepository.findAllByCourseCodeIn(coursesCodes);
        if( allCourses.stream().mapToInt(Course::getCredits).sum()<20 && allCourses.stream().mapToInt(Course::getCredits).sum()>23)
        { throw new BadRequestsException("Credits are less than 20");
        }

        SemesterCourses semesterCourses = new SemesterCourses();
        semesterCourses.setSemesterNumber(semesterCourseSenderDto.getSemesterNumber());
        semesterCourses.setProgramme(programme);
        semesterCourses.setCourses(allCourses);
        semesterCourses.setBatch(batchRepository.findByBatchStart(semesterCourseSenderDto.getBatchStart()));

        semesterCourses = semesterCoursesRepository.save(semesterCourses);


        /*
        yet to do  some task
         */


        return mapToDto(semesterCourses);

    }

    @Override
    public List<SemesterCoursesResponseDto> getAllCoursesSemesters(long progId) {

        if(!programmeRepository.existsById(progId))
            throw new ResourceNotFoundException("Programme doesnot found");

        List<SemesterCourses> byProgramme = semesterCoursesRepository.findByProgramme(programmeRepository.findById(progId).get());

        return byProgramme.stream().map(this::mapToDto).collect(Collectors.toList());

    }

    private SemesterCoursesResponseDto mapToDto(SemesterCourses semesterCourses) {

        SemesterCoursesResponseDto semesterCoursesResponseDto = new SemesterCoursesResponseDto();

        semesterCoursesResponseDto.setSemesterNumber(semesterCourses.getSemesterNumber());
        semesterCoursesResponseDto.setCoursesCodes(semesterCourses.getCourses().stream().map(Course::getCourseCode).collect(Collectors.toList()));
        semesterCoursesResponseDto.setProgrammeName(semesterCourses.getProgramme().getProgrammeName());
        semesterCoursesResponseDto.setBatch(String.valueOf(semesterCourses.getBatch().getBatchStart())+"-"+String.valueOf(semesterCourses.getBatch().getBatchEnd()));

        return semesterCoursesResponseDto;

    }
}
