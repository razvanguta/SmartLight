package com.is.smartlight.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPresetDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Boolean deleted;

    private Integer redValue;

    private Integer greenValue;

    private Integer blueValue;

    private Float intensityPercentage;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Integer getRedValue() {
        return redValue;
    }

    public Integer getGreenValue() {
        return greenValue;
    }

    public Integer getBlueValue() {
        return blueValue;
    }

    public Float getIntensityPercentage() {
        return intensityPercentage;
    }
}
