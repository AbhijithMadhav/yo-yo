package com.am.yo_yo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import static com.am.yo_yo.Constants.COUNT_DOWN_INTERVAL_IN_MILLIS;
import static com.am.yo_yo.Constants.MILLIS_IN_ONE_SEC;
import static com.am.yo_yo.Constants.SHUTTLES_REMAINING;
import static com.am.yo_yo.Constants.STAGES;
import static com.am.yo_yo.Constants.STAGE_INDEX;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ShuttleActivity extends AppCompatActivity {

    private TextView remainingTimeView;

    private TextView currentShuttleStageView;
    private TextView shuttlesRemainingView;
    //private TextView currentSpeedLevelView;
    private TextView currentSpeedView;
    private TextView currentSpeedUnitsView;

    private CountDownTimer shuttleCountDownTimer;

    private Integer currentStageIndex;
    private Stage currentStage;
    private Integer shuttlesRemaining;

    private Button stopButton;
    private MediaPlayer beepMediaPlayer;
    private MediaPlayer halfBeepMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuttle);

        remainingTimeView = findViewById(R.id.remainingTime);
        currentShuttleStageView = findViewById(R.id.currentShuttleStage);
        shuttlesRemainingView = findViewById(R.id.shuttlesRemaining);
        //currentSpeedLevelView = findViewById(R.id.cucurrentSpeedLevel);
        currentSpeedView = findViewById(R.id.currentSpeed);
        currentSpeedUnitsView = findViewById(R.id.currentSpeedUnits);

        currentStageIndex = getIntent().getIntExtra(STAGE_INDEX, 0);
        currentStage = STAGES.get(currentStageIndex);
        shuttlesRemaining = getIntent().getIntExtra(SHUTTLES_REMAINING, currentStage.getNumShuttles());

        shuttleCountDownTimer = getShuttleCountDownTimer();

        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(view -> {
            shuttleCountDownTimer.cancel();
            startActivity(
                    new Intent(this, CompletedActivity.class)
                            .putExtra(STAGE_INDEX, currentStageIndex)
                            .putExtra(SHUTTLES_REMAINING, shuttlesRemaining)
            );
            ShuttleActivity.this.finish();
        });

        beepMediaPlayer = MediaPlayer.create(this, R.raw.beep01a);
        halfBeepMediaPlayer = MediaPlayer.create(this, R.raw.beep02);
    }

    @Override
    protected void onStart() {
        super.onStart();

        currentShuttleStageView.setText(String.valueOf(currentStage.getStageId()));
        shuttlesRemainingView.setText(String.valueOf(shuttlesRemaining));
        currentSpeedView.setText(String.valueOf(currentStage.getSpeedInKph()));
        currentSpeedUnitsView.setText("Kph");


        shuttleCountDownTimer.start();
    }

    private CountDownTimer getShuttleCountDownTimer() {
        final long timeToCompleteShuttleInMillis = (long)((Stage.DISTANCE_IN_METERS * MILLIS_IN_ONE_SEC/ currentStage.getSpeedInMps()));

        return new CountDownTimer(timeToCompleteShuttleInMillis, COUNT_DOWN_INTERVAL_IN_MILLIS) {

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

                Integer shuttlesRemaining = ShuttleActivity.this.shuttlesRemaining - 1;
                if (shuttlesRemaining > 0) {
                    startActivity(new Intent(ShuttleActivity.this, RestActivity.class)
                            .putExtra(STAGE_INDEX, currentStageIndex)
                            .putExtra(SHUTTLES_REMAINING, shuttlesRemaining)
                    );
                }
                else {
                    Integer nextStageIndex = currentStageIndex + 1;
                    if (nextStageIndex < STAGES.size()) {
                        startActivity(new Intent(ShuttleActivity.this, RestActivity.class)
                                .putExtra(STAGE_INDEX, nextStageIndex)
                                .putExtra(SHUTTLES_REMAINING, STAGES.get(nextStageIndex).getNumShuttles())
                        );
                    } else {
                        startActivity(
                                new Intent(ShuttleActivity.this, CompletedActivity.class)
                                        .putExtra(STAGE_INDEX, currentStageIndex)
                                        .putExtra(SHUTTLES_REMAINING, 0)
                        );
                    }
                }

                shuttleCountDownTimer.cancel();
                beepMediaPlayer.release();
                beepMediaPlayer = null;
                halfBeepMediaPlayer.release();
                halfBeepMediaPlayer = null;
            }
        };
    }
}
