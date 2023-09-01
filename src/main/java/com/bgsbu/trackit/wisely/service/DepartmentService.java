package com.bgsbu.trackit.wisely.service;

import com.bgsbu.trackit.wisely.model.DepartmentResponseDto;
import com.bgsbu.trackit.wisely.payload.DepartmentSenderDto;

import java.util.List;

public interface DepartmentService {
    DepartmentResponseDto addNewDepartment(DepartmentSenderDto departmentSenderDto);

    List<DepartmentResponseDto> getAllDepartments();

    DepartmentResponseDto editDepartmentDetails(long deptId, DepartmentSenderDto departmentSenderDto);

    DepartmentResponseDto getDepartments(long deptId);

    String deleteDepartment(long deptId);
}
