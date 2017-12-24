package com.am.yo_yo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import static com.am.yo_yo.Constants.SHUTTLES_REMAINING;
import static com.am.yo_yo.Constants.STAGES;
import static com.am.yo_yo.Constants.STAGE_INDEX;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(view -> {
            startActivity(
                    new Intent(HomeActivity.this, YoYoActivity.class)
                            .putExtra(STAGE_INDEX, 0)
                            .putExtra(SHUTTLES_REMAINING, STAGES.get(0).getNumShuttles())
            );
        });

    }


}
