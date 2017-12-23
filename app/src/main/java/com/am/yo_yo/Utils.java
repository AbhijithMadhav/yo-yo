package com.am.yo_yo;

import static com.am.yo_yo.Constants.SHUTTLE_LENGTH_IN_METERS;
import static com.am.yo_yo.Constants.STAGES;

/**
 * Created by amadhav on 23/12/17.
 */

public class Utils {

    public static Integer distanceCoveredInM(Integer currentStageIndex, Integer shuttlesRemaining) {
        return shuttlesCompleted(currentStageIndex, shuttlesRemaining) * SHUTTLE_LENGTH_IN_METERS;
    }

    public static Integer shuttlesCompleted(Integer currentStageIndex, Integer shuttlesRemaining) {
        Integer shuttlesCompleted = 0;
        for (int i = 0; i < currentStageIndex; i++) {
            Stage stage = STAGES.get(i);
            shuttlesCompleted += stage.getNumShuttles();
        }
        shuttlesCompleted += (STAGES.get(currentStageIndex).getNumShuttles() - shuttlesRemaining);
        return shuttlesCompleted;
    }
}
