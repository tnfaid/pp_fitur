package titik.com.pantaupadi.Menu;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import titik.com.pantaupadi.Activity.MainBlobDetector;
import titik.com.pantaupadi.R;
import titik.com.pantaupadi.ui.DetectionDetailsActivity;

public class Tentang extends Fragment {

    Button btn_cara_penggunaan;
    RelativeLayout view;
    public Tentang(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = (RelativeLayout) inflater.inflate(R.layout.menu_tentang, container, false);

        btn_cara_penggunaan = (Button) view.findViewById(R.id.cara_penggunaan);
        btn_cara_penggunaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getContext())
                Toast.makeText(getContext(), "Pindah ke sono", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainBlobDetector.class);
                startActivity(intent);
            }
        });

        getActivity().setTitle("Tentang");
        Log.e("Tentang", "Tentang");
        return view;
    }
}
