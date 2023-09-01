package com.bgsbu.trackit.wisely.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "programmes")
@Data
public class Programme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "programme_id")
    private Long programmeId;

    @Column(name = "programme_name")
    private String programmeName;

    @Column(name = "programme_code")
    private String programmeCode;

    @Column(name="years")
    private short years;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;

    @JsonManagedReference
    @OneToMany(mappedBy = "programme",  cascade = CascadeType.ALL)
    private List<Course> courses;

}
