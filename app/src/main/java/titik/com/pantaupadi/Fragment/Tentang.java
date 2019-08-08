package titik.com.pantaupadi.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import titik.com.pantaupadi.Activity.Blob.MainBlobDetector;
import titik.com.pantaupadi.Activity.CaraPenggunaanAplikasi_info;
import titik.com.pantaupadi.Activity.ScanHasil;
import titik.com.pantaupadi.Activity.Tentang_CobaDeteksi;
import titik.com.pantaupadi.R;

public class Tentang extends Fragment {

    Button btn_cara_penggunaan, btn_coba_deteksi, btn_coba_recycler_view, btn_blob_tracking;
    RelativeLayout view;
    public Tentang(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = (RelativeLayout) inflater.inflate(R.layout.menu_tentang, container, false);

        btn_cara_penggunaan = (Button) view.findViewById(R.id.cara_penggunaan);
        btn_cara_penggunaan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CaraPenggunaanAplikasi_info.class);
                startActivity(i);
            }
        });
//        btn_coba_deteksi = (Button) view.findViewById(R.id.coba_deteksi);
//        btn_coba_recycler_view = (Button) view.findViewById(R.id.coba_RecyclerView);
        btn_blob_tracking = (Button) view.findViewById(R.id.blob_tracking);
//        btn_coba_recycler_view.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ScanHasil.class);
//                startActivity(intent);
//            }
//        });
        btn_blob_tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext())
                Toast.makeText(getContext(), "Pindah ke sono", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainBlobDetector.class);
                startActivity(intent);
            }
        });
//        btn_coba_deteksi.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), Tentang_CobaDeteksi.class);
//                startActivity(intent);
//            }
//        });

        getActivity().setTitle("Tentang");
        Log.e("Tentang", "Tentang");
        return view;
    }
}
