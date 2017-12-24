package com.am.yo_yo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amadhav on 24/12/17.
 */

public class YoYoIntermittantRecoveryTestLevel1 implements YoYoTest {

    public static final ArrayList<Stage> STAGES = new ArrayList<>();

    static {
        STAGES.add(new Stage(5, 1, 10f));
        STAGES.add(new Stage(8, 1, 11.5f));
        STAGES.add(new Stage(11, 2, 13f));
        STAGES.add(new Stage(12, 3, 13.5f));
        STAGES.add(new Stage(13, 4, 14f));
        STAGES.add(new Stage(14, 8, 14.5f));
        STAGES.add(new Stage(15, 8, 15f));
        STAGES.add(new Stage(16, 8, 15.5f));
        STAGES.add(new Stage(17, 8, 16f));
        STAGES.add(new Stage(18, 8, 16.5f));
        STAGES.add(new Stage(19, 8, 17f));
        STAGES.add(new Stage(20, 8, 17.5f));
        STAGES.add(new Stage(21, 8, 18f));
        STAGES.add(new Stage(22, 8, 18.5f));
        STAGES.add(new Stage(23, 8, 19f));
    }

    @Override
    public ArrayList<Stage> stages() {
        return STAGES;
    }
}
