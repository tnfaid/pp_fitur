package titik.com.pantaupadi.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import titik.com.pantaupadi.Adapter.BerandaAdapter;
import titik.com.pantaupadi.Adapter.DetectionAdapter;
import titik.com.pantaupadi.Model.BerandaModel;
import titik.com.pantaupadi.R;
import titik.com.pantaupadi.Server.MySingleton;
import titik.com.pantaupadi.Server.Server;

public class ScanHasil extends AppCompatActivity {

    private static final String URL_DAUN = Server.URL + "ApiDaun.php";
    ArrayList<BerandaModel> mItems = new ArrayList<BerandaModel>();
    private DetectionAdapter myAdapter;
    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    int umur, hasilScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_hasil_list);

        myAdapter = new DetectionAdapter(getApplicationContext(), mItems);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view1);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        myAdapter.clear();
                        loadJson();
                    }
                }, 1000);
            }
        });
        loadJson();

        umur = Integer.parseInt(getIntent().getStringExtra("usia"));
        hasilScan = getIntent().getIntExtra("hasil",0);

    }

    private void loadJson() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DAUN,
                new Response.Listener<String>() {
                    public void onResponse(String response){
                        Log.d("json", response.toString());

                        try {
                            JSONArray jsonArray =  new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                BerandaModel item = new BerandaModel();
                                Toast.makeText(ScanHasil.this, "umur", Toast.LENGTH_SHORT).show();
                                item.setId(jsonObject.getString("id"));
                                item.setNama_penyakit(jsonObject.getString("nama_penyakit"));
                                item.setUsia(jsonObject.getString("usia"));
                                item.setValue_warna(jsonObject.getString("value_warna"));
                                item.setSolusi(jsonObject.getString("solusi"));
                                item.setGambar(jsonObject.getString("gambar"));
                                item.setKondisi(jsonObject.getString("kondisi"));
                                item.setPenulis(jsonObject.getString("penulis"));
                                item.setTanggal_upload(jsonObject.getString("tanggal_upload"));
                                mItems.add(item);

                                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                mRecyclerView.setHasFixedSize(true);
                                myAdapter = new DetectionAdapter(getApplicationContext(), mItems);
                                mRecyclerView.setAdapter(myAdapter);
                                myAdapter.notifyDataSetChanged();
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
