package com.bgsbu.trackit.wisely.service.serviceImpl;

import com.bgsbu.trackit.wisely.entity.Course;
import com.bgsbu.trackit.wisely.entity.Department;
import com.bgsbu.trackit.wisely.entity.Programme;
import com.bgsbu.trackit.wisely.exception.DuplicateDataException;
import com.bgsbu.trackit.wisely.exception.ResourceNotFoundException;
import com.bgsbu.trackit.wisely.model.ProgrammeResponseDto;
import com.bgsbu.trackit.wisely.payload.ProgrammeSenderDto;
import com.bgsbu.trackit.wisely.repository.DepartmentRepository;
import com.bgsbu.trackit.wisely.repository.ProgrammeRepository;
import com.bgsbu.trackit.wisely.service.ProgrammeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgrammeServiceImpl implements ProgrammeService {

    private final ProgrammeRepository programmeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public ProgrammeServiceImpl(ProgrammeRepository programmeRepository, DepartmentRepository departmentRepository) {
        this.programmeRepository = programmeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public ProgrammeResponseDto addProgramme(long deptId, ProgrammeSenderDto programmeSenderDto) {

        if( programmeRepository.existsByProgrammeCode(programmeSenderDto.getProgrammeCode()))
            throw new DuplicateDataException("Department already exists");

        Optional<Department> department = departmentRepository.findById(deptId);
        if(!department.isPresent())
            throw new ResourceNotFoundException("Departments doesn't exists");;

        Programme programme = new Programme();
        programme.setProgrammeName(programmeSenderDto.getProgrammeName());
        programme.setProgrammeCode(programmeSenderDto.getProgrammeCode());
        programme.setYears(programmeSenderDto.getYears());
        programme.setDepartment(department.get());

        programme = programmeRepository.save(programme);

        department.get().getProgrammes().add(programme);
        departmentRepository.save(department.get());

        return mapToDto(programme);

    }

    @Override
    public List<ProgrammeResponseDto> getAllProgramme(long deptId) {

        List<Programme> programmes = programmeRepository.findAll();
        if(programmes.isEmpty())
            throw new ResourceNotFoundException("No programme yet");

        return programmes.stream().map(this::mapToDto).collect(Collectors.toList());

    }

    @Override
    public ProgrammeResponseDto getProgramme(long deptId, long programmeId) {

       if(!departmentRepository.existsById(deptId))
           throw new ResourceNotFoundException("Department doesn't exists");

        Optional<Programme> programme = programmeRepository.findById(programmeId);
        if(!programme.isPresent())
            throw new ResourceNotFoundException("programme doesn't exists");

        ProgrammeResponseDto programmeResponseDto = new ProgrammeResponseDto();
        programmeResponseDto.setProgrammeName(programme.get().getProgrammeName());
        programmeResponseDto.setProgrammeCode(programme.get().getProgrammeCode());
        programmeResponseDto.setProgrammeId(programme.get().getProgrammeId());
        programmeResponseDto.setYears(programme.get().getYears());
        programmeResponseDto.setDepartment(programme.get().getDepartment().getDepartmentName());
        programmeResponseDto.setCourses(
                programme.get().getCourses().stream().map(Course::getCourseName).collect(Collectors.toList())
        );

        return programmeResponseDto;
    }

    @Override
    public ProgrammeResponseDto editProgramme(long deptId, long programmeId, ProgrammeSenderDto programmeSenderDto) {

        if(!departmentRepository.existsById(deptId))
            throw new ResourceNotFoundException("Department doesn't exists");

        Optional<Programme> programme = programmeRepository.findById(programmeId);
        if(!programme.isPresent())
            throw new ResourceNotFoundException("programme doesn't exists");

        programme.get().setProgrammeName(programmeSenderDto.getProgrammeName());
        programme.get().setProgrammeCode(programmeSenderDto.getProgrammeCode());
        programme.get().setYears(programmeSenderDto.getYears());

        Programme save = programmeRepository.save(programme.get());
        return mapToDto(save);

    }

    @Override
    public String deleteProgramme(long deptId, long programmeId) {

        if(!departmentRepository.existsById(deptId))
            return "programme doesn't exists";

        if(!programmeRepository.existsById(programmeId))
            return "programme doesn't exists!";
        try {
            programmeRepository.deleteById(programmeId);
            return "deleted";
        }catch (Exception e){
            return  "Contact service provider";
        }
    }

    private ProgrammeResponseDto mapToDto(Programme programme) {

        ProgrammeResponseDto programmeResponseDto = new ProgrammeResponseDto();

        programmeResponseDto.setProgrammeName(programme.getProgrammeName());
        programmeResponseDto.setProgrammeId(programme.getProgrammeId());
        programmeResponseDto.setProgrammeCode(programme.getProgrammeCode());
        programmeResponseDto.setYears(programme.getYears());
        programmeResponseDto.setDepartment(programme.getDepartment().getDepartmentName());

        if(programme.getCourses() != null)
            programmeResponseDto.setCourses(
                    programme.getCourses().stream().map(Course::getCourseName).collect(Collectors.toList())

            );

        return programmeResponseDto;

    }
}
