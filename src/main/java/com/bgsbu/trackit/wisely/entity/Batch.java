package com.bgsbu.trackit.wisely.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "batch")
@Data
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "batch_start")
    private int batchStart;

    @Column(name = "batch_end")
    private int batchEnd;

    @Column(name = "semester_month")
    private String semesterMonth;

}
