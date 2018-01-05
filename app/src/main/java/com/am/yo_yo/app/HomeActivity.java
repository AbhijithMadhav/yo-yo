package com.am.yo_yo.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.am.yo_yo.R;
import com.am.yo_yo.test.YoYoIntermittantEnduranceTestLevel1;
import com.am.yo_yo.test.YoYoIntermittantEnduranceTestLevel2;
import com.am.yo_yo.test.YoYoIntermittantRecoveryTestLevel1;
import com.am.yo_yo.test.YoYoIntermittantRecoveryTestLevel2;
import com.am.yo_yo.test.YoYoTest;

import java.util.HashMap;
import java.util.Map;

import static com.am.yo_yo.app.Constants.TEST_NAME;

public class HomeActivity extends AppCompatActivity {

    private static final YoYoTest YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1 = new YoYoIntermittantRecoveryTestLevel1();
    private static final YoYoTest YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_2 = new YoYoIntermittantRecoveryTestLevel2();
    private static final YoYoTest YO_YO_INTERMITTENT_ENDURANCE_TEST_LEVEL_1 = new YoYoIntermittantEnduranceTestLevel1();
    private static final YoYoTest YO_YO_INTERMITTENT_ENDURANCE_TEST_LEVEL_2 = new YoYoIntermittantEnduranceTestLevel2();

    public static final Map<String, YoYoTest> TEST_MAP = new HashMap<>();
    static {
        TEST_MAP.put(YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1.testName(), YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1);
        TEST_MAP.put(YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_2.testName(), YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_2);
        TEST_MAP.put(YO_YO_INTERMITTENT_ENDURANCE_TEST_LEVEL_1.testName(), YO_YO_INTERMITTENT_ENDURANCE_TEST_LEVEL_1);
        TEST_MAP.put(YO_YO_INTERMITTENT_ENDURANCE_TEST_LEVEL_2.testName(), YO_YO_INTERMITTENT_ENDURANCE_TEST_LEVEL_2);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView yyitView = findViewById(R.id.yyitTest);
        yyitView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView yyirtTestLevelsView = findViewById(R.id.yyirtTestLevels);
        yyirtTestLevelsView.setMovementMethod(LinkMovementMethod.getInstance());

        Button yyirtL1StartButton = findViewById(R.id.yyirtL1StartButton);
        yyirtL1StartButton.setOnClickListener(view -> {
            Intent yoyoService = new Intent(this, YoYoService.class).putExtra(TEST_NAME, YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1.testName());
            startService(yoyoService);
            startActivity(
                    new Intent(HomeActivity.this, YoYoActivity.class)
                            .putExtra(Constants.TEST_NAME, YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1.testName())
            );
        });

        Button yyirtL2StartButton = findViewById(R.id.yyirtL2StartButton);
        yyirtL2StartButton.setOnClickListener(view -> {
            Intent yoyoService = new Intent(this, YoYoService.class).putExtra(TEST_NAME, YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_2.testName());
            startService(yoyoService);
            startActivity(
                    new Intent(HomeActivity.this, YoYoActivity.class)
                            .putExtra(Constants.TEST_NAME, YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_2.testName())
            );
        });

        TextView yyietTestLevelsView = findViewById(R.id.yyietTestLevels);
        yyietTestLevelsView.setMovementMethod(LinkMovementMethod.getInstance());

        Button yyietL1StartButton = findViewById(R.id.yyietL1StartButton);
        yyietL1StartButton.setOnClickListener(view -> {
            Intent yoyoService = new Intent(this, YoYoService.class).putExtra(TEST_NAME, YO_YO_INTERMITTENT_ENDURANCE_TEST_LEVEL_1.testName());
            startService(yoyoService);
            startActivity(
                    new Intent(HomeActivity.this, YoYoActivity.class)
                            .putExtra(Constants.TEST_NAME, YO_YO_INTERMITTENT_ENDURANCE_TEST_LEVEL_1.testName())
            );
        });

        Button yyietL2StartButton = findViewById(R.id.yyietL2StartButton);
        yyietL2StartButton.setOnClickListener(view -> {
            Intent yoyoService = new Intent(this, YoYoService.class).putExtra(TEST_NAME, YO_YO_INTERMITTENT_ENDURANCE_TEST_LEVEL_2.testName());
            startService(yoyoService);
            startActivity(
                    new Intent(HomeActivity.this, YoYoActivity.class)
                            .putExtra(Constants.TEST_NAME, YO_YO_INTERMITTENT_ENDURANCE_TEST_LEVEL_2.testName())
            );
        });

        TextView yyetView = findViewById(R.id.yyetTest);
        yyetView.setMovementMethod(LinkMovementMethod.getInstance());

        Button yyetL1StartButton = findViewById(R.id.yyetL1StartButton);
        yyetL1StartButton.setOnClickListener(view -> {
            Toast toast = Toast.makeText(getApplicationContext(), "Not Yet Implemented", Toast.LENGTH_SHORT);
            toast.show();
        });

        Button yyetL2StartButton = findViewById(R.id.yyetL2StartButton);
        yyetL2StartButton.setOnClickListener(view -> {
            Toast toast = Toast.makeText(getApplicationContext(), "Not Yet Implemented", Toast.LENGTH_SHORT);
            toast.show();
        });


    }


}
