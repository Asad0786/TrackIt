package com.bgsbu.trackit.wisely.service;

import com.bgsbu.trackit.wisely.model.CourseResponseDto;
import com.bgsbu.trackit.wisely.payload.CourseSenderDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    CourseResponseDto addNewCourse(long progId, CourseSenderDto courseSenderDto);

    List<CourseResponseDto> getAllCourses(long progrId);

    CourseResponseDto getCourse(long progrId, long courseId);

    CourseResponseDto editCourse(long progrId, long courseId, CourseSenderDto courseSenderDto);
}
