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
        TEST_STAGES.add(new Stage(11f, 1, 13f));
        TEST_STAGES.add(new Stage(15f, 1, 15f));
        TEST_STAGES.add(new Stage(17f, 2, 16f));
        TEST_STAGES.add(new Stage(18f, 3, 16.5f));
        TEST_STAGES.add(new Stage(19f, 4, 17f));
        TEST_STAGES.add(new Stage(20f, 8, 17.5f));
        TEST_STAGES.add(new Stage(21f, 8, 18f));
        TEST_STAGES.add(new Stage(22f, 8, 18.5f));
        TEST_STAGES.add(new Stage(23f, 8, 19f));
        TEST_STAGES.add(new Stage(24f, 8, 19.5f));
        TEST_STAGES.add(new Stage(25f, 8, 20f));
        TEST_STAGES.add(new Stage(26f, 8, 20.5f));
        TEST_STAGES.add(new Stage(27f, 8, 21f));
        TEST_STAGES.add(new Stage(28f, 8, 21.5f));
        TEST_STAGES.add(new Stage(29f, 8, 22.5f));
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
