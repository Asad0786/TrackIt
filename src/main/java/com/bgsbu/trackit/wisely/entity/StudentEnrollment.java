package com.bgsbu.trackit.wisely.entity;

import lombok.Data;
        import javax.persistence.*;

@Entity
@Table(name = "enrollment")
@Data
public class StudentEnrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentDetails student;

    @ManyToOne
    @JoinColumn(name = "programme_id")
    private Programme programme;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Column(name = "semester")
    private int currentSemester=1;

    @Column(name = "roll_number")
    private String rollNumber;


    public String generateRollNumber() {
        String rollNumber = String.format("%d-%s-%d",
                this.getId(), this.getProgramme().getProgrammeCode(),
                this.getBatch().getBatchStart());
        return rollNumber;
    }
}
