package com.bgsbu.trackit.wisely.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SemesterCourseSenderDto {

    private long id;
    private List<String> coursesCodes;
    private String programmeCode;
    private int semesterNumber;
    private int batchStart;

}
