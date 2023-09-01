package com.bgsbu.trackit.wisely.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammeResponseDto {

    private Long programmeId;
    private String programmeName;
    private String programmeCode;
    private short years;
    private String department;
    private List<String> courses;

}
