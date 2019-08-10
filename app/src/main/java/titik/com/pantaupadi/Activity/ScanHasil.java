package titik.com.pantaupadi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    TextView text_umur, text_modus, text_take ;
    int usiaNya, hasilScan, umurFix, valueFix, jumlahAmbilGambar, umurDb, warnaDb, intModus, intUmur, intUmur2;
    String str_text_umur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_hasil_list);

        myAdapter = new DetectionAdapter(getApplicationContext(), mItems);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view1);
        text_modus = (TextView) findViewById(R.id.tv_modus);
        text_umur = (TextView) findViewById(R.id.tv_umur);
        text_take = (TextView) findViewById(R.id.tv_take);

        Intent intent = getIntent();

        jumlahAmbilGambar = intent.getIntExtra("intMax_ambil_gambar", jumlahAmbilGambar);
        intModus = intent.getIntExtra("intIntentHasil", 0);
        intUmur = intent.getIntExtra("intIntentUsia", 0);

        intent.putExtra("take" , jumlahAmbilGambar);
        intent.putExtra("modus" , intModus);

        /**
         *
         * get umur bermasalah coy, bisa sih kalo get pake cara ini
         *  if (intent.hasExtra("intIntentUsia")){
         *             text_umur.setText("nilai usia dari pop up = " + intent.getStringExtra("intIntentUsia"));
         *
         *  }
         */

        if (intent.hasExtra("intIntentUsia")){
            text_umur.setText(" " + intent.getIntExtra("intIntentUsia",0));
//            str_text_umur = text_umur.getText().toString();
//            intUmur2 = Integer.parseInt(str_text_umur);

        }
        text_modus.setText(" " + intModus  );
        text_take.setText(" " + jumlahAmbilGambar);
//        Toast.makeText(this, "modus" , Toast.LENGTH_SHORT).show();
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

//        if (intUmur >= 25 && intModus == 10){
//            umurFix = intUmur;
//            valueFix = intModus;
//        }
//        else if(intUmur >= 0 && intModus == 4 ) {
//            umurFix = intUmur;
//            valueFix = intModus;
//        }
//        else {
//            umurFix = intUmur;
//            valueFix = intModus;
//            Toast.makeText(this, "DAUN APA INI ?", Toast.LENGTH_SHORT).show();
//        }
    }
//
//    private Intent intent() {
//     return intent();
//    }

    private void loadJson() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DAUN,
                new Response.Listener<String>() {
                    public void onResponse(String response){
                        Log.d("json", response.toString());

                        //try catch biar g force close kalo error
                        try {
                            JSONArray jsonArray =  new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                BerandaModel item = new BerandaModel();

                                if (jsonObject.getInt("value_warna") == getIntent().getIntExtra("intIntentHasil",0) &&
                                        jsonObject.getInt("usia")<=getIntent().getIntExtra("intIntentUsia",0)) {
                                    item.setId(jsonObject.getString("id"));
                                    item.setNama_penyakit(jsonObject.getString("nama_penyakit"));
                                    item.setUsia(String.valueOf(jsonObject.getInt("usia")));
                                    item.setValue_warna(""+jsonObject.getInt("value_warna"));
                                    item.setSolusi(jsonObject.getString("solusi"));
                                    item.setGambar(jsonObject.getString("gambar"));
                                    item.setKondisi(jsonObject.getString("kondisi"));
                                    item.setPenulis(jsonObject.getString("penulis"));
                                    item.setTanggal_upload(jsonObject.getString("tanggal_upload"));
////                                        item.setValue_warna(jsonObject.getString("value_warna"));
                                    mItems.add(item);
//                                    } else{
//                                        Toast.makeText(ScanHasil.this, "Data tidak terdaftar", Toast.LENGTH_SHORT).show();
//                                    }
                                }else{
                                    Toast.makeText(ScanHasil.this, "Warna tidak dikenali", Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(ScanHasil.this, "ini text_modus = " + getIntent().getIntExtra("intIntentHasil",0) + ", yang ini text_umur = " + getIntent().getIntExtra("intIntentUsia",0), Toast.LENGTH_LONG ).show();
                                }
                            }
                            Log.e(getClass().getSimpleName(), "total items :"+mItems.size() );
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            mRecyclerView.setHasFixedSize(true);
                            myAdapter = new DetectionAdapter(getApplicationContext(), mItems);
                            mRecyclerView.setAdapter(myAdapter);
                            myAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ScanHasil.this, "something error", Toast.LENGTH_SHORT).show();
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