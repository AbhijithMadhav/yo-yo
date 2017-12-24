package com.am.yo_yo.test;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by amadhav on 24/12/17.
 */

public abstract class YoYoTest {

    public static final Integer SHUTTLE_LENGTH_IN_METERS = 40;

    public abstract ArrayList<Stage> testStages();

    public abstract Long restIntervalInMills();

    public abstract String testName();

    public abstract Uri testNormUri();

    public Integer distanceCoveredInM(Integer currentStageIndex, Integer shuttlesRemaining) {
        return shuttlesCompleted(currentStageIndex, shuttlesRemaining) * SHUTTLE_LENGTH_IN_METERS;
    }

    public Integer shuttlesCompleted(Integer currentStageIndex, Integer shuttlesRemaining) {
        Integer shuttlesCompleted = 0;
        for (int i = 0; i < currentStageIndex; i++) {
            Stage stage = testStages().get(i);
            shuttlesCompleted += stage.getNumShuttles();
        }
        shuttlesCompleted += (testStages().get(currentStageIndex).getNumShuttles() - shuttlesRemaining);
        return shuttlesCompleted;
    }
}
