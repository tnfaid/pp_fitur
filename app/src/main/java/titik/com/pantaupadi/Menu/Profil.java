package titik.com.pantaupadi.Menu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import titik.com.pantaupadi.R;

import static android.util.Log.e;

public class Profil extends Fragment {

    RelativeLayout view;
    public Profil() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = (RelativeLayout) inflater.inflate(R.layout.menu_profile, container, false);

        getActivity().setTitle("Profil");
        Log.e("Profil", "Profil");

        return view;
    }
}
