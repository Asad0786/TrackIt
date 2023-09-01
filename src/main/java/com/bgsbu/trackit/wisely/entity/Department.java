package com.bgsbu.trackit.wisely.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "departments")
@Data
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_code")
    private String departmentCode;

    @Column(name = "department_description")
    private String departmentDescription;

    @JsonManagedReference
    @OneToMany(mappedBy = "department",  cascade = CascadeType.ALL)
    private List<Programme> programmes;

}
