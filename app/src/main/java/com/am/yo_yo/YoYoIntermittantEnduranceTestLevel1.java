package com.am.yo_yo;

import java.util.ArrayList;

/**
 * Created by amadhav on 24/12/17.
 */

public class YoYoIntermittantEnduranceTestLevel1 extends YoYoTest {

    private static final String TEST_NAME = "Yo-Yo Intermittent Endurance Test - Level 1";
    private static final Long REST_TIME_IN_MILLIS = 6000L;
    private static final ArrayList<Stage> TEST_STAGES = new ArrayList<>();

    static {
        TEST_STAGES.add(new Stage(1f, 2, 8f));
        TEST_STAGES.add(new Stage(3f, 2, 9f));
        TEST_STAGES.add(new Stage(5f, 2, 10f));
        TEST_STAGES.add(new Stage(6f, 2, 10.5f));
        TEST_STAGES.add(new Stage(6.5f, 8, 10.75f));
        TEST_STAGES.add(new Stage(7f, 8, 11f));
        TEST_STAGES.add(new Stage(7.5f, 3, 11.25f));
        TEST_STAGES.add(new Stage(8f, 3, 11.5f));
        TEST_STAGES.add(new Stage(8.5f, 6, 11.75f));
        TEST_STAGES.add(new Stage(9f, 6, 12f));
        TEST_STAGES.add(new Stage(9.5f, 6, 12.25f));
        TEST_STAGES.add(new Stage(10f, 6, 12.5f));
        TEST_STAGES.add(new Stage(10.5f, 6, 12.75f));
        TEST_STAGES.add(new Stage(11f, 6, 13f));
        TEST_STAGES.add(new Stage(11.5f, 6, 13.25f));
        TEST_STAGES.add(new Stage(12f, 6, 13.5f));
        TEST_STAGES.add(new Stage(12.5f, 6, 13.75f));
        TEST_STAGES.add(new Stage(13f, 6, 14f));
        TEST_STAGES.add(new Stage(13.5f, 6, 14.25f));
        TEST_STAGES.add(new Stage(14f, 6, 14.5f));
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
