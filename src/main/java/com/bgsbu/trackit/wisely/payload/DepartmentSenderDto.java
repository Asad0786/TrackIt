package com.bgsbu.trackit.wisely.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSenderDto {

    private String departmentName;
    private String departmentCode;
    private String departmentDescription;

}
