package titik.com.pantaupadi.Menu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import titik.com.pantaupadi.R;

public class Tentang extends Fragment {

    RelativeLayout view;
    public Tentang(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = (RelativeLayout) inflater.inflate(R.layout.menu_tentang, container, false);

        getActivity().setTitle("Tentang");
        Log.e("Tentang", "Tentang");
        return view;
    }
}
