package com.bgsbu.trackit.wisely.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/*
    This class will hold all the courses that can be offered in a semester for
    example if a programme has 20-30 courses ,
 */

@Entity
@Table(name = "syllabus",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"semester_number", "batch_start", "programme_id"},
                        name = "unique_semester_batch_programme")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Syllabus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Course> course;

    @ManyToOne
    @JoinColumn(name = "programme_id")
    private Programme programme;

    @Column(name = "semester_number")
    private int semesterNumber;

    @Column(name = "batch_start")
    private int batchStart;

    @Column(name = "batch_end")
    private int batchEnd;

}
