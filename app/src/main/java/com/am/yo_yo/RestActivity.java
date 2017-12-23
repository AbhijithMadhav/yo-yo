package com.am.yo_yo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;


import static com.am.yo_yo.Constants.COUNT_DOWN_INTERVAL_IN_MILLIS;
import static com.am.yo_yo.Constants.MILLIS_IN_ONE_SEC;
import static com.am.yo_yo.Constants.REST_TIME_IN_MILLIS;
import static com.am.yo_yo.Constants.SHUTTLES_REMAINING;
import static com.am.yo_yo.Constants.STAGES;
import static com.am.yo_yo.Constants.STAGE_INDEX;
import static java.lang.Boolean.FALSE;

public class RestActivity extends AppCompatActivity {


    private TextView remainingTimeView;
    private TextView distanceCoveredView;
    private TextView totalShuttlesCompletedView;

    private Switch detailsSwitch;

    private LinearLayout currentStageStatsLayout;
    private TextView currentShuttleStageView;
    private TextView shuttlesRemainingView;
    private TextView currentSpeedView;
    private TextView currentSpeedUnitsView;

    private LinearLayout upcomingStageStatsLayout;
    private TextView upcomingShuttleStageView;
    private TextView numberOfShuttlesView;
    private TextView upcomingSpeedView;
    private TextView upcomingSpeedUnitsView;

    private Button stopButton;
    private CountDownTimer restCountDownTimer;

    private Integer currentStageIndex;
    private Stage currentStage;
    private Integer shuttlesRemaining;

    private MediaPlayer tickMediaPlayer;
    private MediaPlayer beepMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest);

        // UI
        remainingTimeView = findViewById(R.id.remainingTime);
        distanceCoveredView = findViewById(R.id.distanceCovered);
        totalShuttlesCompletedView = findViewById(R.id.shuttlesCompleted);

        currentStageStatsLayout = findViewById(R.id.currentStageStatsLayout);
        currentShuttleStageView = findViewById(R.id.currentShuttleStage);
        shuttlesRemainingView = findViewById(R.id.shuttlesRemaining);
        currentSpeedView = findViewById(R.id.currentSpeed);
        currentSpeedUnitsView = findViewById(R.id.currentSpeedUnits);

        // UI
        upcomingStageStatsLayout = findViewById(R.id.upcomingStageLayout);
        upcomingShuttleStageView = findViewById(R.id.upcomingShuttleStage);
        numberOfShuttlesView = findViewById(R.id.numberOfShuttles);
        upcomingSpeedView = findViewById(R.id.upcomingSpeed);
        upcomingSpeedUnitsView = findViewById(R.id.upcomingSpeedUnits);

        detailsSwitch = findViewById(R.id.detailsSwitch);
        detailsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                currentStageStatsLayout.setVisibility(LinearLayout.VISIBLE);
                upcomingStageStatsLayout.setVisibility(LinearLayout.VISIBLE);
            }
            else {
                currentStageStatsLayout.setVisibility(LinearLayout.GONE);
                upcomingStageStatsLayout.setVisibility(LinearLayout.GONE);
            }

        });
        detailsSwitch.setChecked(FALSE);
        currentStageStatsLayout.setVisibility(LinearLayout.GONE);
        upcomingStageStatsLayout.setVisibility(LinearLayout.GONE);

        // Input from intent
        currentStageIndex = getIntent().getIntExtra(STAGE_INDEX, 0);
        currentStage = STAGES.get(currentStageIndex);
        shuttlesRemaining = getIntent().getIntExtra(SHUTTLES_REMAINING, currentStage.getNumShuttles());

        restCountDownTimer = restCountDownTimer();

        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(view -> {
            restCountDownTimer.cancel();
            startActivity(
                    new Intent(this, CompletedActivity.class)
                            .putExtra(STAGE_INDEX, currentStageIndex)
                            .putExtra(SHUTTLES_REMAINING, shuttlesRemaining)
            );
            RestActivity.this.finish();
        });

        // https://www.soundjay.com/beep-sounds-1.html
        tickMediaPlayer = MediaPlayer.create(this, R.raw.beep07);
        beepMediaPlayer = MediaPlayer.create(this, R.raw.beep01a);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Integer currentStageIndex = getIntent().getIntExtra(STAGE_INDEX, 0);
        Stage currentStage = STAGES.get(currentStageIndex);
        Integer shuttlesRemaining = getIntent().getIntExtra(SHUTTLES_REMAINING, currentStage.getNumShuttles());


        distanceCoveredView.setText(String.valueOf(Utils.distanceCoveredInM(currentStageIndex, shuttlesRemaining)));
        totalShuttlesCompletedView.setText(String.valueOf(Utils.shuttlesCompleted(currentStageIndex, shuttlesRemaining)));

        currentShuttleStageView.setText(String.valueOf(currentStage.getStageId()));
        this.shuttlesRemainingView.setText(String.valueOf(shuttlesRemaining));
        currentSpeedView.setText(String.valueOf(currentStage.getSpeedInKph()));
        currentSpeedUnitsView.setText("Kph");


        Integer nextStageIndex = currentStageIndex + 1;
        Stage nextStage;
        if (nextStageIndex < STAGES.size()) {
            nextStage = STAGES.get(nextStageIndex);
            upcomingShuttleStageView.setText(String.valueOf(nextStage.getStageId()));
            numberOfShuttlesView.setText(String.valueOf(nextStage.getNumShuttles()));
            upcomingSpeedView.setText(String.valueOf(nextStage.getSpeedInKph()));
            upcomingSpeedUnitsView.setText("Kph");
        } else {
            upcomingShuttleStageView.setText("-");
            numberOfShuttlesView.setText("-");
            upcomingSpeedView.setText("-");
            upcomingSpeedUnitsView.setText("");
        }


        restCountDownTimer.start();
    }

    private CountDownTimer restCountDownTimer() {

        return new CountDownTimer(REST_TIME_IN_MILLIS, COUNT_DOWN_INTERVAL_IN_MILLIS) {

            private Boolean tick = FALSE;
            public void onTick(long millisUntilFinished) {
                remainingTimeView.setText(String.valueOf(millisUntilFinished / MILLIS_IN_ONE_SEC));
                if (tick)
                    tickMediaPlayer.start();
                tick = !tick;
            }

            public void onFinish() {

                beepMediaPlayer.start();
                if (shuttlesRemaining > 0) {
                    startActivity(new Intent(RestActivity.this, ShuttleActivity.class)
                            .putExtra(STAGE_INDEX, currentStageIndex)
                            .putExtra(SHUTTLES_REMAINING, shuttlesRemaining)
                    );
                }
                else {
                    Integer nextStageIndex = currentStageIndex + 1;
                    if (nextStageIndex < STAGES.size()) {
                        startActivity(new Intent(RestActivity.this, ShuttleActivity.class)
                                .putExtra(STAGE_INDEX, nextStageIndex)
                                .putExtra(SHUTTLES_REMAINING, STAGES.get(nextStageIndex).getNumShuttles())
                        );
                    } else {
                        throw new RuntimeException("No rest after the last stage and the last shuttle");
                    }
                }
                RestActivity.this.finish();
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        restCountDownTimer.cancel();
        tickMediaPlayer.release();
        tickMediaPlayer = null;
        beepMediaPlayer.release();
        beepMediaPlayer = null;
    }
}