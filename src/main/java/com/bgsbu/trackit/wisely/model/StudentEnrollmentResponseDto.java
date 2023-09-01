package com.bgsbu.trackit.wisely.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnrollmentResponseDto {

    private String name;
    private String email;
    private String programmeCode;
    private String rollNumber;
    private String batch;
    private int currentSemester;

}
