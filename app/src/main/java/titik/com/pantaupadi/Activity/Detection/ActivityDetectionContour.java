package titik.com.pantaupadi.Activity.Detection;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import titik.com.pantaupadi.Activity.DetectionResultHolder;
import titik.com.pantaupadi.Adapter.DetectionDetailsListAdapter;
import titik.com.pantaupadi.R;

public class ActivityDetectionContour extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detection_contours_list);
        if (DetectionResultHolder.detectionResultAvailable()) {
            ListView detectionDetailsList = (ListView) findViewById(R.id.detection_deteils_activity_list);

            DetectionDetailsListAdapter adapter = new DetectionDetailsListAdapter(this, DetectionResultHolder.getDetectionResult().getDetectionStepDetails());

            detectionDetailsList.setAdapter(adapter);
        }
    }
}
