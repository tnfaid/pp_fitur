package titik.com.pantaupadi.Menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;

import titik.com.pantaupadi.Data.DataPenyakit;
import titik.com.pantaupadi.R;

public class Scan extends Fragment {

    DataPenyakit dPenyakit;
    String[] namaP;
    String[] namaD;
    Button btn_tambah_area, btn_monitor_area;

    private static final String TAG = Scan.class.getSimpleName();
    private static final String TAG_SUCCESS = "value";

    TextView area, tanggal;

    int success;
    private static final String TAG_MESSAGE = "message";
    final Scan c = this;

    CarouselView carouselView;

    public Scan() {}

    RelativeLayout view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = (RelativeLayout)inflater.inflate(R.layout.menu_scan_daun, container, false);

        dPenyakit = new DataPenyakit();
        namaP = dPenyakit.getNama();
        namaD = dPenyakit.getDeskripsi();

        btn_tambah_area = (Button)view.findViewById(R.id.btn_tambah_area);
        btn_tambah_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), namaP[0] + namaD[0],
                        Toast.LENGTH_LONG).show();
                LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
                View mView = layoutInflaterAndroid.inflate(R.layout.scan_tambahkan_area,null);
                AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
                alertDialogBuilderUserInput.setView(mView);

                area = (TextView) mView.findViewById(R.id.txt_nama_area);
                alertDialogBuilderUserInput
                        .setCancelable(false)
                        .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //tulis disni
                    }
                })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                alertDialogAndroid.show();

            }
        });

        return view;
    }





}
