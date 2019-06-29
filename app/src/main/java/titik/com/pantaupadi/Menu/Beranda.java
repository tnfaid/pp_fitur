package titik.com.pantaupadi.Menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
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
import java.util.zip.Inflater;

import titik.com.pantaupadi.Adapter.BerandaAdapter;
import titik.com.pantaupadi.Data.DataPenyakit;
import titik.com.pantaupadi.LoginActivity;
import titik.com.pantaupadi.Model.BerandaModel;
import titik.com.pantaupadi.R;
import titik.com.pantaupadi.Server.MySingleton;
import titik.com.pantaupadi.Server.Server;

import static titik.com.pantaupadi.LoginActivity.TAG_FIRST_NAME;
import static titik.com.pantaupadi.LoginActivity.TAG_LAST_NAME;

public class Beranda extends Fragment {


    private static final String URL_DAUN = Server.URL + "ApiDaun.php";
    private static final String URL_DAUN_DETAIL = Server.URL + "detail.php";
    private BerandaAdapter myAdapter;
    ArrayList<BerandaModel> mItems = new ArrayList<>();
    RecyclerView mRecyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    final Beranda c = this;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        final View view = inflater.inflate(R.layout.list_beranda, container, false);


        myAdapter = new BerandaAdapter(view.getContext(),mItems);
        mRecyclerView = view.findViewById(R.id.recycler_view1);

        swipeRefreshLayout = view.findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        myAdapter.clear();
                        loadJson(view);
                    }
                }, 1000);
            }
        });

        loadJson(view);
        return view;
    }

    private void loadJson(final View view ) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_DAUN,
                    new Response.Listener<String>() {
                    public void onResponse(String response){
                        Log.d("json", response.toString());

                        try {
                            JSONArray jsonArray =  new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                BerandaModel item = new BerandaModel();

                                item.setId(jsonObject.getString("id"));
                                item.setJenis_tanaman(jsonObject.getString("jenis_tanaman"));
                                item.setWarna_daun(jsonObject.getString("warna_daun"));
                                item.setBwd_range(jsonObject.getString("bwd_range"));
                                item.setSolusi(jsonObject.getString("solusi"));
//                                item.setPic_compare(Server.URL_IMG+jsonObject.getString("pic_compare"));
                                item.setKondisi(jsonObject.getString("kondisi"));
                                item.setPenulis(jsonObject.getString("penulis"));
                                item.setTanggal_upload(jsonObject.getString("tanggal_upload"));
                                mItems.add(item);

                                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                mRecyclerView.setHasFixedSize(true);
                                myAdapter = new BerandaAdapter(getContext(), mItems);
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
        MySingleton.getInstance(this.getContext()).addToRequestQueue(stringRequest);
    }


//    DataPenyakit dataPenyakit;
//    String[] arrNama, arrDeskripsi, arrTanggal;
//    ArrayList<BerandaModel> mItems = new ArrayList<>();
//
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        final View view = inflater.inflate(R.layout.list_beranda, container, false);
////        myAdapter = new BerandaAdapter(view.getContext(),mItems);
//        mRecyclerView = view.findViewById(R.id.recycler_view1);
//
////        dataPenyakit = new DataPenyakit();
////        arrNama = dataPenyakit.getNama();
////        arrDeskripsi = dataPenyakit.getDeskripsi();
////        arrTanggal = dataPenyakit.getTanggal();
//        LoadData();
////        namaPenyakit.setText(arrNama[1]);
////        tanggal.setText(arrDeskripsi[1]);
//        getActivity().setTitle("Beranda Info");
//        Log.e("Beranda", "Beranda");
//        return view;
//    }
//
//    private void LoadData() {
//        for (int i = 0; i < arrNama.length; i++) {
//                BerandaModel item = new BerandaModel();
//
//                item.setNama(arrNama[i]);
//                //item.setGambar(jsonObject.getString("username"));
//                //item.setTanggal(jsonObject.getString("address"));
//                item.setAuthor(arrDeskripsi[i]);
//                item.setTanggal(arrTanggal[i]);
//                mItems.add(item);
//            }
//
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            mRecyclerView.setHasFixedSize(true);
//            myAdapter = new BerandaAdapter(getActivity(), mItems);
//            mRecyclerView.setAdapter(myAdapter);
//            myAdapter.notifyDataSetChanged();
//
//
//        }

}
