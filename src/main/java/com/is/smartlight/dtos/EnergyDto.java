package com.is.smartlight.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyDto {
    @NotNull(message = "Invalid price")
    private Float pricePerkWh;

    @NotNull(message = "Invalid date")
    private String date;
}
