package com.bgsbu.trackit.wisely.model;

import com.bgsbu.trackit.wisely.entity.Programme;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponseDto {

    private long id;
    private String departmentName;
    private String departmentCode;
    private String departmentDescription;
    private List<Programme> programmes;

}
