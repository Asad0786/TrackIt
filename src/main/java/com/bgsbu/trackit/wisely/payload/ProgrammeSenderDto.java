package com.bgsbu.trackit.wisely.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammeSenderDto {

    private String programmeName;
    private String programmeCode;
    private short years;

}
