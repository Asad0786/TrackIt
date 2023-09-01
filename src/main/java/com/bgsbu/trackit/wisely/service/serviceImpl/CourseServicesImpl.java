package com.bgsbu.trackit.wisely.service.serviceImpl;

import com.bgsbu.trackit.wisely.entity.Course;
import com.bgsbu.trackit.wisely.entity.Programme;
import com.bgsbu.trackit.wisely.exception.BadRequestsException;
import com.bgsbu.trackit.wisely.exception.DuplicateDataException;
import com.bgsbu.trackit.wisely.exception.ResourceNotFoundException;
import com.bgsbu.trackit.wisely.model.CourseResponseDto;
import com.bgsbu.trackit.wisely.payload.CourseSenderDto;
import com.bgsbu.trackit.wisely.repository.CourseRepository;
import com.bgsbu.trackit.wisely.repository.ProgrammeRepository;
import com.bgsbu.trackit.wisely.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServicesImpl implements CourseService {

//    @Value("${upload.path}") // Define a properties value for the upload path
//    private String uploadPath;
    private final CourseRepository courseRepository;
    private final ProgrammeRepository programmeRepository;

    @Autowired
    public CourseServicesImpl(CourseRepository courseRepository, ProgrammeRepository programmeRepository) {
        this.courseRepository = courseRepository;
        this.programmeRepository = programmeRepository;
    }



  //  public CourseResponseDto addNewCourse(long progId, CourseSenderDto courseSenderDto, MultipartFile file) {
  @Override
    public CourseResponseDto addNewCourse(long progId, CourseSenderDto courseSenderDto) {

        if(courseRepository.existsByCourseCode(courseSenderDto.getCourseCode()))
            throw new DuplicateDataException("Course already exists");

        if(!programmeRepository.existsById(progId))
            throw new ResourceNotFoundException("Programme doesn't exists");

        Optional<Programme> programme = programmeRepository.findById(progId);
        Course course = new Course();
        course.setCourseName(courseSenderDto.getCourseName());
        course.setCourseCode(courseSenderDto.getCourseCode());
        course.setCredits(courseSenderDto.getCredits());
        course.setProgramme(programme.get());
        course.setPractical(courseSenderDto.isPractical());

        course = courseRepository.save(course);

//        Path filePath = Paths.get(uploadPath, course.getCourseCode());
//
//        try {
//            // Save file locally
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//        }catch (Exception e){
//            return null;
//        }

        programme.get().getCourses().add(course);
        programmeRepository.save(programme.get());

        return mapToDto(course);
    }

    @Override
    public List<CourseResponseDto> getAllCourses(long progrId) {

        if(!programmeRepository.existsById(progrId))
            throw new ResourceNotFoundException("Programme doesn't exists");


        List<Course> courses = courseRepository.findByProgramme(programmeRepository.findById(progrId).get());

        return courses.stream().map(this::mapToDto).collect(Collectors.toList());

    }

    @Override
    public CourseResponseDto getCourse(long progrId, long courseId) {
        if(!programmeRepository.existsById(progrId))
            throw new ResourceNotFoundException("Programme doesn't exists");

        if(!courseRepository.existsById(courseId))
            throw new ResourceNotFoundException("Course doesn't exists");

        if (progrId != courseRepository.findById(courseId).get().getProgramme().getProgrammeId())
            throw new BadRequestsException("Wrong programme");


        Course course = courseRepository.findById(courseId).get();


        return mapToDto(course);
    }

    @Override
    public CourseResponseDto editCourse(long progrId, long courseId, CourseSenderDto courseSenderDto) {

        if(!programmeRepository.existsById(progrId))
            throw new ResourceNotFoundException("Programme doesn't exists");

        if(!courseRepository.existsById(courseId))
            throw new ResourceNotFoundException("Course doesn't exists");

        if (progrId != courseRepository.findById(courseId).get().getProgramme().getProgrammeId())
            throw new BadRequestsException("Wrong programme");

        Course course = courseRepository.findById(courseId).get();
        course.setCredits(courseSenderDto.getCredits());
        course.setCourseCode(courseSenderDto.getCourseCode());
        course.setCourseName(courseSenderDto.getCourseName());

        course = courseRepository.save(course);

        return mapToDto(course);

    }

    private CourseResponseDto mapToDto(Course course) {

        CourseResponseDto courseResponseDto= new CourseResponseDto();

        courseResponseDto.setCourseCode(course.getCourseCode());
        courseResponseDto.setCourseId(course.getCourseId());
        courseResponseDto.setCourseName(course.getCourseName());
        courseResponseDto.setCredits(courseResponseDto.getCredits());
        courseResponseDto.setPractical(course.isPractical());
        courseResponseDto.setCredits(course.getCredits());
        courseResponseDto.setProgramme(course.getProgramme().getProgrammeName());

        return courseResponseDto;

    }
}
