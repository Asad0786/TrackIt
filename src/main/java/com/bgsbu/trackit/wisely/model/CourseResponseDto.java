package com.bgsbu.trackit.wisely.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponseDto {

    private Long courseId;
    private String courseCode;
    private String courseName;
    private short credits;
    private boolean isPractical;
    private String programme;


}
