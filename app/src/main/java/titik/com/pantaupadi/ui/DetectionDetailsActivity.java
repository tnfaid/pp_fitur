package titik.com.pantaupadi.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import titik.com.pantaupadi.Adapter.BerandaAdapter;
import titik.com.pantaupadi.DetectionResultHolder;
import titik.com.pantaupadi.Model.BerandaModel;
import titik.com.pantaupadi.R;
import titik.com.pantaupadi.Server.MySingleton;
import titik.com.pantaupadi.Server.Server;


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

    int hasilScan, umur, valueDb, tempUmur, counter = -1;

    int umurDb[] = {10, 30, 20, 40, 50};
    private List<BerandaModel> listPenyakit;
    ArrayList<String> namaPenyakit = new ArrayList<String>();
    ArrayList<String> solusi = new ArrayList<String>();
    ArrayList<String> kondisi = new ArrayList<String>();
    ArrayList<String> usia = new ArrayList<String>();
    ArrayList<String> author = new ArrayList<String>();
    ArrayList<String> valueWarna = new ArrayList<String>();
    private static final String URL_DAUN = Server.URL + "ApiDaun.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view = setContentView(R.layout.scan_daun_detail);

        if (DetectionResultHolder.detectionResultAvailable()) {
            ListView detectionDetailsList = (ListView) findViewById(R.id.detection_deteils_activity_list);

            DetectionDetailsListAdapter adapter = new DetectionDetailsListAdapter(this, DetectionResultHolder.getDetectionResult().getDetectionStepDetails());

            detectionDetailsList.setAdapter(adapter);
        }

        hasilScan = getIntent().getIntExtra("hasil",0);
//        umur = 21;
        umur = Integer.parseInt(getIntent().getStringExtra("usia"));
        tempUmur = umur;

        //get dr db
//        valueDb = 5;
        for (int i = 0; i<namaPenyakit.size(); i++){
//            valueDb = Integer.parseInt(listPenyakit.get(i).getValue_warna());
            if(hasilScan == Integer.parseInt(valueWarna.get(i))){
                for(int j = 0; j<usia.size(); j++){
                    if(tempUmur<=Integer.parseInt(usia.get(j))){
                        tempUmur = Integer.parseInt(usia.get(j));
                        counter = j;
                    }
                }
            }
        }


        if(counter != -1){
            Toast.makeText(getApplicationContext(), "Nama Penyakit :" + listPenyakit.get(counter).getNama_penyakit(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Gambar :" + listPenyakit.get(counter).getGambar(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Solusi :" + listPenyakit.get(counter).getSolusi(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "Kondisi :" + listPenyakit.get(counter).getKondisi(), Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(getApplicationContext(), "hasil "+ hasilScan, Toast.LENGTH_SHORT).show();

        loadJson(view);

    }
//
    private void loadJson(final View view ) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DAUN,
                new Response.Listener<String>() {
                    public void onResponse(String response){
                        Log.d("json", response.toString());

                        try {
                            JSONArray jsonArray =  new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                namaPenyakit.add(jsonObject.getString("nama_penyakit"));
                                solusi.add(jsonObject.getString("solusi"));
                                kondisi.add(jsonObject.getString("kondisi"));
                                usia.add(jsonObject.getString("usia"));
                                author.add(jsonObject.getString("penulis"));
                                valueWarna.add(jsonObject.getString("value_warna"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        MySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
