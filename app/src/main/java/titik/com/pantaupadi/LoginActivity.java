package titik.com.pantaupadi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import maes.tech.intentanim.CustomIntent;
import titik.com.pantaupadi.Server.MySingleton;
import titik.com.pantaupadi.Server.Server;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    EditText txt_email, txt_password;
    TextView txt_register;
    Intent intent;

    int success;
    ConnectivityManager conMgr;

    private static final String URL_LOGIN = Server.URL + "ApiLogin.php";
    private static final String TAG = LoginActivity.class.getSimpleName();

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    public final static String TAG_ID = "id";
    public final static String TAG_USER_ID = "user_id";
    public final static String TAG_FIRST_NAME = "first_name";
    public final static String TAG_LAST_NAME = "last_name";
    public final static String TAG_EMAIL = "email";
    public final static String TAG_MOBILE = "mobile";
    public final static String TAG_COUNTRY = "country";
    public final static String TAG_CREATED_AT = "created_at";
    public final static String TAG_STATUS = "status";
    public final static String TAG_ROLE = "role";

    String tag_json_obj = "json_obj_req";

    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, user_id, first_name, last_name, email, mobile, country, created_at, status, role;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";

    Button btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        {
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
            } else {
                Toast.makeText(getApplicationContext(), "No Internet Connection",
                        Toast.LENGTH_LONG).show();
            }
        }

        txt_email = (EditText) findViewById(R.id.txt_email);
        txt_password = (EditText) findViewById(R.id.txt_password);

        // Cek session login jika TRUE maka langsung buka MainActivity
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        user_id = sharedpreferences.getString(TAG_USER_ID, null);
        first_name = sharedpreferences.getString(TAG_FIRST_NAME, null);
        last_name = sharedpreferences.getString(TAG_LAST_NAME, null);
        email = sharedpreferences.getString(TAG_EMAIL, null);
        mobile = sharedpreferences.getString(TAG_MOBILE, null);
        country = sharedpreferences.getString(TAG_COUNTRY, null);
        created_at = sharedpreferences.getString(TAG_CREATED_AT, null);
        status = sharedpreferences.getString(TAG_STATUS, null);
        role = sharedpreferences.getString(TAG_ROLE, null);

        if (session) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_USER_ID, user_id);
            intent.putExtra(TAG_FIRST_NAME, first_name);
            intent.putExtra(TAG_LAST_NAME, last_name);
            intent.putExtra(TAG_EMAIL, email);
            intent.putExtra(TAG_MOBILE, mobile);
            intent.putExtra(TAG_COUNTRY, country);
            intent.putExtra(TAG_CREATED_AT, created_at);
            intent.putExtra(TAG_STATUS, status);
            intent.putExtra(TAG_ROLE, role);

            finish();
            startActivity(intent);
        }

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO Auto-generated method stub
                String email = txt_email.getText().toString();
                String password = txt_password.getText().toString();

                // mengecek kolom yang kosong
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    if (conMgr.getActiveNetworkInfo() != null
                            && conMgr.getActiveNetworkInfo().isAvailable()
                            && conMgr.getActiveNetworkInfo().isConnected()) {
                        checkLogin(email, password);
                    } else {
                        Toast.makeText(getApplicationContext() ,"No Internet Connection", Toast.LENGTH_LONG).show();
                    }
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void checkLogin(final String email, final String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    success = jObj.getInt(TAG_SUCCESS);

                    // Check for error node in json
                    if (success == 1) {

                        String id = jObj.getString(TAG_ID);
                        String user_id = jObj.getString(TAG_USER_ID);
                        String first_name = jObj.getString(TAG_FIRST_NAME);
                        String last_name = jObj.getString(TAG_LAST_NAME);
                        String email = jObj.getString(TAG_EMAIL);
                        String mobile = jObj.getString(TAG_MOBILE);
                        String country = jObj.getString(TAG_COUNTRY);
                        String created_at = jObj.getString(TAG_CREATED_AT);
                        String status = jObj.getString(TAG_STATUS);
                        String role = jObj.getString(TAG_ROLE);


                        Log.e("Successfully Login!", jObj.toString());

                        Toast.makeText(getApplicationContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                        // menyimpan login ke session
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(session_status, true);
                        editor.putString(TAG_ID, id);
                        editor.putString(TAG_USER_ID, user_id);
                        editor.putString(TAG_FIRST_NAME, first_name);
                        editor.putString(TAG_LAST_NAME, last_name);
                        editor.putString(TAG_EMAIL, email);
                        editor.putString(TAG_MOBILE, mobile);
                        editor.putString(TAG_COUNTRY, country);
                        editor.putString(TAG_CREATED_AT, created_at);
                        editor.putString(TAG_STATUS, status);
                        editor.putString(TAG_ROLE, role);
                        editor.commit();

                        // Memanggil main activity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra(TAG_ID, id);
                        intent.putExtra(TAG_USER_ID, user_id);
                        intent.putExtra(TAG_FIRST_NAME, first_name);
                        intent.putExtra(TAG_LAST_NAME, last_name);
                        intent.putExtra(TAG_EMAIL, email);
                        intent.putExtra(TAG_MOBILE, mobile);
                        intent.putExtra(TAG_COUNTRY, country);
                        intent.putExtra(TAG_CREATED_AT, created_at);
                        intent.putExtra(TAG_STATUS, status);
                        intent.putExtra(TAG_ROLE, role);
                        finish();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();

                hideDialog();

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        MySingleton.getInstance(this).addToRequestQueue(strReq);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void Register(View view){
        Intent intent = new Intent (this, RegisterActivity.class);
        startActivity(intent);
        CustomIntent.customType(this, "fadein-to-fadeout");
    }
}
