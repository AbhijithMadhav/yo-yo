package com.am.yo_yo.test;

import java.io.Serializable;

/**
 * Represents a shuttle in the YO-YO test
 * Created by amadhav on 08/12/17.
 */

public class Stage implements Serializable {

    private Float speedLevel; // Represents a particular speed at which this shuttle should be run
    private Integer numShuttles; // Represents the no of shuttles at this speed level

    private Float speedInKph;
    private Float speedInMps;

    public Stage(Float speedLevel, Integer numShuttles, Float speedInKph) {
        this.numShuttles = numShuttles;
        this.speedLevel = speedLevel;
        this.speedInKph = speedInKph;
        speedInMps = new Float(speedInKph * 1000f/3600);
    }

    public Float getSpeedLevel() {
        return speedLevel;
    }

    public Integer getNumShuttles() {
        return numShuttles;
    }

    public Float getSpeedInKph() {
        return speedInKph;
    }

    public Float getSpeedInMps() {
        return speedInMps;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Stage{");
        sb.append("speedLevel=").append(speedLevel);
        sb.append(", numShuttles=").append(numShuttles);
        sb.append(", speedInKph=").append(speedInKph);
        sb.append(", speedInMps=").append(speedInMps);
        sb.append('}');
        return sb.toString();
    }
}

