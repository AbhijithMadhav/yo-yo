package com.am.yo_yo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import static com.am.yo_yo.Constants.COUNT_DOWN_INTERVAL_IN_MILLIS;
import static com.am.yo_yo.Constants.MILLIS_IN_ONE_SEC;
import static com.am.yo_yo.Constants.REST_TIME_IN_MILLIS;
import static com.am.yo_yo.Constants.SHUTTLES_REMAINING;
import static com.am.yo_yo.Constants.STAGE_INDEX;
import static com.am.yo_yo.Constants.TEST_NAME;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class YoYoActivity extends AppCompatActivity {


    private TextView testNameView;
    private String testName;

    private TextView remainingTimeLabel;
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
    private CountDownTimer shuttleCountDownTimer;

    private ArrayList<Stage> testStages;
    private Integer currentStageIndex;
    private Integer shuttlesRemaining;

    private MediaPlayer tickMediaPlayer;
    private MediaPlayer beepMediaPlayer;
    private MediaPlayer halfBeepMediaPlayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoyo);

        testNameView = findViewById(R.id.testName);
        testName = getIntent().getStringExtra(TEST_NAME);
        testNameView.setText(testName);

        // count down
        remainingTimeLabel = findViewById(R.id.remainingTimeLabel);
        remainingTimeView = findViewById(R.id.remainingTime);
        distanceCoveredView = findViewById(R.id.distanceCovered);
        totalShuttlesCompletedView = findViewById(R.id.shuttlesCompleted);

        // Detailed stats
        currentStageStatsLayout = findViewById(R.id.currentStageStatsLayout);
        currentShuttleStageView = findViewById(R.id.currentShuttleStage);
        shuttlesRemainingView = findViewById(R.id.shuttlesRemaining);
        currentSpeedView = findViewById(R.id.currentSpeed);
        currentSpeedUnitsView = findViewById(R.id.currentSpeedUnits);

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
        testStages = Constants.testStagesMap.get(getIntent().getStringExtra(TEST_NAME));
        currentStageIndex = 0;
        shuttlesRemaining = testStages.get(currentStageIndex).getNumShuttles();

        // Stop
        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(view -> {
            cancelTimers();
            startActivity(
                    new Intent(this, CompletedActivity.class)
                            .putExtra(TEST_NAME, testName)
                            .putExtra(STAGE_INDEX, currentStageIndex)
                            .putExtra(SHUTTLES_REMAINING, shuttlesRemaining)
            );
            YoYoActivity.this.finish();
        });

        // For timer beeps
        // https://www.soundjay.com/beep-sounds-1.html
        tickMediaPlayer = MediaPlayer.create(this, R.raw.beep07);
        beepMediaPlayer = MediaPlayer.create(this, R.raw.beep01a);
        halfBeepMediaPlayer = MediaPlayer.create(this, R.raw.beep02);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startRun(currentStageIndex, shuttlesRemaining);
    }

    private void startRun(Integer currentStageIndex, Integer shuttlesRemaining) {
        restCountDown(currentStageIndex, shuttlesRemaining);
    }

    private void restCountDown(final Integer currentStageIndex, final Integer shuttlesRemaining) {
        updateStatsForRest(currentStageIndex, shuttlesRemaining);

        // storing timer in reference variable so as to get a handle to cancel the same in some other life cycle stage of the activity
        restCountDownTimer = new CountDownTimer(REST_TIME_IN_MILLIS, COUNT_DOWN_INTERVAL_IN_MILLIS) {

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
                    shuttleCountDown(currentStageIndex, shuttlesRemaining);
                }
                else {
                    Integer nextStageIndex = currentStageIndex + 1;
                    if (nextStageIndex < testStages.size()) {
                        shuttleCountDown(nextStageIndex, testStages.get(nextStageIndex).getNumShuttles());
                    } else {
                        throw new RuntimeException("No rest after the last stage and the last shuttle");
                    }
                }

            }
        };
        restCountDownTimer.start();
    }


    private void shuttleCountDown(final Integer currentStageIndex, final Integer shuttlesRemaining) {

        updateStatusForShuttling(currentStageIndex, shuttlesRemaining);

        Stage currentStage = testStages.get(currentStageIndex);

        long timeToCompleteShuttleInMillis = (long)((Stage.DISTANCE_IN_METERS * MILLIS_IN_ONE_SEC/ currentStage.getSpeedInMps()));

        // storing timer in reference variable so as to get a handle to cancel the same in some other life cycle stage of the activity
        shuttleCountDownTimer = new CountDownTimer(timeToCompleteShuttleInMillis, COUNT_DOWN_INTERVAL_IN_MILLIS) {

            private Boolean halfBeep = TRUE;

            public void onTick(long millisUntilFinished) {
                remainingTimeView.setText(String.valueOf(millisUntilFinished / MILLIS_IN_ONE_SEC));
                if (timeToCompleteShuttleInMillis/millisUntilFinished == 2 && halfBeep) {
                    halfBeepMediaPlayer.start();
                    halfBeep = FALSE;
                }
            }

            public void onFinish() {

                beepMediaPlayer.start();
                int remainingShuttles = shuttlesRemaining - 1;
                if (remainingShuttles > 0) {
                    YoYoActivity.this.currentStageIndex = currentStageIndex;
                    YoYoActivity.this.shuttlesRemaining = remainingShuttles;

                    restCountDown(currentStageIndex, remainingShuttles);
                }
                else {
                    Integer nextStageIndex = currentStageIndex + 1;
                    if (nextStageIndex < testStages.size()) {
                        restCountDown(nextStageIndex, testStages.get(nextStageIndex).getNumShuttles());
                        YoYoActivity.this.currentStageIndex = nextStageIndex;
                        YoYoActivity.this.shuttlesRemaining = testStages.get(nextStageIndex).getNumShuttles();
                    } else {
                        YoYoActivity.this.currentStageIndex = currentStageIndex;
                        YoYoActivity.this.shuttlesRemaining = 0;
                        startActivity(
                                new Intent(YoYoActivity.this, CompletedActivity.class)
                                        .putExtra(STAGE_INDEX, currentStageIndex)
                                        .putExtra(SHUTTLES_REMAINING, 0)
                        );
                    }
                }
            }
        };
        shuttleCountDownTimer.start();
    }

    private void updateStatsForRest(final Integer currentStageIndex, final Integer shuttlesRemaining) {
        updateStats("REST", currentStageIndex, shuttlesRemaining);
    }

    private void updateStatusForShuttling(final Integer currentStageIndex, final Integer shuttlesRemaining) {
        updateStats("RUN", currentStageIndex, shuttlesRemaining);
    }

    private void updateStats(String label, final Integer currentStageIndex, final Integer shuttlesRemaining){
        remainingTimeLabel.setText(label);
        distanceCoveredView.setText(String.valueOf(Utils.distanceCoveredInM(testStages, currentStageIndex, shuttlesRemaining)));
        totalShuttlesCompletedView.setText(String.valueOf(Utils.shuttlesCompleted(testStages, currentStageIndex, shuttlesRemaining)));

        currentShuttleStageView.setText(String.valueOf(testStages.get(currentStageIndex).getStageId()));
        this.shuttlesRemainingView.setText(String.valueOf(shuttlesRemaining));
        currentSpeedView.setText(String.valueOf(testStages.get(currentStageIndex).getSpeedInKph()));
        currentSpeedUnitsView.setText("Kph");


        Integer nextStageIndex = currentStageIndex + 1;
        Stage nextStage;
        if (nextStageIndex < testStages.size()) {
            nextStage = testStages.get(nextStageIndex);
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
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimers();
        tickMediaPlayer.release();
        tickMediaPlayer = null;
        halfBeepMediaPlayer.release();
        halfBeepMediaPlayer = null;
        beepMediaPlayer.release();
        beepMediaPlayer = null;
    }

    private void cancelTimers() {
        if (restCountDownTimer != null)
            restCountDownTimer.cancel();
        if (shuttleCountDownTimer != null)
            shuttleCountDownTimer.cancel();
    }
}