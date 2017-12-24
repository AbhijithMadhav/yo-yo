package com.am.yo_yo;

import java.util.ArrayList;

/**
 * Created by amadhav on 24/12/17.
 */

public class YoYoIntermittantEnduranceTestLevel2 extends YoYoTest {

    private static final String TEST_NAME = "Yo-Yo Intermittent Endurance Test - Level 2";
    private static final Long REST_TIME_IN_MILLIS = 6000L;
    private static final ArrayList<Stage> TEST_STAGES = new ArrayList<>();

    static {
        TEST_STAGES.add(new Stage(8f, 2, 11.5f));
        TEST_STAGES.add(new Stage(10f, 2, 12.5f));
        TEST_STAGES.add(new Stage(12f, 2, 13.5f));
        TEST_STAGES.add(new Stage(13f, 8, 14f));
        TEST_STAGES.add(new Stage(13.5f, 8, 14.25f));
        TEST_STAGES.add(new Stage(14f, 8, 14.5f));
        TEST_STAGES.add(new Stage(14.5f, 3, 14.75f));
        TEST_STAGES.add(new Stage(15f, 3, 15f));
        TEST_STAGES.add(new Stage(15.5f, 6, 15.25f));
        TEST_STAGES.add(new Stage(16f, 6, 15.5f));
        TEST_STAGES.add(new Stage(16.5f, 6, 15.75f));
        TEST_STAGES.add(new Stage(17f, 6, 16f));
        TEST_STAGES.add(new Stage(17.5f, 6, 16.25f));
        TEST_STAGES.add(new Stage(18f, 6, 16.5f));
        TEST_STAGES.add(new Stage(18.5f, 6, 16.75f));
        TEST_STAGES.add(new Stage(19f, 6, 17f));
        TEST_STAGES.add(new Stage(19.5f, 6, 17.25f));
        TEST_STAGES.add(new Stage(20f, 6, 17.5f));
        TEST_STAGES.add(new Stage(20.5f, 6, 17.75f));
        TEST_STAGES.add(new Stage(21f, 6, 18f));
        TEST_STAGES.add(new Stage(21.5f, 6, 18.5f));
    }

    @Override
    public ArrayList<Stage> testStages() {
        return TEST_STAGES;
    }

    @Override
    public Long restIntervalInMills() {
        return REST_TIME_IN_MILLIS;
    }

    @Override
    public String testName() {
        return TEST_NAME;
    }
}
