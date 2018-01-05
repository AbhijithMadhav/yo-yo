package com.am.yo_yo.app;

/**
 * Created by amadhav on 01/01/18.
 */

public class YoYoUIModel {
    private Integer currentStageIndex;
    private Integer shuttlesRemaining;
    private double remainingTimeInSecs;
    private YoYoPhase yoYoPhase;


    public Integer getCurrentStageIndex() {
        return currentStageIndex;
    }

    public Integer getShuttlesRemaining() {
        return shuttlesRemaining;
    }

    public double getRemainingTimeInSecs() {
        return remainingTimeInSecs;
    }


    public void setCurrentStageIndex(Integer currentStageIndex) {
        this.currentStageIndex = currentStageIndex;
    }

    public void setShuttlesRemaining(Integer shuttlesRemaining) {
        this.shuttlesRemaining = shuttlesRemaining;
    }

    public void setRemainingTimeInSecs(double remainingTimeInSecs) {
        this.remainingTimeInSecs = remainingTimeInSecs;
    }


    public YoYoPhase getYoYoPhase() {
        return yoYoPhase;
    }

    public void setYoYoPhase(YoYoPhase yoYoPhase) {
        this.yoYoPhase = yoYoPhase;
    }

    public enum YoYoPhase {
        REST, SHUTTLE;
    }
}

