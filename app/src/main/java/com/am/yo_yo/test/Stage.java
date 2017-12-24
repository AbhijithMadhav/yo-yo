package com.am.yo_yo.test;

/**
 * Represents a shuttle in the YO-YO test
 * Created by amadhav on 08/12/17.
 */

public class Stage {

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


}

