package com.is.smartlight.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "routines")
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean isActive;
    //setGroupPreset
    @ManyToOne
    @JoinColumn(name="group_id", nullable=false)
    private LightGroup group;

    @ElementCollection
    private List<String> eachHourPreset = new ArrayList<>(24);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LightGroup getGroup() {
        return group;
    }

    public void setGroup(LightGroup group) {
        this.group = group;
    }

    public List<String> getEachHourPreset() {
        return eachHourPreset;
    }

    public void setEachHourPreset(List<String> eachHourPreset) {
        this.eachHourPreset = eachHourPreset;
    }
}
