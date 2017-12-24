package com.am.yo_yo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import static com.am.yo_yo.Constants.TEST_NAME;

public class HomeActivity extends AppCompatActivity {

    private static final YoYoTest YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1 = new YoYoIntermittantRecoveryTestLevel1();
    private static final YoYoTest YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_2 = new YoYoIntermittantRecoveryTestLevel2();

    public static final Map<String, YoYoTest> TEST_MAP = new HashMap<>();
    static {
        TEST_MAP.put(YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1.testName(), YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1);
        TEST_MAP.put(YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_2.testName(), YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button yyirtL1StartButton = findViewById(R.id.yyirtL1StartButton);
        yyirtL1StartButton.setOnClickListener(view -> {
            startActivity(
                    new Intent(HomeActivity.this, YoYoActivity.class)
                            .putExtra(TEST_NAME, YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1.testName())
            );
        });

        Button yyirtL2StartButton = findViewById(R.id.yyirtL2StartButton);
        yyirtL2StartButton.setOnClickListener(view -> {
            startActivity(
                    new Intent(HomeActivity.this, YoYoActivity.class)
                            .putExtra(TEST_NAME, YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_2.testName())
            );
        });
    }


}
