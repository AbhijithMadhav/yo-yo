package com.am.yo_yo;

import java.util.ArrayList;

/**
 * Created by amadhav on 24/12/17.
 */

public class YoYoIntermittantRecoveryTestLevel1 extends YoYoTest {

    private static final String TEST_NAME = "Yo-Yo Intermittent Recovery Test - Level 1";
    private static final Long REST_TIME_IN_MILLIS = 11000L;
    private static final ArrayList<Stage> TEST_STAGES = new ArrayList<>();

    static {
        TEST_STAGES.add(new Stage(5, 1, 10f));
        TEST_STAGES.add(new Stage(8, 1, 11.5f));
        TEST_STAGES.add(new Stage(11, 2, 13f));
        TEST_STAGES.add(new Stage(12, 3, 13.5f));
        TEST_STAGES.add(new Stage(13, 4, 14f));
        TEST_STAGES.add(new Stage(14, 8, 14.5f));
        TEST_STAGES.add(new Stage(15, 8, 15f));
        TEST_STAGES.add(new Stage(16, 8, 15.5f));
        TEST_STAGES.add(new Stage(17, 8, 16f));
        TEST_STAGES.add(new Stage(18, 8, 16.5f));
        TEST_STAGES.add(new Stage(19, 8, 17f));
        TEST_STAGES.add(new Stage(20, 8, 17.5f));
        TEST_STAGES.add(new Stage(21, 8, 18f));
        TEST_STAGES.add(new Stage(22, 8, 18.5f));
        TEST_STAGES.add(new Stage(23, 8, 19f));
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
