package com.am.yo_yo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static com.am.yo_yo.Constants.SHUTTLES_REMAINING;
import static com.am.yo_yo.Constants.STAGES;
import static com.am.yo_yo.Constants.STAGE_INDEX;

public class CompletedActivity extends AppCompatActivity {

    private TextView completedShuttlesView;
    private TextView completedLevelView;
    private TextView distanceCoveredView;
    private TextView totalTimeView;

    private Stage currentStage;
    private Integer shuttlesRemaining;
    private int currentStageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        completedShuttlesView = findViewById(R.id.completedShuttles);
        completedLevelView = findViewById(R.id.completedLevel);
        distanceCoveredView = findViewById(R.id.distanceCovered);
        totalTimeView = findViewById(R.id.totalTime);

        currentStageIndex = getIntent().getIntExtra(STAGE_INDEX, 0);
        currentStage = STAGES.get(currentStageIndex);
        shuttlesRemaining = getIntent().getIntExtra(SHUTTLES_REMAINING, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();

        completedShuttlesView.setText(String.valueOf(Utils.shuttlesCompleted(currentStageIndex, shuttlesRemaining)));
        completedLevelView.setText(
                STAGES.get(currentStageIndex).getStageId()
                        + ":"
                        + (STAGES.get(currentStageIndex).getNumShuttles() - shuttlesRemaining)
        );
        distanceCoveredView.setText(String.valueOf(Utils.distanceCoveredInM(currentStageIndex, shuttlesRemaining)));

    }
}
