package com.am.yo_yo.test;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by amadhav on 24/12/17.
 */

public class YoYoTest implements Serializable {

    // TODO : private
    public static final Integer SHUTTLE_LENGTH_IN_METERS = 40;

    private final String testName;
    private final String testNormUri;
    private final Long restIntervalInMillis;
    private final ArrayList<Stage> testStages;

    public YoYoTest(String testName, String testNormUri, Long restIntervalInMillis, ArrayList<Stage> testStages) {
        this.testName = testName;
        Uri.parse(testNormUri);
        this.testNormUri = testNormUri;
        this.restIntervalInMillis = restIntervalInMillis;
        this.testStages = testStages;
    }

    public ArrayList<Stage> testStages() {
        return testStages;
    }

    public Long restIntervalInMills() {
        return restIntervalInMillis;
    }

    public String testName(){
        return testName;
    }

    // TODO : should parse while constructing object but getting serialization exception
    public Uri testNormUri() {
        return Uri.parse(testNormUri);
    }

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

    public Integer timeTakenInSecs(Integer currentStageIndex, Integer shuttlesRemaining, Integer restIntervalInSecs) {
        Integer timeTaken = 0;
        for (int i = 0; i < currentStageIndex; i++) {
            Stage stage = testStages().get(i);
            timeTaken += stage.getNumShuttles() * Math.round(SHUTTLE_LENGTH_IN_METERS/stage.getSpeedInMps()) 
                    + ((stage.getNumShuttles()) * restIntervalInSecs);
        }
        Stage currentStage = testStages().get(currentStageIndex);
        timeTaken += (currentStage.getNumShuttles() - shuttlesRemaining) * Math.round(SHUTTLE_LENGTH_IN_METERS/ currentStage.getSpeedInMps());
        if (currentStage.getNumShuttles() > shuttlesRemaining)
                timeTaken += ((currentStage.getNumShuttles() - shuttlesRemaining - 1) * restIntervalInSecs);
        return timeTaken;
    }
}
