package com.bgsbu.trackit.wisely.service;

import com.bgsbu.trackit.wisely.model.ProgrammeResponseDto;
import com.bgsbu.trackit.wisely.payload.ProgrammeSenderDto;

import java.util.List;

public interface ProgrammeService {
    ProgrammeResponseDto addProgramme(long deptId, ProgrammeSenderDto programmeSenderDto);

    List<ProgrammeResponseDto> getAllProgramme(long deptId);

    ProgrammeResponseDto getProgramme(long deptId, long programmeId);

    ProgrammeResponseDto editProgramme(long deptId, long programmeId, ProgrammeSenderDto programmeSenderDto);

    String deleteProgramme(long deptId, long programmeId);
}
