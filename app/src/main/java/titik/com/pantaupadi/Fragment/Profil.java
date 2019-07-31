package titik.com.pantaupadi.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import titik.com.pantaupadi.Activity.LoginActivity;
import titik.com.pantaupadi.R;

import static android.util.Log.e;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_COUNTRY;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_CREATED_AT;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_EMAIL;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_FIRST_NAME;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_ID;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_LAST_NAME;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_MOBILE;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_PASSWORD;
import static titik.com.pantaupadi.Activity.LoginActivity.session_status;

public class Profil extends Fragment {

    RelativeLayout view;
    TextView txt_id, txt_mobile, txt_first_name, txt_last_name, txt_country, txt_created_at, txt_email, txt_nama;
    SharedPreferences sharedPreferences;
    String id, first_name, last_name, created_at, email, country, mobile, nama, password;
    Boolean session = false;
    public Profil() {}
    Button btn_logout, btn_gambar;

    public static final String my_share_preferences = "my_shared_preferences";
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.menu_profile, container, false);

        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        id =sharedPreferences.getString(TAG_ID,null);
        email =sharedPreferences.getString(TAG_EMAIL,null);
        first_name =sharedPreferences.getString(TAG_FIRST_NAME,null);
        last_name =sharedPreferences.getString(TAG_LAST_NAME,null);
        nama =sharedPreferences.getString(TAG_FIRST_NAME + TAG_LAST_NAME,null);
        created_at =sharedPreferences.getString(TAG_CREATED_AT,null);
        country =sharedPreferences.getString(TAG_COUNTRY,null);
        mobile =sharedPreferences.getString(TAG_MOBILE,null);
        password =sharedPreferences.getString(TAG_PASSWORD,null);
        session = sharedPreferences.getBoolean(session_status, false);

        txt_email = (TextView) view.findViewById(R.id.tv_email);
        txt_nama = (TextView) view.findViewById(R.id.tv_nama);
        txt_created_at = (TextView) view.findViewById(R.id.tv_bergabung);
        txt_country = (TextView) view.findViewById(R.id.tv_lokasi);
        txt_mobile = (TextView) view.findViewById(R.id.tv_password);


        txt_email.setText(email);
        txt_nama.setText(first_name+ " " +last_name);
        txt_created_at.setText(created_at);
        txt_mobile.setText(mobile);

        btn_gambar = (Button) view.findViewById(R.id.btn_camera);
        btn_gambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!session){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    Toast.makeText(getContext(), "dah session detect", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_logout = (Button) view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(session_status, false);
                editor.putString(TAG_ID, null);
                editor.putString(TAG_FIRST_NAME, null);
                editor.putString(TAG_LAST_NAME, null);
                editor.putString(TAG_MOBILE, null);
                editor.putString(TAG_CREATED_AT, null);
                editor.putString(TAG_EMAIL, null);
                editor.commit();

                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                Toast.makeText(getContext(),"Berhasil Keluar", Toast.LENGTH_SHORT).show();
            }
        });

        getActivity().setTitle("Profil");
        Log.e("Profil", "Profil");

        return view;
    }
}
