package com.am.yo_yo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import static com.am.yo_yo.Constants.TEST_NAME;
import static com.am.yo_yo.Constants.YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1;
import static com.am.yo_yo.Constants.YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_2;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button yyirtL1StartButton = findViewById(R.id.yyirtL1StartButton);
        yyirtL1StartButton.setOnClickListener(view -> {
            startActivity(
                    new Intent(HomeActivity.this, YoYoActivity.class)
                            .putExtra(TEST_NAME, YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_1)
            );
        });

        Button yyirtL2StartButton = findViewById(R.id.yyirtL2StartButton);
        yyirtL2StartButton.setOnClickListener(view -> {
            startActivity(
                    new Intent(HomeActivity.this, YoYoActivity.class)
                            .putExtra(TEST_NAME, YO_YO_INTERMITTENT_RECOVERY_TEST_LEVEL_2)
            );
        });
    }


}
