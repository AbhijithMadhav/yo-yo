package com.am.yo_yo.app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.am.yo_yo.R;
import com.am.yo_yo.test.Stage;
import com.am.yo_yo.test.YoYoTest;

import java.text.DecimalFormat;

import static com.am.yo_yo.app.Constants.MILLIS_IN_ONE_SEC;
import static com.am.yo_yo.app.Constants.SHUTTLE_COUNT_DOWN_INTERVAL_IN_MILLIS;
import static com.am.yo_yo.test.YoYoTest.SHUTTLE_LENGTH_IN_METERS;
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

    private YoYoTest yoYoTest;
    private Integer currentStageIndex;
    private Integer shuttlesRemaining;

    private MediaPlayer tickMediaPlayer;
    private MediaPlayer beepMediaPlayer;
    private MediaPlayer halfBeepMediaPlayer;

    private MediaPlayer[] restCountDownMediaPlayer;

    private static final String TAG = YoYoActivity.class.getSimpleName();

    private Boolean fromOnCreate = FALSE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yoyo);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        testNameView = findViewById(R.id.testName);
        testName = getIntent().getStringExtra(Constants.TEST_NAME);
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
        yoYoTest = HomeActivity.TEST_MAP.get(getIntent().getStringExtra(Constants.TEST_NAME));
        currentStageIndex = 0;
        shuttlesRemaining = yoYoTest.testStages().get(currentStageIndex).getNumShuttles();

        // Stop
        stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(view -> {
            cancelTimers();
            startActivity(
                    new Intent(this, CompletedActivity.class)
                            .putExtra(Constants.TEST_NAME, testName)
                            .putExtra(Constants.STAGE_INDEX, currentStageIndex)
                            .putExtra(Constants.SHUTTLES_REMAINING, shuttlesRemaining)
            );
            YoYoActivity.this.finish();
        });

        // For timer beeps
        // https://www.soundjay.com/beep-sounds-1.html
        tickMediaPlayer = MediaPlayer.create(this, R.raw.beep07);
        beepMediaPlayer = MediaPlayer.create(this, R.raw.beep01a);
        halfBeepMediaPlayer = MediaPlayer.create(this, R.raw.beep02);

        restCountDownMediaPlayer = new MediaPlayer[11];
        restCountDownMediaPlayer[0] = MediaPlayer.create(this, R.raw.go);
        restCountDownMediaPlayer[1] = MediaPlayer.create(this, R.raw.one);
        restCountDownMediaPlayer[2] = MediaPlayer.create(this, R.raw.two);
        restCountDownMediaPlayer[3] = MediaPlayer.create(this, R.raw.three);
        restCountDownMediaPlayer[4] = MediaPlayer.create(this, R.raw.four);
        restCountDownMediaPlayer[5] = MediaPlayer.create(this, R.raw.five);
        restCountDownMediaPlayer[6] = MediaPlayer.create(this, R.raw.six);
        restCountDownMediaPlayer[7] = MediaPlayer.create(this, R.raw.seven);
        restCountDownMediaPlayer[8] = MediaPlayer.create(this, R.raw.eight);
        restCountDownMediaPlayer[9] = MediaPlayer.create(this, R.raw.nine);
        restCountDownMediaPlayer[10] = MediaPlayer.create(this, R.raw.ten);

        fromOnCreate = TRUE;
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Want to start a new run only if state is changing from onCreate -> onStart
        // Not if comming of onStop -> onRestart -> onStart which indicates that the app was bought into foreground. Just continue the existing run
        if (fromOnCreate)
            startRun(currentStageIndex, shuttlesRemaining);
        fromOnCreate = FALSE;
    }


    private void startRun(Integer currentStageIndex, Integer shuttlesRemaining) {
        restCountDown(currentStageIndex, shuttlesRemaining);
    }

    private void restCountDown(final Integer currentStageIndex, final Integer shuttlesRemaining) {
        updateStatsForRest(currentStageIndex, shuttlesRemaining);

        // storing timer in reference variable so as to get a handle to cancel the same in some other life cycle stage of the activity
        restCountDownTimer = new CountDownTimer(yoYoTest.restIntervalInMills(), Constants.REST_COUNT_DOWN_INTERVAL_IN_MILLIS) {

            private int count = (int) (yoYoTest.restIntervalInMills()/MILLIS_IN_ONE_SEC) - 1;
            private Boolean tick = FALSE;
            public void onTick(long millisUntilFinished) {
                if (tick)
                    //tickMediaPlayer.start();
                    restCountDownMediaPlayer[count--].start();
                tick = !tick;
                remainingTimeView.setText(String.valueOf(millisUntilFinished / MILLIS_IN_ONE_SEC));
            }

            public void onFinish() {

                restCountDownMediaPlayer[0].start();
                //beepMediaPlayer.start();
                if (shuttlesRemaining > 0) {
                    shuttleCountDown(currentStageIndex, shuttlesRemaining);
                }
                else {
                    Integer nextStageIndex = currentStageIndex + 1;
                    if (nextStageIndex < yoYoTest.testStages().size()) {
                        shuttleCountDown(nextStageIndex, yoYoTest.testStages().get(nextStageIndex).getNumShuttles());
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

        Stage currentStage = yoYoTest.testStages().get(currentStageIndex);

        long timeToCompleteShuttleInMillis = (long)((SHUTTLE_LENGTH_IN_METERS * MILLIS_IN_ONE_SEC/ currentStage.getSpeedInMps()));

        // storing timer in reference variable so as to get a handle to cancel the same in some other life cycle stage of the activity
        shuttleCountDownTimer = new CountDownTimer(timeToCompleteShuttleInMillis, SHUTTLE_COUNT_DOWN_INTERVAL_IN_MILLISq) {

            private final DecimalFormat SINGLE_DIGIT_FORMAT = new DecimalFormat("#.#");
            private Boolean halfBeep = TRUE;

            public void onTick(long millisUntilFinished) {
                remainingTimeView.setText(SINGLE_DIGIT_FORMAT.format(millisUntilFinished / (double)MILLIS_IN_ONE_SEC));
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
                    if (nextStageIndex < yoYoTest.testStages().size()) {
                        restCountDown(nextStageIndex, yoYoTest.testStages().get(nextStageIndex).getNumShuttles());
                        YoYoActivity.this.currentStageIndex = nextStageIndex;
                        YoYoActivity.this.shuttlesRemaining = yoYoTest.testStages().get(nextStageIndex).getNumShuttles();
                    } else {
                        YoYoActivity.this.currentStageIndex = currentStageIndex;
                        YoYoActivity.this.shuttlesRemaining = 0;
                        startActivity(
                                new Intent(YoYoActivity.this, CompletedActivity.class)
                                        .putExtra(Constants.STAGE_INDEX, currentStageIndex)
                                        .putExtra(Constants.SHUTTLES_REMAINING, 0)
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
        distanceCoveredView.setText(String.valueOf(yoYoTest.distanceCoveredInM(currentStageIndex, shuttlesRemaining)));
        totalShuttlesCompletedView.setText(String.valueOf(yoYoTest.shuttlesCompleted(currentStageIndex, shuttlesRemaining)));

        currentShuttleStageView.setText(String.valueOf(yoYoTest.testStages().get(currentStageIndex).getSpeedLevel()));
        this.shuttlesRemainingView.setText(String.valueOf(shuttlesRemaining));
        currentSpeedView.setText(String.valueOf(yoYoTest.testStages().get(currentStageIndex).getSpeedInKph()));
        currentSpeedUnitsView.setText("Kph");


        Integer nextStageIndex = currentStageIndex + 1;
        Stage nextStage;
        if (nextStageIndex < yoYoTest.testStages().size()) {
            nextStage = yoYoTest.testStages().get(nextStageIndex);
            upcomingShuttleStageView.setText(String.valueOf(nextStage.getSpeedLevel()));
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

        for (int i = 0 ; i < restCountDownMediaPlayer.length; i++) {
            restCountDownMediaPlayer[i].release();
            restCountDownMediaPlayer[i] = null;
        }
    }

    private void cancelTimers() {
        if (restCountDownTimer != null)
            restCountDownTimer.cancel();
        if (shuttleCountDownTimer != null)
            shuttleCountDownTimer.cancel();
    }
}