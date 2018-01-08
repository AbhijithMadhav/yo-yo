package com.am.yo_yo.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.am.yo_yo.R;
import com.am.yo_yo.test.Stage;
import com.am.yo_yo.test.YoYoTest;

import java.util.Timer;
import java.util.TimerTask;

import static com.am.yo_yo.app.Constants.TEST_NAME;
import static java.lang.Boolean.FALSE;

public class YoYoActivity extends AppCompatActivity {

    private YoYoTest yoYoTest;

    private TextView remainingTimeLabel;
    private TextView remainingTimeView;
    private TextView distanceCoveredView;
    private TextView totalShuttlesCompletedView;

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

    private static final String TAG = YoYoActivity.class.getSimpleName();

    private YoYoUIModel yoYoUIModel;

    private ServiceConnection serviceConnection;

    private NotificationManager notificationManager;
    private final static Integer NOTIFICATION_ID = R.string.local_service_started;
    private Notification notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "OnCreate");

        setContentView(R.layout.activity_yoyo);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


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

        Switch detailsSwitch = findViewById(R.id.detailsSwitch);
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

        TextView testNameView = findViewById(R.id.testName);
        String testName = getIntent().getStringExtra(TEST_NAME);
        testNameView.setText(testName);

        // Stop
        Button stopButton = findViewById(R.id.stopButton);
        stopButton.setOnClickListener(view -> {
            stopService(new Intent(this, YoYoService.class).putExtra(TEST_NAME, testName));
            startActivity(
                    new Intent(this, CompletedActivity.class)
                            .putExtra(TEST_NAME, testName)
                            .putExtra(Constants.STAGE_INDEX, yoYoUIModel.getCurrentStageIndex())
                            .putExtra(Constants.SHUTTLES_REMAINING, yoYoUIModel.getShuttlesRemaining())
            );
            YoYoActivity.this.finish();
        });

        yoYoTest = HomeActivity.TEST_MAP.get(testName);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.i("ServiceConnection", "onServiceConnected");
                YoYoService.LocalBinder binder = (YoYoService.LocalBinder) service;
                yoYoUIModel = binder.getService().getUIModel();
                //Log.i("ServiceConnection", "ui model : " + yoYoUIModel);
                //isBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("ServiceConnection", "onServiceDisconnected");
                yoYoUIModel = null;
                //isBound = false;
            }
        };


        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        PendingIntent contentIntent = PendingIntent.getActivity(
                this,
                0,
                new Intent(this, YoYoActivity.class).putExtra(TEST_NAME, yoYoTest.testName()),
                0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.notification_channel_id), getString(R.string.channel_name), NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notification = new NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
                .setSmallIcon(R.drawable.ic_launcher_background)  // the status icon
                .setTicker(yoYoTest.testName())  // the status text
                .setWhen(System.currentTimeMillis())  // the time stamp
                .setContentTitle(yoYoTest.testName())  // the label of the entry
                .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();

        notificationManager.cancel(NOTIFICATION_ID);
        Log.i(TAG, "onStart");
        bindService(
                new Intent(this, YoYoService.class).putExtra(TEST_NAME, yoYoTest.testName()),
                serviceConnection,
                BIND_AUTO_CREATE
        );

        new Timer("uiUpdater", true).schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> updateView());

            }
        }, 0, 100);
    }

    private void updateView(){
        if (yoYoUIModel == null)
            return;

        final String phaseLabel = yoYoUIModel.getYoYoPhase().name();
        final Integer currentStageIndex = yoYoUIModel.getCurrentStageIndex();
        final Integer shuttlesRemaining = yoYoUIModel.getShuttlesRemaining();

        remainingTimeLabel.setText(phaseLabel);
        remainingTimeView.setText(String.valueOf(yoYoUIModel.getRemainingTimeInSecs()));
        distanceCoveredView.setText(String.valueOf(yoYoTest.distanceCoveredInM(currentStageIndex, shuttlesRemaining)));
        totalShuttlesCompletedView.setText(String.valueOf(yoYoTest.shuttlesCompleted(currentStageIndex, shuttlesRemaining)));

        currentShuttleStageView.setText(String.valueOf(yoYoTest.testStages().get(currentStageIndex).getSpeedLevel()));
        shuttlesRemainingView.setText(String.valueOf(shuttlesRemaining));
        currentSpeedView.setText(String.valueOf(yoYoTest.testStages().get(currentStageIndex).getSpeedInKph()));
        currentSpeedUnitsView.setText(R.string.kph);


        Integer nextStageIndex = currentStageIndex + 1;
        Stage nextStage;
        if (nextStageIndex < yoYoTest.testStages().size()) {
            nextStage = yoYoTest.testStages().get(nextStageIndex);
            upcomingShuttleStageView.setText(String.valueOf(nextStage.getSpeedLevel()));
            numberOfShuttlesView.setText(String.valueOf(nextStage.getNumShuttles()));
            upcomingSpeedView.setText(String.valueOf(nextStage.getSpeedInKph()));
            upcomingSpeedUnitsView.setText(R.string.kph);
        } else {
            upcomingShuttleStageView.setText("-");
            numberOfShuttlesView.setText("-");
            upcomingSpeedView.setText("-");
            upcomingSpeedUnitsView.setText("");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");

        // Send the notification.
        notificationManager.notify(NOTIFICATION_ID, notification);
        unbindService(serviceConnection);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        String testName = getIntent().getStringExtra(TEST_NAME);
        stopService(new Intent(this, YoYoService.class).putExtra(TEST_NAME, testName));
        notificationManager.cancel(NOTIFICATION_ID);
    }
}