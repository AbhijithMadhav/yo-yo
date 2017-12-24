package com.am.yo_yo;

import java.util.ArrayList;

/**
 * Created by amadhav on 24/12/17.
 */

public class YoYoIntermittantRecoveryTestLevel2 extends YoYoTest {

    private static final String TEST_NAME = "Yo-Yo Intermittent Recovery Test - Level 1";
    private static final Long REST_TIME_IN_MILLIS = 11000L;

    private static final ArrayList<Stage> TEST_STAGES = new ArrayList<>();
    static {
        TEST_STAGES.add(new Stage(11, 1, 13f));
        TEST_STAGES.add(new Stage(15, 1, 15f));
        TEST_STAGES.add(new Stage(17, 2, 16f));
        TEST_STAGES.add(new Stage(18, 3, 16.5f));
        TEST_STAGES.add(new Stage(19, 4, 17f));
        TEST_STAGES.add(new Stage(20, 8, 17.5f));
        TEST_STAGES.add(new Stage(21, 8, 18f));
        TEST_STAGES.add(new Stage(22, 8, 18.5f));
        TEST_STAGES.add(new Stage(23, 8, 19f));
        TEST_STAGES.add(new Stage(24, 8, 19.5f));
        TEST_STAGES.add(new Stage(25, 8, 20f));
        TEST_STAGES.add(new Stage(26, 8, 20.5f));
        TEST_STAGES.add(new Stage(27, 8, 21f));
        TEST_STAGES.add(new Stage(28, 8, 21.5f));
        TEST_STAGES.add(new Stage(29, 8, 22.5f));
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
