package com.am.yo_yo.app;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completed);

        yoYoTest = (YoYoTest) getIntent().getSerializableExtra(Constants.TEST_NAME);
        currentStageIndex = getIntent().getIntExtra(Constants.STAGE_INDEX, 0);
        shuttlesRemaining = getIntent().getIntExtra(Constants.SHUTTLES_REMAINING, 0);

        testNameView = findViewById(R.id.testName);
        testNameView.setText(yoYoTest.testName());

        completedShuttlesView = findViewById(R.id.completedShuttles);
        completedLevelView = findViewById(R.id.completedLevel);
        distanceCoveredView = findViewById(R.id.distanceCovered);
        totalTimeView = findViewById(R.id.totalTime);

        testNormLinkView = findViewById(R.id.testNormLink);

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


        totalTimeView.setText(String.valueOf(yoYoTest.timeTakenInSecs(currentStageIndex, shuttlesRemaining, Math.round(yoYoTest.restIntervalInMills() / Constants.MILLIS_IN_ONE_SEC))));
        String href = "<a href='" + yoYoTest.testNormUri().toString() + "'>View Test Norms</a>";
        Spanned linkText;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            linkText = Html.fromHtml(href, Html.FROM_HTML_MODE_LEGACY);
        else
            linkText = Html.fromHtml(href);

        testNormLinkView.setText(linkText);
        testNormLinkView.setMovementMethod(LinkMovementMethod.getInstance());


    }
}
