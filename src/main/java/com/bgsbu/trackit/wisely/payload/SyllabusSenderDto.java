package com.bgsbu.trackit.wisely.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusSenderDto {

    private List<String> courseCode;
    private int semesterNumber;
    private int batchStart;
    private int batchEnd;

}
