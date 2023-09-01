package com.bgsbu.trackit.wisely.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponseDto {

    private String rollNumber;
    private String email;
    private int semester;
    private String courseCode;
    private Integer internalMarks;
    private Integer externalMarks;

}
