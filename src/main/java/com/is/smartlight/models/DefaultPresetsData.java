package com.is.smartlight.models;

public enum DefaultPresetsData {
    Default (255, 255, 255, 1f),
    Dim (255, 255, 255, 0.5f),
    Cozy(255, 255, 224, 1f),
    Red(255, 0, 0, 0.5f),
    Random(1, 1, 1, 1f);

    private final Integer r;
    private final Integer g;
    private final Integer b;
    private final Float intensity;

    DefaultPresetsData(Integer r, Integer g, Integer b, Float intensity) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.intensity = intensity;
    }

    public Integer getR() {
        return r;
    }

    public Integer getG() {
        return g;
    }

    public Integer getB() {
        return b;
    }

    public Float getIntensity() {
        return intensity;
    }
}
