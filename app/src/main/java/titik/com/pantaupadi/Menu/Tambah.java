package titik.com.pantaupadi.Menu;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import titik.com.pantaupadi.LoginActivity;
import titik.com.pantaupadi.MainActivity;
import titik.com.pantaupadi.R;
import titik.com.pantaupadi.Server.MySingleton;
import titik.com.pantaupadi.Server.Server;

import static titik.com.pantaupadi.LoginActivity.TAG_FIRST_NAME;
import static titik.com.pantaupadi.LoginActivity.TAG_ID;
import static titik.com.pantaupadi.LoginActivity.TAG_LAST_NAME;
import static titik.com.pantaupadi.LoginActivity.TAG_STATUS;

public class Tambah extends Fragment {

    public static final String URL = Server.URL;
    private static final String urltambah = Server.URL + "ApiInputDaun.php";
    private static final String TAG = Tambah.class.getSimpleName();
    private static final String TAG_SUCCESS = "value";

    EditText nama_penyakit, kondisi, solusi;

    int success;
    private  static final String TAG_MESSAGE = "message";

    String user_id, first_name, last_name, penulis;
    SharedPreferences sharedPreferences;
    public static final String my_shared_preferences = "my_shared_preferences";
    private Button btn_simpan;
    final Tambah c = this;

//    ScrollView view;
//    public Tambah(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.menu_tambah_info, container, false);

        getActivity().setTitle("Tambah Info");
//        Log.e("Tambah Info", "Tambah Info");
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString(TAG_ID, null);
        first_name = sharedPreferences.getString(TAG_FIRST_NAME, null);
        last_name = sharedPreferences.getString(TAG_LAST_NAME, null);

        nama_penyakit = (EditText)view.findViewById(R.id.et_nama_penyakit);
        kondisi = (EditText)view.findViewById(R.id.et_input_kondisi_);
        solusi = (EditText)view.findViewById(R.id.et_input_solusi_);
        btn_simpan = (Button)view.findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                simpanData();
            }
        });

        return view;
    }

    public void simpanData()
    {
        StringRequest postRequest = new StringRequest(Request.Method.POST, urltambah,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            // Check for error node in json
                            if (success == 1) {

                                Log.e("Sukses Tambah Data!", jObj.toString());

                                Toast.makeText(getContext(),
                                        jObj.getString("message"), Toast.LENGTH_LONG).show();



                            } else {
                                Toast.makeText(getContext(), "error bro",
                                        Toast.LENGTH_LONG).show();

                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getContext(), "error bro",
                                Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("user_id", String.valueOf(user_id));
                params.put("penulis", String.valueOf(first_name+' '+last_name));
                params.put("jenis_tanaman", nama_penyakit.getText().toString());
                params.put("kondisi", kondisi.getText().toString());
                params.put("solusi", solusi.getText().toString());
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);
    }
}
