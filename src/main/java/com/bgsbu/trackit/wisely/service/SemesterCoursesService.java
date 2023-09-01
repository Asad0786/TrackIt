package com.bgsbu.trackit.wisely.service;

import com.bgsbu.trackit.wisely.model.SemesterCoursesResponseDto;
import com.bgsbu.trackit.wisely.payload.SemesterCourseSenderDto;

import java.util.List;

public interface SemesterCoursesService {
    SemesterCoursesResponseDto defineCourses(long progId, SemesterCourseSenderDto semesterCourseSenderDto);

    List<SemesterCoursesResponseDto> getAllCoursesSemesters(long progId);
}
