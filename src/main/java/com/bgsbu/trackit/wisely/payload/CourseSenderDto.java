package com.bgsbu.trackit.wisely.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSenderDto {

    private Long courseId;
    private String courseCode;
    private String courseName;
    private short credits;

    @JsonProperty
    private boolean isPractical;


}
