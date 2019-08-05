package titik.com.pantaupadi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import titik.com.pantaupadi.R;
import titik.com.pantaupadi.Server.MySingleton;
import titik.com.pantaupadi.Server.Server;

public class DetailPenyakitDaunActivity extends AppCompatActivity {
    public static final String URL = Server.URL;
    public static final String URL_IMAGE = Server.URL_IMG;
    public static final String URL_DETAIL = Server.URL + "ApiDetailPenyakitDaun.php";
    TextView  id, tv_penyakit, tv_kondisi, tv_solusi, tv_diunggah, tv_tanggal, tv_usia;
    ImageView img_detail, info_detail;

    String  idDaun, jenis_tanaman, kondisi, solusi, penulis, tanggal_upload, usia;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beranda_detail);

        idDaun = getIntent().getStringExtra("id");

        tv_penyakit = (TextView) findViewById(R.id.tv_penyakit);
        tv_kondisi = (TextView) findViewById(R.id.tv_kondisi);
        tv_solusi = (TextView) findViewById(R.id.tv_solusi);
        tv_tanggal = (TextView) findViewById(R.id.tv_tanggal);
        tv_diunggah = (TextView) findViewById(R.id.tv_diunggah);
        tv_usia = (TextView) findViewById(R.id.tv_usia);
        img_detail = (ImageView) findViewById(R.id.iv_gambar);
        info_detail = (ImageView) findViewById(R.id.iv_info_detail);
        info_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ScanHasilDetail_info.class );
                startActivity(intent);
            }
        });

        jenis_tanaman = getIntent().getStringExtra("jenis_tanaman");
        kondisi = getIntent().getStringExtra("kondisi");
        solusi = getIntent().getStringExtra("solusi");
        penulis = getIntent().getStringExtra("penulis");
        tanggal_upload = getIntent().getStringExtra("tanggal_upload");
        usia = getIntent().getStringExtra("usia");

//        Toast.makeText(getApplicationContext(), jenis_tanaman +
//                "\n" + kondisi +
//                "\n" + solusi +
//                "\n" + penulis +
//                "\n" + tanggal_upload, Toast.LENGTH_SHORT).show();
//
        tv_penyakit.setText(jenis_tanaman);
        tv_kondisi.setText(kondisi);
        tv_solusi.setText(solusi);
        tv_diunggah.setText(penulis);
        tv_tanggal.setText(tanggal_upload);
//        tv_usia.setText(usia);
        tv_usia.setText("Halooooo");
//        Toast.makeText(DetailPenyakitDaunActivity.this, jenis_tanaman ,Toast.LENGTH_SHORT).show();
        detailDaun();
    }

    public void detailDaun(){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DETAIL,
                new Response.Listener<String>() {
                    public void onResponse(String response){
                        String json = response;
                        Log.e("Response :",response);
                        try {

                            JSONArray array = new JSONArray(json);


                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);


                                tv_penyakit.setText(jsonObject.getString("nama_penyakit"));
                                tv_kondisi.setText(jsonObject.getString("kondisi"));
                                tv_solusi.setText(jsonObject.getString("solusi"));
                                tv_diunggah.setText(jsonObject.getString("penulis"));
                                tv_tanggal.setText(jsonObject.getString("tanggal_upload"));
                                tv_usia.setText(jsonObject.getString("usia" )+" HST");

                                showImage(Server.URL_IMG+jsonObject.getString("gambar"));

                                //Toast.makeText(getApplicationContext(), jsonObject.getString("title"),Toast.LENGTH_LONG).show();

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
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id", idDaun);
                return params;
            }
        };

        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
    public void showImage(String linkImage){
        ImageLoader imageLoader = MySingleton.getInstance(this.getApplicationContext()).getImageLoader();
        imageLoader.get(linkImage, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                img_detail.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }
}
