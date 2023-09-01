package com.bgsbu.trackit.wisely.service.serviceImpl;

import com.bgsbu.trackit.wisely.entity.Department;
import com.bgsbu.trackit.wisely.exception.ResourceNotFoundException;
import com.bgsbu.trackit.wisely.model.DepartmentResponseDto;
import com.bgsbu.trackit.wisely.payload.DepartmentSenderDto;
import com.bgsbu.trackit.wisely.repository.DepartmentRepository;
import com.bgsbu.trackit.wisely.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponseDto addNewDepartment(DepartmentSenderDto departmentSenderDto) {

        Department department = new Department();
        department.setDepartmentName(departmentSenderDto.getDepartmentName());
        department.setDepartmentCode(departmentSenderDto.getDepartmentCode());
        department.setDepartmentDescription(departmentSenderDto.getDepartmentDescription());

        department = departmentRepository.save(department);

        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
        departmentResponseDto.setId(department.getDepartmentId());
        departmentResponseDto.setDepartmentName(department.getDepartmentName());
        departmentResponseDto.setDepartmentCode(department.getDepartmentCode());
        departmentResponseDto.setDepartmentDescription(department.getDepartmentDescription());

        return departmentResponseDto;

    }

    @Override
    public List<DepartmentResponseDto> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();

        if( departments.isEmpty() )
            throw new ResourceNotFoundException("No departments yet");

        return departments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentResponseDto editDepartmentDetails(long deptId, DepartmentSenderDto departmentSenderDto) {

        if(!departmentRepository.existsById(deptId))
            throw new ResourceNotFoundException("programme doesn't exists");

        Department department = departmentRepository.findById(deptId).get();

        department.setDepartmentName(departmentSenderDto.getDepartmentName());
        department.setDepartmentCode(departmentSenderDto.getDepartmentCode());
        department.setDepartmentDescription(departmentSenderDto.getDepartmentDescription());

        department = departmentRepository.save(department);

        return mapToResponse(department);
    }

    @Override
        public DepartmentResponseDto getDepartments(long deptId) {

            Department department = departmentRepository.findById(deptId).get();

            DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();

            return mapToResponse(department);
    }

    @Override
    public String deleteDepartment(long deptId) {

        if(!departmentRepository.existsById(deptId))
            throw new ResourceNotFoundException("programme doesn't exists");

        try {
            departmentRepository.deleteById(deptId);
            return "deleted";
        }catch (Exception e){
            return "error";
        }

    }

    private DepartmentResponseDto mapToResponse(Department department) {

        DepartmentResponseDto departmentResponseDto = new DepartmentResponseDto();
        departmentResponseDto.setId(department.getDepartmentId());
        departmentResponseDto.setDepartmentName(department.getDepartmentName());
        departmentResponseDto.setDepartmentCode(department.getDepartmentCode());
        departmentResponseDto.setDepartmentDescription(department.getDepartmentDescription());
        departmentResponseDto.setProgrammes(department.getProgrammes());

        return departmentResponseDto;

    }
}
