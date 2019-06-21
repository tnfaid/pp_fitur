package titik.com.pantaupadi.Menu;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import titik.com.pantaupadi.R;

public class Scan extends Fragment {

    public Scan() {}
    RelativeLayout view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = (RelativeLayout) inflater.inflate(R.layout.menu_scan_daun, container, false);

        getActivity().setTitle("Scan");
        Log.e("Scan", "Scan");
        return view;
    }

}
