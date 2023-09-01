package com.bgsbu.trackit.wisely.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultSenderDto {

    private String rollNumber;
    private String courseCode;
    private Integer internalMarks;
    private Integer externalMarks;

}
