package titik.com.pantaupadi.Menu;

import android.os.Bundle;
import android.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import titik.com.pantaupadi.R;

public class Beranda extends Fragment {
    RelativeLayout view;
    public Beranda(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = (RelativeLayout) inflater.inflate(R.layout.menu_beranda_info, container, false);

        getActivity().setTitle("Beranda Info");
        Log.e("Beranda", "Beranda");
        return view;
    }
}
