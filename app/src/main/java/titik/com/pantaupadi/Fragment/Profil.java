package titik.com.pantaupadi.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import titik.com.pantaupadi.Activity.LoginActivity;
import titik.com.pantaupadi.R;

import static android.util.Log.e;

public class Profil extends Fragment {

    RelativeLayout view;
    TextView txt_id, txt_mobile, txt_first_name, txt_last_name, txt_country, txt_created_at, txt_email, txt_nama;
    SharedPreferences sharedPreferences;
    String id, first_name, last_name, created_at, email, country, mobile, nama;
    public Profil() {}
    private ImageButton btn_logout;

    public static final String my_share_preferences = "my_shared_preferences";
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
//        id =sharedPreferences.getString(TAG_ID,null);
//        email =sharedPreferences.getString(TAG_EMAIL,null);
//        first_name =sharedPreferences.getString(TAG_FIRST_NAME,null);
//        last_name =sharedPreferences.getString(TAG_LAST_NAME,null);
//        nama =sharedPreferences.getString(TAG_FIRST_NAME + TAG_LAST_NAME,null);
//        created_at =sharedPreferences.getString(TAG_CREATED_AT,null);
//        country =sharedPreferences.getString(TAG_COUNTRY,null);
//        mobile =sharedPreferences.getString(TAG_MOBILE,null);
//
//        txt_email = (TextView) view.findViewById(R.id.tv_email);
//        txt_nama = (TextView) view.findViewById(R.id.tv_nama);
//        txt_created_at = (TextView) view.findViewById(R.id.tv_bergabung);
//        txt_country = (TextView) view.findViewById(R.id.tv_lokasi);
//        txt_mobile = (TextView) view.findViewById(R.id.tv_password);
//
//        txt_email.setText(id);
//        txt_email.setText(email);
//        txt_nama.setText(nama);
//        txt_created_at.setText(created_at);
//        txt_mobile.setText(mobile);

//        btn_logout = (ImageButton) view.findViewById(R.id.btn_logout);
//
//        btn_logout.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean(LoginActivity.session_status, false);
//                editor.putString(TAG_ID, null);
//                editor.putString(TAG_FIRST_NAME, null);
//                editor.putString(TAG_LAST_NAME, null);
//                editor.putString(TAG_MOBILE, null);
//                editor.putString(TAG_CREATED_AT, null);
//                editor.putString(TAG_EMAIL, null);
//                editor.commit();
//
//                Intent intent = new Intent(getContext(), LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//            }
//        });
        view = (RelativeLayout) inflater.inflate(R.layout.menu_profile, container, false);

        getActivity().setTitle("Profil");
        Log.e("Profil", "Profil");

        return view;
    }
}