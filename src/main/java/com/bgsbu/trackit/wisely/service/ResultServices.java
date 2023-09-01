package com.bgsbu.trackit.wisely.service;

import com.bgsbu.trackit.wisely.model.ResultResponseDto;
import com.bgsbu.trackit.wisely.payload.ResultSenderDto;

import java.util.List;

public interface ResultServices {
    ResultResponseDto declareResult(ResultSenderDto resultSenderDto);

    List<ResultResponseDto> getResult(ResultSenderDto rollNumber);

    List<ResultResponseDto> getPerSemesterResult(int semesterNumber, ResultSenderDto resultSenderDto);

    String getReports(String rollNumber);

    ResultResponseDto updateResult(ResultSenderDto resultSenderDto);
}
