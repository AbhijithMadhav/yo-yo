package com.am.yo_yo.app;

import com.am.yo_yo.test.Stage;

import java.util.ArrayList;

/**
 * Created by amadhav on 23/12/17.
 */

public class Constants {

    // Intent extra's keys
    public static final String STAGE_INDEX = "stageIndex";
    public static final String SHUTTLES_REMAINING = "shuttlesRemaining";
    public static final String TEST_NAME = "testName";


    public static final Integer REST_COUNT_DOWN_INTERVAL_IN_MILLIS = 1000;
    public static final Integer SHUTTLE_COUNT_DOWN_INTERVAL_IN_MILLIS = 100;
    public static final Integer MILLIS_IN_ONE_SEC = 1000;

    public static final ArrayList<Stage> YY_IR_L1_STAGES = new ArrayList<>();
    static {
        YY_IR_L1_STAGES.add(new Stage(5f, 1, 10f));
        YY_IR_L1_STAGES.add(new Stage(8f, 1, 11.5f));
        YY_IR_L1_STAGES.add(new Stage(11f, 2, 13f));
        YY_IR_L1_STAGES.add(new Stage(12f, 3, 13.5f));
        YY_IR_L1_STAGES.add(new Stage(13f, 4, 14f));
        YY_IR_L1_STAGES.add(new Stage(14f, 8, 14.5f));
        YY_IR_L1_STAGES.add(new Stage(15f, 8, 15f));
        YY_IR_L1_STAGES.add(new Stage(16f, 8, 15.5f));
        YY_IR_L1_STAGES.add(new Stage(17f, 8, 16f));
        YY_IR_L1_STAGES.add(new Stage(18f, 8, 16.5f));
        YY_IR_L1_STAGES.add(new Stage(19f, 8, 17f));
        YY_IR_L1_STAGES.add(new Stage(20f, 8, 17.5f));
        YY_IR_L1_STAGES.add(new Stage(21f, 8, 18f));
        YY_IR_L1_STAGES.add(new Stage(22f, 8, 18.5f));
        YY_IR_L1_STAGES.add(new Stage(23f, 8, 19f));
    }

    public static final ArrayList<Stage> YY_IR_L2_STAGES = new ArrayList<>();
    static {
        YY_IR_L2_STAGES.add(new Stage(11f, 1, 13f));
        YY_IR_L2_STAGES.add(new Stage(15f, 1, 15f));
        YY_IR_L2_STAGES.add(new Stage(17f, 2, 16f));
        YY_IR_L2_STAGES.add(new Stage(18f, 3, 16.5f));
        YY_IR_L2_STAGES.add(new Stage(19f, 4, 17f));
        YY_IR_L2_STAGES.add(new Stage(20f, 8, 17.5f));
        YY_IR_L2_STAGES.add(new Stage(21f, 8, 18f));
        YY_IR_L2_STAGES.add(new Stage(22f, 8, 18.5f));
        YY_IR_L2_STAGES.add(new Stage(23f, 8, 19f));
        YY_IR_L2_STAGES.add(new Stage(24f, 8, 19.5f));
        YY_IR_L2_STAGES.add(new Stage(25f, 8, 20f));
        YY_IR_L2_STAGES.add(new Stage(26f, 8, 20.5f));
        YY_IR_L2_STAGES.add(new Stage(27f, 8, 21f));
        YY_IR_L2_STAGES.add(new Stage(28f, 8, 21.5f));
        YY_IR_L2_STAGES.add(new Stage(29f, 8, 22.5f));
    }

    public static final ArrayList<Stage> YY_IR_E1_STAGES = new ArrayList<>();

    static {
        YY_IR_E1_STAGES.add(new Stage(1f, 2, 8f));
        YY_IR_E1_STAGES.add(new Stage(3f, 2, 9f));
        YY_IR_E1_STAGES.add(new Stage(5f, 2, 10f));
        YY_IR_E1_STAGES.add(new Stage(6f, 2, 10.5f));
        YY_IR_E1_STAGES.add(new Stage(6.5f, 8, 10.75f));
        YY_IR_E1_STAGES.add(new Stage(7f, 8, 11f));
        YY_IR_E1_STAGES.add(new Stage(7.5f, 3, 11.25f));
        YY_IR_E1_STAGES.add(new Stage(8f, 3, 11.5f));
        YY_IR_E1_STAGES.add(new Stage(8.5f, 6, 11.75f));
        YY_IR_E1_STAGES.add(new Stage(9f, 6, 12f));
        YY_IR_E1_STAGES.add(new Stage(9.5f, 6, 12.25f));
        YY_IR_E1_STAGES.add(new Stage(10f, 6, 12.5f));
        YY_IR_E1_STAGES.add(new Stage(10.5f, 6, 12.75f));
        YY_IR_E1_STAGES.add(new Stage(11f, 6, 13f));
        YY_IR_E1_STAGES.add(new Stage(11.5f, 6, 13.25f));
        YY_IR_E1_STAGES.add(new Stage(12f, 6, 13.5f));
        YY_IR_E1_STAGES.add(new Stage(12.5f, 6, 13.75f));
        YY_IR_E1_STAGES.add(new Stage(13f, 6, 14f));
        YY_IR_E1_STAGES.add(new Stage(13.5f, 6, 14.25f));
        YY_IR_E1_STAGES.add(new Stage(14f, 6, 14.5f));
    }

    public static final ArrayList<Stage> YY_IR_E2_STAGES = new ArrayList<>();

    static {
        YY_IR_E2_STAGES.add(new Stage(8f, 2, 11.5f));
        YY_IR_E2_STAGES.add(new Stage(10f, 2, 12.5f));
        YY_IR_E2_STAGES.add(new Stage(12f, 2, 13.5f));
        YY_IR_E2_STAGES.add(new Stage(13f, 8, 14f));
        YY_IR_E2_STAGES.add(new Stage(13.5f, 8, 14.25f));
        YY_IR_E2_STAGES.add(new Stage(14f, 8, 14.5f));
        YY_IR_E2_STAGES.add(new Stage(14.5f, 3, 14.75f));
        YY_IR_E2_STAGES.add(new Stage(15f, 3, 15f));
        YY_IR_E2_STAGES.add(new Stage(15.5f, 6, 15.25f));
        YY_IR_E2_STAGES.add(new Stage(16f, 6, 15.5f));
        YY_IR_E2_STAGES.add(new Stage(16.5f, 6, 15.75f));
        YY_IR_E2_STAGES.add(new Stage(17f, 6, 16f));
        YY_IR_E2_STAGES.add(new Stage(17.5f, 6, 16.25f));
        YY_IR_E2_STAGES.add(new Stage(18f, 6, 16.5f));
        YY_IR_E2_STAGES.add(new Stage(18.5f, 6, 16.75f));
        YY_IR_E2_STAGES.add(new Stage(19f, 6, 17f));
        YY_IR_E2_STAGES.add(new Stage(19.5f, 6, 17.25f));
        YY_IR_E2_STAGES.add(new Stage(20f, 6, 17.5f));
        YY_IR_E2_STAGES.add(new Stage(20.5f, 6, 17.75f));
        YY_IR_E2_STAGES.add(new Stage(21f, 6, 18f));
        YY_IR_E2_STAGES.add(new Stage(21.5f, 6, 18.5f));
    }

}
