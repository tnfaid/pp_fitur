package titik.com.pantaupadi.Menu;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import titik.com.pantaupadi.LoginActivity;
import titik.com.pantaupadi.R;

import static android.util.Log.e;
import static titik.com.pantaupadi.LoginActivity.TAG_EMAIL;
import static titik.com.pantaupadi.LoginActivity.TAG_ID;

public class Profil extends Fragment {

    RelativeLayout view;
    TextView txt_username;
    TextView txt_email;
    SharedPreferences sharedPreferences;
    String id, username, email;
    public Profil() {}
    private ImageButton btn_logout;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id =sharedPreferences.getString(TAG_ID,null);
        email =sharedPreferences.getString(TAG_EMAIL,null);

//        btn_logout = (ImageButton) view.findViewById(R.id.btn_logout);
//        btn_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean(LoginActivity.session_status, false);
//                editor.putString(TAG_ID, null);
//                editor.putString(TAG_EMAIL, null);
//                editor.commit();
//
//                Intent intent = new Intent(getContext(), LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//
//            }
//        });

        view = (RelativeLayout) inflater.inflate(R.layout.menu_profile, container, false);

        getActivity().setTitle("Profil");
        Log.e("Profil", "Profil");

        return view;
    }
}
