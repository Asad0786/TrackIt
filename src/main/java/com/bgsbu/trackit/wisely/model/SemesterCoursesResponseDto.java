package com.bgsbu.trackit.wisely.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemesterCoursesResponseDto {

    private List<String> coursesCodes;
    private String programmeName;
    private int semesterNumber;
    private String batch;
}
