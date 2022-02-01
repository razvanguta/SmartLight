package com.is.smartlight.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutineDto {
    private int startHour;
    private int endHour;
    private Long presetId;
    private String dayOfTheWeek;

}
