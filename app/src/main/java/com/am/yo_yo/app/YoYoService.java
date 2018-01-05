package com.am.yo_yo.app;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.am.yo_yo.R;
import com.am.yo_yo.test.YoYoTest;

import static com.am.yo_yo.app.Constants.MILLIS_IN_ONE_SEC;
import static com.am.yo_yo.app.Constants.SHUTTLE_COUNT_DOWN_INTERVAL_IN_MILLIS;
import static com.am.yo_yo.test.YoYoTest.SHUTTLE_LENGTH_IN_METERS;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class YoYoService extends Service {

    private static final String TAG = YoYoService.class.getSimpleName();

    private YoYoUIModel yoYoUIModel;
    private YoYoTest yoYoTest;

    private CountDownTimer restCountDownTimer;
    private CountDownTimer shuttleCountDownTimer;

    private MediaPlayer tickMediaPlayer;
    private MediaPlayer beepMediaPlayer;
    private MediaPlayer halfBeepMediaPlayer;
    private MediaPlayer[] restCountDownMediaPlayer;


    class LocalBinder extends Binder {
        YoYoService getService() {
            return YoYoService.this;
        }
    }
    private final IBinder yoyoServiceBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return yoyoServiceBinder;
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");

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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand");

        yoYoTest = HomeActivity.TEST_MAP.get(intent.getStringExtra(Constants.TEST_NAME));

        yoYoUIModel = new YoYoUIModel();
        yoYoUIModel.setCurrentStageIndex(0);
        yoYoUIModel.setShuttlesRemaining(yoYoTest.testStages().get(0).getNumShuttles());

        // Display a notification about us starting.  We put an icon in the status bar.
        // showNotification();

        startRun();

        return START_STICKY;
    }

    private void startRun() {
        restCountDown();
    }

    private void restCountDown() {

        yoYoUIModel.setYoYoPhase(YoYoUIModel.YoYoPhase.REST);


        // storing timer in reference variable so as to get a handle to cancel the same in some other life cycle stage of the activity
        restCountDownTimer = new CountDownTimer(yoYoTest.restIntervalInMills(), Constants.REST_COUNT_DOWN_INTERVAL_IN_MILLIS) {

            private int index = (int) (yoYoTest.restIntervalInMills()/MILLIS_IN_ONE_SEC) - 1;
            public void onTick(long millisUntilFinished) {
                //tickMediaPlayer.start();
                restCountDownMediaPlayer[index--].start();
                yoYoUIModel.setRemainingTimeInSecs(millisUntilFinished / MILLIS_IN_ONE_SEC);
            }

            public void onFinish() {

                restCountDownMediaPlayer[0].start();
                //beepMediaPlayer.start();
                if (yoYoUIModel.getShuttlesRemaining() > 0) {
                    shuttleCountDown();
                }
                else {
                    Integer nextStageIndex = yoYoUIModel.getCurrentStageIndex() + 1;
                    if (nextStageIndex < yoYoTest.testStages().size()) {
                        yoYoUIModel.setCurrentStageIndex(nextStageIndex);
                        yoYoUIModel.setShuttlesRemaining(yoYoTest.testStages().get(nextStageIndex).getNumShuttles());
                        shuttleCountDown();
                    } else {
                        throw new RuntimeException("No rest after the last stage and the last shuttle");
                    }
                }

            }
        }.start();
    }


    private void shuttleCountDown() {

        yoYoUIModel.setYoYoPhase(YoYoUIModel.YoYoPhase.SHUTTLE);

        long timeToCompleteShuttleInMillis = (long)((SHUTTLE_LENGTH_IN_METERS * MILLIS_IN_ONE_SEC/ yoYoTest.testStages().get(yoYoUIModel.getCurrentStageIndex()).getSpeedInMps()));

        // storing timer in reference variable so as to get a handle to cancel the same in some other life cycle stage of the activity
        shuttleCountDownTimer = new CountDownTimer(timeToCompleteShuttleInMillis, SHUTTLE_COUNT_DOWN_INTERVAL_IN_MILLIS) {

            private Boolean halfBeep = TRUE;

            public void onTick(long millisUntilFinished) {
                yoYoUIModel.setRemainingTimeInSecs(millisUntilFinished / (double)MILLIS_IN_ONE_SEC);
                if (timeToCompleteShuttleInMillis/millisUntilFinished == 2 && halfBeep) {
                    halfBeepMediaPlayer.start();
                    halfBeep = FALSE;
                }
            }

            public void onFinish() {

                beepMediaPlayer.start();
                int remainingShuttles = yoYoUIModel.getShuttlesRemaining() - 1;
                if (remainingShuttles > 0) {
                    yoYoUIModel.setShuttlesRemaining(remainingShuttles);
                    restCountDown();
                }
                else {
                    Integer nextStageIndex = yoYoUIModel.getCurrentStageIndex() + 1;
                    if (nextStageIndex < yoYoTest.testStages().size()) {
                        yoYoUIModel.setCurrentStageIndex(nextStageIndex);
                        yoYoUIModel.setShuttlesRemaining(yoYoTest.testStages().get(nextStageIndex).getNumShuttles());
                        restCountDown();
                    } else {
                        yoYoUIModel.setShuttlesRemaining(0);
                        stopSelf();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");

        // Tell the user we stopped.
        Toast.makeText(this, R.string.local_service_stopped, Toast.LENGTH_SHORT).show();

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

    public YoYoUIModel getUIModel() {
        return yoYoUIModel;
    }


}
