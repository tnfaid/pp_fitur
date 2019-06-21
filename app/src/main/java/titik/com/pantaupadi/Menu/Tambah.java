package titik.com.pantaupadi.Menu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import titik.com.pantaupadi.R;

public class Tambah extends Fragment {

    RelativeLayout view;
    public Tambah(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = (RelativeLayout) inflater.inflate(R.layout.menu_tambah_info, container, false);

        getActivity().setTitle("Tambah Info");
        Log.e("Tambah Info", "Tambah Info");

        return view;
    }
}
