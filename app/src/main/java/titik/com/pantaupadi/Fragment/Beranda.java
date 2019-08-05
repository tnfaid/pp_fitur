package titik.com.pantaupadi.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import titik.com.pantaupadi.Adapter.BerandaAdapter;
import titik.com.pantaupadi.Model.BerandaModel;
import titik.com.pantaupadi.R;
import titik.com.pantaupadi.Server.MySingleton;
import titik.com.pantaupadi.Server.Server;

public class Beranda extends Fragment {


    private static final String URL_DAUN = Server.URL + "ApiDaun.php";
    private static final String URL_CARI = Server.URL + "ApiCariDaun.php";
    private static final String URL_DAUN_DETAIL = Server.URL + "detail.php";

    private static final String TAG = Beranda.class.getSimpleName();
    public static final String TAG_ID = "id";
    public static final String TAG_FIRST_NAMA = "first_name";
    public static final String TAG_LAST_NAME = "last_name";
    public static final String TAG_RESULT = "result";
    public static final String TAG_MESSAGE = "message";
    public static final String TAG_VALUE = "value";

    String tag_json_obj = "json_obj_req";

    private BerandaAdapter myAdapter;
    ArrayList<BerandaModel> mItems = new ArrayList<>();
    RecyclerView mRecyclerView;
    private BerandaAdapter berandaAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    final Context context = this.getContext();
    ProgressDialog pDialog;
    final Beranda c = this;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {

        final View view = inflater.inflate(R.layout.list_beranda, container, false);

        getActivity().setTitle("Beranda Info");


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

                                BerandaModel item = new BerandaModel(jsonObject.getInt("id"), Server.URL_IMG + jsonObject.getString("gambar"),
                                        jsonObject.getString("nama_penyakit"), jsonObject.getInt("tanggal_upload"), jsonObject.getString("penulis"));

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setIconified(true);

        super.onCreateOptionsMenu(menu, inflater);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                cariData(s, context);
                return false;
            }


        });
    }

    private void cariData(final String keyword, final Context context ) {

        StringRequest SearchReq = new StringRequest(Request.Method.POST, URL_CARI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response){
                        String json = response.toString();
                        Log.e("Response: ", response.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            mItems.clear();
                            berandaAdapter.notifyDataSetChanged();

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObjectArray = jsonArray.getJSONObject(i);
                                mItems.add(new BerandaModel(jsonObject.getInt("id"), Server.URL_IMG + jsonObject.getString("gambar"),
                                        jsonObject.getString("nama_penyakit"), jsonObject.getInt("tanggal_upload"), jsonObject.getString("penulis")));
                                berandaAdapter.notifyDataSetChanged();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("keyword",keyword);
                return map;
            }
        };

        MySingleton.getInstance(context).addToRequestQueue(SearchReq);

    }



}
