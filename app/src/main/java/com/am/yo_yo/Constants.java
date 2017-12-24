package com.am.yo_yo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by amadhav on 23/12/17.
 */

public class Constants {

    // Intent extra's keys
    public static final String STAGE_INDEX = "stageIndex";
    public static final String SHUTTLES_REMAINING = "shuttlesRemaining";
    public static final String TEST_NAME = "testName";

    public static final String YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1 = "Yo-Yo Intermittent Recovery Test - Level 1";
    public static final ArrayList<Stage> YYIRT_L1_STAGES = new ArrayList<>();
    static {
        YYIRT_L1_STAGES.add(new Stage(5, 1, 10f));
        YYIRT_L1_STAGES.add(new Stage(8, 1, 11.5f));
        YYIRT_L1_STAGES.add(new Stage(11, 2, 13f));
        YYIRT_L1_STAGES.add(new Stage(12, 3, 13.5f));
        YYIRT_L1_STAGES.add(new Stage(13, 4, 14f));
        YYIRT_L1_STAGES.add(new Stage(14, 8, 14.5f));
        YYIRT_L1_STAGES.add(new Stage(15, 8, 15f));
        YYIRT_L1_STAGES.add(new Stage(16, 8, 15.5f));
        YYIRT_L1_STAGES.add(new Stage(17, 8, 16f));
        YYIRT_L1_STAGES.add(new Stage(18, 8, 16.5f));
        YYIRT_L1_STAGES.add(new Stage(19, 8, 17f));
        YYIRT_L1_STAGES.add(new Stage(20, 8, 17.5f));
        YYIRT_L1_STAGES.add(new Stage(21, 8, 18f));
        YYIRT_L1_STAGES.add(new Stage(22, 8, 18.5f));
        YYIRT_L1_STAGES.add(new Stage(23, 8, 19f));
    }

    public static Map<String, ArrayList<Stage>> testStagesMap = new HashMap<>();
    static {
        testStagesMap.put(YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1, YYIRT_L1_STAGES);
    }


    public static final Integer REST_TIME_IN_MILLIS = 11000;
    public static final Integer COUNT_DOWN_INTERVAL_IN_MILLIS = 500;
    public static final Integer MILLIS_IN_ONE_SEC = 1000;
    public static final Integer SHUTTLE_LENGTH_IN_METERS = 40;

}
