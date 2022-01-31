package com.is.smartlight.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "lightbulbs")
public class Lightbulb {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer redValue;

    private Integer greenValue;

    private Integer blueValue;

    private Integer maxIntensity;

    private Float intensityPercentage;

    private Boolean turnedOn;

    private Boolean deleted;

    private Boolean isWorking;

    @ManyToOne
    @JoinColumn(name="group_id", nullable=false)
    private LightGroup group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRedValue() {
        return redValue;
    }

    public void setRedValue(Integer redValue) {
        this.redValue = redValue;
    }

    public Integer getGreenValue() {
        return greenValue;
    }

    public void setGreenValue(Integer greenValue) {
        this.greenValue = greenValue;
    }

    public Integer getBlueValue() {
        return blueValue;
    }

    public void setBlueValue(Integer blueValue) {
        this.blueValue = blueValue;
    }

    public Integer getMaxIntensity() {
        return maxIntensity;
    }

    public void setMaxIntensity(Integer maxIntensity) {
        this.maxIntensity = maxIntensity;
    }

    public Float getIntensityPercentage() {
        return intensityPercentage;
    }

    public void setIntensityPercentage(Float intensityPercentage) {
        this.intensityPercentage = intensityPercentage;
    }

    public Boolean getTurnedOn() {
        return turnedOn;
    }

    public void setTurnedOn(Boolean turnedOn) {
        this.turnedOn = turnedOn;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Boolean getWorking() {
        return isWorking;
    }

    public void setWorking(Boolean working) {
        isWorking = working;
    }
}
