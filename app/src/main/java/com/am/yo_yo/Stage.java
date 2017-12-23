package com.am.yo_yo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a shuttle in the YO-YO test
 * Created by amadhav on 08/12/17.
 */

class Stage {

    public static final Integer DISTANCE_IN_METERS = 40;

    private Integer stageId; // Represents a particular speed at which this shuttle should be run
    private Integer numShuttles; // Represents the no of shuttles at this speed level

    private Float speedInKph;
    private Float speedInMps;

    public Stage(Integer stageId, Integer numShuttles, Float speedInKph) {
        this.numShuttles = numShuttles;
        this.stageId = stageId;
        this.speedInKph = speedInKph;
        speedInMps = new Float(speedInKph * 1000f/3600);
    }

    public Integer getStageId() {
        return stageId;
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

