package com.bgsbu.trackit.wisely.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollmentSenderDto {

    private String studentEmail;
    private String programmeCode;
    private int batchStart;
    private int currentSemester;
    private String rollNumber;
}
