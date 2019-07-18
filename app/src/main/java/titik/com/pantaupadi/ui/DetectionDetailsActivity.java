package titik.com.pantaupadi.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import titik.com.pantaupadi.DetectionResultHolder;
import titik.com.pantaupadi.R;


/**
 * This activity displays the Detection Step Details of the last detection process.
 * The data to display is from the DetectionResultHolder static class.
 * <p>
 * Created by stefan on 28.05.2017.
 */
public class DetectionDetailsActivity extends AppCompatActivity {

    /**
     * Sets up the view and initializes the view elements (controls).
     *
     * @param savedInstanceState see android documentation
     */

    int hasilScan, valueDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_daun_detail);

        if (DetectionResultHolder.detectionResultAvailable()) {
            ListView detectionDetailsList = (ListView) findViewById(R.id.detection_deteils_activity_list);

            DetectionDetailsListAdapter adapter = new DetectionDetailsListAdapter(this, DetectionResultHolder.getDetectionResult().getDetectionStepDetails());

            detectionDetailsList.setAdapter(adapter);
        }

        hasilScan = getIntent().getIntExtra("hasil",0);
        Toast.makeText(getApplicationContext(), "hasil "+ hasilScan, Toast.LENGTH_SHORT).show();
    }
}
