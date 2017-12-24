package com.am.yo_yo.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.am.yo_yo.R;
import com.am.yo_yo.test.YoYoTest;

public class CompletedActivity extends AppCompatActivity {

    private TextView testNameView;
    private TextView completedShuttlesView;
    private TextView completedLevelView;
    private TextView distanceCoveredView;
    private TextView totalTimeView;
    private TextView testNormLinkView;

    private YoYoTest yoYoTest;
    private Integer shuttlesRemaining;
    private int currentStageIndex;
    private String testName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        testName = getIntent().getStringExtra(Constants.TEST_NAME);
        testNameView = findViewById(R.id.testName);
        testNameView.setText(testName);

        completedShuttlesView = findViewById(R.id.completedShuttles);
        completedLevelView = findViewById(R.id.completedLevel);
        distanceCoveredView = findViewById(R.id.distanceCovered);
        totalTimeView = findViewById(R.id.totalTime);

        testNormLinkView = findViewById(R.id.testNormLink);

        yoYoTest = HomeActivity.TEST_MAP.get(getIntent().getStringExtra(Constants.TEST_NAME));
        currentStageIndex = getIntent().getIntExtra(Constants.STAGE_INDEX, 0);
        shuttlesRemaining = getIntent().getIntExtra(Constants.SHUTTLES_REMAINING, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();

        completedShuttlesView.setText(String.valueOf(yoYoTest.shuttlesCompleted(currentStageIndex, shuttlesRemaining)));
        completedLevelView.setText(
                yoYoTest.testStages().get(currentStageIndex).getSpeedLevel()
                        + ":"
                        + (yoYoTest.testStages().get(currentStageIndex).getNumShuttles() - shuttlesRemaining)
        );
        distanceCoveredView.setText(String.valueOf(yoYoTest.distanceCoveredInM(currentStageIndex, shuttlesRemaining)));

        testNormLinkView.setText(Html.fromHtml("<a href='" + yoYoTest.testNormUri().toString() + "'>View Test Norms</a>", Html.FROM_HTML_MODE_LEGACY));
        testNormLinkView.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
