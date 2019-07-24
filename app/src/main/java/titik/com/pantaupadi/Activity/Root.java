package titik.com.pantaupadi.Activity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import titik.com.pantaupadi.R;


public class Root extends Fragment {
//sebagai tampilan awal ketika dijalankan
    public Root(){}
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.root, container, false);

        getActivity().setTitle("Pantau Padi");

        return rootView;
    }
}
