package com.am.yo_yo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

import static com.am.yo_yo.Constants.SHUTTLES_REMAINING;
import static com.am.yo_yo.Constants.STAGE_INDEX;
import static com.am.yo_yo.Constants.TEST_NAME;

public class CompletedActivity extends AppCompatActivity {

    private TextView testNameView;
    private TextView completedShuttlesView;
    private TextView completedLevelView;
    private TextView distanceCoveredView;
    private TextView totalTimeView;

    private ArrayList<Stage> testStages;
    private Integer shuttlesRemaining;
    private int currentStageIndex;
    private String testName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        testName = getIntent().getStringExtra(TEST_NAME);
        testNameView = findViewById(R.id.testName);
        testNameView.setText(testName);

        completedShuttlesView = findViewById(R.id.completedShuttles);
        completedLevelView = findViewById(R.id.completedLevel);
        distanceCoveredView = findViewById(R.id.distanceCovered);
        totalTimeView = findViewById(R.id.totalTime);

        testStages = Constants.testStagesMap.get(getIntent().getStringExtra(TEST_NAME));
        currentStageIndex = getIntent().getIntExtra(STAGE_INDEX, 0);
        shuttlesRemaining = getIntent().getIntExtra(SHUTTLES_REMAINING, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();

        completedShuttlesView.setText(String.valueOf(Utils.shuttlesCompleted(testStages, currentStageIndex, shuttlesRemaining)));
        completedLevelView.setText(
                testStages.get(currentStageIndex).getStageId()
                        + ":"
                        + (testStages.get(currentStageIndex).getNumShuttles() - shuttlesRemaining)
        );
        distanceCoveredView.setText(String.valueOf(Utils.distanceCoveredInM(testStages, currentStageIndex, shuttlesRemaining)));

    }
}
