package com.am.yo_yo;

import java.util.ArrayList;

import static com.am.yo_yo.Constants.SHUTTLE_LENGTH_IN_METERS;

/**
 * Created by amadhav on 23/12/17.
 */

public class Utils {

    public static Integer distanceCoveredInM(ArrayList<Stage> testStages, Integer currentStageIndex, Integer shuttlesRemaining) {
        return shuttlesCompleted(testStages, currentStageIndex, shuttlesRemaining) * SHUTTLE_LENGTH_IN_METERS;
    }

    public static Integer shuttlesCompleted(ArrayList<Stage> testStages, Integer currentStageIndex, Integer shuttlesRemaining) {
        Integer shuttlesCompleted = 0;
        for (int i = 0; i < currentStageIndex; i++) {
            Stage stage = testStages.get(i);
            shuttlesCompleted += stage.getNumShuttles();
        }
        shuttlesCompleted += (testStages.get(currentStageIndex).getNumShuttles() - shuttlesRemaining);
        return shuttlesCompleted;
    }
}
