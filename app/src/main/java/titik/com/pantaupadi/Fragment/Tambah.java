package titik.com.pantaupadi.Fragment;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.synnapps.carouselview.CarouselView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import titik.com.pantaupadi.Activity.LoginActivity;
import titik.com.pantaupadi.Activity.MainActivity;
import titik.com.pantaupadi.R;
import titik.com.pantaupadi.Server.MySingleton;
import titik.com.pantaupadi.Server.Server;
import titik.com.pantaupadi.VolleyMultipartRequest;

import static android.app.Activity.RESULT_OK;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_FIRST_NAME;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_ID;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_LAST_NAME;

public class Tambah extends Fragment {

    public static final String URL = Server.URL;
    private ProgressDialog progress;
    private Bitmap bitmap;

    Calendar date = Calendar.getInstance();
    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = mdformat.format(date.getTime());

    private static final String urltambah = Server.URL + "UploadGambar.php";
    private static final String TAG = Tambah.class.getSimpleName();
    private static final String TAG_SUCCESS = "value";

    EditText nama_penyakit, kondisi, solusi, usia;

    int success;
    private  static final String TAG_MESSAGE = "message";

    String user_id, first_name, last_name, penulis;
    SharedPreferences sharedPreferences;
    ImageView iv_gambar;
    public static final String my_shared_preferences = "my_shared_preferences";
    private Button btn_simpan, btn_gambar;
    ImageView imageView;
    private final int IMG_REQUEST = 1;
    final Tambah c = this;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.menu_tambah_info, container, false);
        getActivity().setTitle("Tambah Info");
//        Log.e("Tambah Info", "Tambah Info");
        sharedPreferences = getActivity().getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        user_id = sharedPreferences.getString(TAG_ID, null);
        first_name = sharedPreferences.getString(TAG_FIRST_NAME, null);
        last_name = sharedPreferences.getString(TAG_LAST_NAME, null);

        iv_gambar = (ImageView)view.findViewById(R.id.iv_gambar);
        nama_penyakit = (EditText)view.findViewById(R.id.et_nama_penyakit);
        kondisi = (EditText)view.findViewById(R.id.et_input_kondisi_);
        solusi = (EditText)view.findViewById(R.id.et_input_solusi_);
        usia = (EditText)view.findViewById(R.id.et_input_usia);
        btn_simpan = (Button)view.findViewById(R.id.btn_simpan);
        btn_simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadBitmap(bitmap);
//                simpanData();
            }
        });
        btn_gambar = (Button)view.findViewById(R.id.btn_unggah_gambar);
        btn_gambar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                select_image();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getActivity().getPackageName()));
            getActivity().finish();
            startActivity(intent);
            return view;
        }

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 0:
                if (resultCode == RESULT_OK && data != null) {

                    Uri imageUri = data.getData();
                    try {
                        //getting bitmap object from uri
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                        //displaying selected image to imageview
                        iv_gambar.setImageBitmap(bitmap);


//                        //calling the method uploadBitmap to upload image
//                        uploadBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
    private void select_image() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 0);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadBitmap(final Bitmap bitmap) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, urltambah,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.i(getContext().);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", String.valueOf(user_id));
                params.put("penulis", String.valueOf(first_name+' '+last_name));
                params.put("jenis_tanaman", nama_penyakit.getText().toString());
                params.put("kondisi", kondisi.getText().toString());
                params.put("solusi", solusi.getText().toString());
                params.put("usia", usia.getText().toString());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
//        MySingleton.getInstance(getContext()).addToRequestQueue(volleyMultipartRequest);


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
                params.put("usia", usia.getText().toString());
                return params;
            }
        };
        MySingleton.getInstance(getContext()).addToRequestQueue(postRequest);

    }
}
