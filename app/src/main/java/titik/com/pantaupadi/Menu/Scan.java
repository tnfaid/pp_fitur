package titik.com.pantaupadi.Menu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.w3c.dom.Text;

import titik.com.pantaupadi.R;

public class Scan extends Fragment {

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
        view = (RelativeLayout)inflater.inflate(R.layout.menu_profile, container, false);

        btn_tambah_area = (Button)view.findViewById(R.id.btn_tambah_area);
        btn_tambah_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
