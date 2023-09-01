package com.bgsbu.trackit.wisely.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusResponseDto {

    private List<String> courseName;
    private List<String> courseCode;
    private String programmeName;
    private int semesterNumber;
    private int batchStart;
    private int batchEnd;

}
