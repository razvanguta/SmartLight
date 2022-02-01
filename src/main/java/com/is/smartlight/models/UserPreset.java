package com.is.smartlight.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userpresets")
public class UserPreset extends DefaultPreset {
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    @Builder(builderMethodName = "userPresetBuilder")
    public UserPreset(Long id, String name, Boolean deleted, Integer redValue, Integer greenValue, Integer blueValue, Float intensityPercentage, User user) {
        super(id, name, deleted, redValue, greenValue, blueValue, intensityPercentage);
        this.user = user;
    }
}
