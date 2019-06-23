package titik.com.pantaupadi.Menu;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import titik.com.pantaupadi.Adapter.BerandaAdapter;
import titik.com.pantaupadi.Data.DataPenyakit;
import titik.com.pantaupadi.Model.BerandaModel;
import titik.com.pantaupadi.R;

public class Beranda extends Fragment {
    DataPenyakit dataPenyakit;
    String[] arrNama, arrDeskripsi;

//
    BerandaAdapter myAdapter;
    ArrayList<BerandaModel> mItems = new ArrayList<>();
    RecyclerView mRecyclerView;

    public Beranda(){}
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.list_beranda, container, false);
        myAdapter = new BerandaAdapter(view.getContext(),mItems);
        mRecyclerView = view.findViewById(R.id.recycler_view1);

        dataPenyakit = new DataPenyakit();
        arrNama = dataPenyakit.getNama();
        arrDeskripsi = dataPenyakit.getDeskripsi();
        LoadData();
//        namaPenyakit.setText(arrNama[1]);
//        tanggal.setText(arrDeskripsi[1]);
        getActivity().setTitle("Beranda Info");
        Log.e("Beranda", "Beranda");
        return view;
    }

    private void LoadData() {
        for (int i = 0; i < arrNama.length; i++) {
                BerandaModel item = new BerandaModel();

                item.setNama(arrNama[i]);
                //item.setGambar(jsonObject.getString("username"));
                //item.setTanggal(jsonObject.getString("address"));
                item.setAuthor(arrDeskripsi[i]);
                mItems.add(item);
            }

            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setHasFixedSize(true);
            myAdapter = new BerandaAdapter(getActivity(), mItems);
            mRecyclerView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();


        }

}
