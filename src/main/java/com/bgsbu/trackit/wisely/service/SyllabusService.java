package com.bgsbu.trackit.wisely.service;

import com.bgsbu.trackit.wisely.model.SyllabusResponseDto;
import com.bgsbu.trackit.wisely.payload.SyllabusSenderDto;

import java.util.List;

public interface SyllabusService {
    SyllabusResponseDto addSyllabus(long progId, SyllabusSenderDto syllabusSenderDto);

    List<SyllabusResponseDto> getSyllabus(long progId);

    SyllabusResponseDto getSyllabusPerSem(long progId, int sem);

    SyllabusResponseDto addMoreCourses(long progId, long syllabusId,SyllabusSenderDto syllabusSenderDto);
}
