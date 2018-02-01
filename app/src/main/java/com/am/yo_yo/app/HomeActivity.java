package com.am.yo_yo.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.am.yo_yo.R;
import com.am.yo_yo.test.YoYoTest;

import static com.am.yo_yo.app.Constants.TEST_NAME;
import static com.am.yo_yo.app.Constants.YY_IR_E1_STAGES;
import static com.am.yo_yo.app.Constants.YY_IR_E2_STAGES;
import static com.am.yo_yo.app.Constants.YY_IR_L1_STAGES;
import static com.am.yo_yo.app.Constants.YY_IR_L2_STAGES;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView yyitView = findViewById(R.id.yyitTest);
        yyitView.setMovementMethod(LinkMovementMethod.getInstance());

        TextView yyirtTestLevelsView = findViewById(R.id.yyirtTestLevels);
        yyirtTestLevelsView.setMovementMethod(LinkMovementMethod.getInstance());

        Button yyirtL1StartButton = findViewById(R.id.yyirtL1StartButton);
        YoYoTest yyirtL1Test = new YoYoTest(
                "Yo-Yo Intermittent Recovery Test - Level 1",
                "http://www.topendsports.com/testing/norms/yo-yo.htm",
                10000L,
                YY_IR_L1_STAGES
        );
        yyirtL1StartButton.setOnClickListener(view -> {
            startService(new Intent(this, YoYoService.class).putExtra(TEST_NAME, yyirtL1Test));
            startActivity(new Intent(HomeActivity.this, YoYoActivity.class).putExtra(TEST_NAME, yyirtL1Test));
        });


        Button yyirtL2StartButton = findViewById(R.id.yyirtL2StartButton);
        YoYoTest yyirtL2Test = new YoYoTest(
                "Yo-Yo Intermittent Recovery Test - Level 2",
                "http://www.topendsports.com/testing/norms/yo-yo.htm",
                10000L,
                YY_IR_L2_STAGES
        );
        yyirtL2StartButton.setOnClickListener(view -> {
            startService(new Intent(this, YoYoService.class).putExtra(TEST_NAME, yyirtL2Test));
            startActivity(new Intent(HomeActivity.this, YoYoActivity.class).putExtra(TEST_NAME, yyirtL2Test));
        });

        TextView yyietTestLevelsView = findViewById(R.id.yyietTestLevels);
        yyietTestLevelsView.setMovementMethod(LinkMovementMethod.getInstance());

        Button yyietL1StartButton = findViewById(R.id.yyietL1StartButton);
        YoYoTest yyietL1Test = new YoYoTest(
                "Yo-Yo Intermittent Endurance Test - Level 1",
                "http://www.topendsports.com/testing/norms/beep.htm",
                5000L,
                YY_IR_E1_STAGES
        );
        yyietL1StartButton.setOnClickListener(view -> {
            startService(new Intent(this, YoYoService.class).putExtra(TEST_NAME, yyietL1Test));
            startActivity(new Intent(HomeActivity.this, YoYoActivity.class).putExtra(TEST_NAME, yyietL1Test));
        });

        Button yyietL2StartButton = findViewById(R.id.yyietL2StartButton);
        YoYoTest yyietL2Test = new YoYoTest(
                "Yo-Yo Intermittent Endurance Test - Level 2",
                "http://www.topendsports.com/testing/norms/beep.htm",
                5000L,
                YY_IR_E2_STAGES
        );
        yyietL2StartButton.setOnClickListener(view -> {
            startService(new Intent(this, YoYoService.class).putExtra(TEST_NAME, yyietL2Test));
            startActivity(new Intent(HomeActivity.this, YoYoActivity.class).putExtra(TEST_NAME, yyietL2Test));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // launch settings activity
            startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
