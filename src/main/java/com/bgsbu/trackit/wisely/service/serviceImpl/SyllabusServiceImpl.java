package com.bgsbu.trackit.wisely.service.serviceImpl;

import com.bgsbu.trackit.wisely.entity.Course;
import com.bgsbu.trackit.wisely.entity.Syllabus;
import com.bgsbu.trackit.wisely.exception.BadRequestsException;
import com.bgsbu.trackit.wisely.exception.ResourceNotFoundException;
import com.bgsbu.trackit.wisely.model.SyllabusResponseDto;
import com.bgsbu.trackit.wisely.payload.SyllabusSenderDto;
import com.bgsbu.trackit.wisely.repository.CourseRepository;
import com.bgsbu.trackit.wisely.repository.ProgrammeRepository;
import com.bgsbu.trackit.wisely.repository.SyllabusRepository;
import com.bgsbu.trackit.wisely.service.SyllabusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SyllabusServiceImpl implements SyllabusService {

    private final SyllabusRepository syllabusRepository;
    private final ProgrammeRepository programmeRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public SyllabusServiceImpl(SyllabusRepository syllabusRepository, ProgrammeRepository programmeRepository, CourseRepository courseRepository) {
        this.syllabusRepository = syllabusRepository;
        this.programmeRepository = programmeRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public SyllabusResponseDto addSyllabus(long progId, SyllabusSenderDto syllabusSenderDto) {

        if(!programmeRepository.existsById(progId)){
            throw new ResourceNotFoundException("programme doesn't exists");

        }


        List<String> coursesCodes = syllabusSenderDto.getCourseCode();

        for(String courseCode: coursesCodes){
            if(!courseRepository.existsByCourseCode(courseCode)){
                throw new ResourceNotFoundException("Course doesn't exists");

            }
            if(courseRepository.findByCourseCode(courseCode).getProgramme() != programmeRepository.findById(progId).get() ){
                throw new BadRequestsException("Course doesn't fall in this programme");

            }
        }

        Syllabus syllabus = new Syllabus();

        syllabus.setProgramme(programmeRepository.findById(progId).get());
        syllabus.setBatchStart(syllabusSenderDto.getBatchStart());
        syllabus.setBatchEnd(syllabusSenderDto.getBatchEnd());
        syllabus.setSemesterNumber(syllabusSenderDto.getSemesterNumber());
        syllabus.setCourse(courseRepository.findAllByCourseCodeIn(syllabusSenderDto.getCourseCode()));

        syllabusRepository.save(syllabus);

        return mapToDto(syllabus);
    }

    @Override
    public List<SyllabusResponseDto> getSyllabus(long progId) {

        if(!programmeRepository.existsById(progId))
            throw new ResourceNotFoundException("programme doesn't exists");

        List<Syllabus> syllabuses = syllabusRepository.findByProgramme(programmeRepository.findById(progId).get());

        return syllabuses.stream().map(this::mapToDto).collect(Collectors.toList());

    }

    @Override
    public SyllabusResponseDto getSyllabusPerSem(long progId, int sem) {

        if(!programmeRepository.existsById(progId))
            throw new ResourceNotFoundException("programme doesn't exists");


        if(!syllabusRepository.existsBySemesterNumber(sem))
            throw new ResourceNotFoundException("Syllabus for this semester doesn't exists");

        Syllabus bySemesterNumber = syllabusRepository.findBySemesterNumber(sem);

        return mapToDto(bySemesterNumber);
    }

    @Override
    public SyllabusResponseDto addMoreCourses(long progId, long syllabusId, SyllabusSenderDto syllabusSenderDto) {

        if(!syllabusRepository.existsById(syllabusId))
            throw new ResourceNotFoundException("Syllabus for this semester doesn't exists");

        if(!programmeRepository.existsById(progId))
            throw new ResourceNotFoundException("Programme doesn't exist");

        List<String> coursesCodes = syllabusSenderDto.getCourseCode();

        for(String courseCode: coursesCodes){
            if(!courseRepository.existsByCourseCode(courseCode))
                throw new ResourceNotFoundException("course doesn't exist");
        }

        Syllabus syllabus = syllabusRepository.findById(syllabusId).get();
        List<String> collect = syllabus.getCourse().stream().map(Course::getCourseCode).collect(Collectors.toList());
        for (String courseCode : coursesCodes) {
            if (collect.contains(courseCode)) {
                throw new ResourceNotFoundException("Courses are already");

            }
        }

        syllabus.getCourse().addAll(courseRepository.findAllByCourseCodeIn(syllabusSenderDto.getCourseCode()));
        syllabusRepository.save(syllabus);
        return mapToDto(syllabus);
    }

    private SyllabusResponseDto mapToDto(Syllabus syllabus) {
        SyllabusResponseDto syllabusResponseDto = new SyllabusResponseDto();

        syllabusResponseDto.setProgrammeName(syllabus.getProgramme().getProgrammeName());
        syllabusResponseDto.setCourseName(syllabus.getCourse().stream().map(Course::getCourseName).collect(Collectors.toList()));
        syllabusResponseDto.setCourseCode(syllabus.getCourse().stream().map(Course::getCourseCode).collect(Collectors.toList()));
        syllabusResponseDto.setSemesterNumber(syllabus.getSemesterNumber());
        syllabusResponseDto.setBatchStart(syllabus.getBatchStart());
        syllabusResponseDto.setBatchEnd(syllabus.getBatchEnd());

        return syllabusResponseDto;
    }


}
