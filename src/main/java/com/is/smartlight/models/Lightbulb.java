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

    private Boolean working;

    @ManyToOne
    @JoinColumn(name="group_id", nullable=false)
    private LightGroup group;

}
