package titik.com.pantaupadi.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import titik.com.pantaupadi.Activity.LoginActivity;
import titik.com.pantaupadi.R;
import titik.com.pantaupadi.Server.Server;

import static android.app.Activity.RESULT_OK;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_FIRST_NAME;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_ID;
import static titik.com.pantaupadi.Activity.LoginActivity.TAG_LAST_NAME;
import static titik.com.pantaupadi.Activity.AppController.getInstance;

public class Tambah extends Fragment {


    public static final String URL = Server.URL;
    private static final String UPLOAD_URL = Server.URL + "UploadGambarKuncoro.php";
    private ProgressDialog progress;
    private Bitmap bitmap, decoded;

    Calendar date = Calendar.getInstance();
    SimpleDateFormat mdformat = new SimpleDateFormat("yyyy-MM-dd");
    String strDate = mdformat.format(date.getTime());

    private static final String TAG = Tambah.class.getSimpleName();
    private static final String TAG_SUCCESS = "success";
    private  static final String TAG_MESSAGE = "message";
    SharedPreferences sharedPreferences;
    public static final String my_shared_preferences = "my_shared_preferences";

    EditText nama_penyakit, kondisi, solusi, usia;
    ImageView iv_gambar;
    private Button btn_simpan, btn_gambar;
    String user_id, first_name, last_name, penulis;

    int success;
    int PICK_IMAGE_REQUEST = 1;
    int bitmap_size = 60;

    String tag_json_obj = "json_obj_req";
    private String KEY_IMAGE = "gambar";
    private String KEY_NAME = "nama_penyakit";
    private String KEY_KONDISI = "kondisi";
    private String KEY_SOLUSI = "solusi";
    private String KEY_USIA = "usia";
    private String KEY_PENULIS = "penulis";
    private String KEY_USERID = "user_id";
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
              uploadImage();
//              Intent i = new Intent(view.getContext(), Beranda.class);
//              view.getContext().startActivity(i);
            }
        });
        btn_gambar = (Button)view.findViewById(R.id.btn_unggah_gambar);
        btn_gambar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                showFileChooser();
            }
        });
        return view;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage() {
        //menampilkan progress dialog
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Uploading...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e(TAG, "Response: " + response.toString());

                        try {
                            JSONObject jObj = new JSONObject(response);
                            success = jObj.getInt(TAG_SUCCESS);

                            if (success == 1) {
                                Log.e("v Add", jObj.toString());

                                Toast.makeText(getContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();

                                kosong();

                            } else {
                                Toast.makeText(getContext(), jObj.getString(TAG_MESSAGE), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //menghilangkan progress dialog
                        loading.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //menghilangkan progress dialog
                        loading.dismiss();

                        //menampilkan toast
                        Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.getMessage().toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                //membuat parameters
                Map<String, String> params = new HashMap<String, String>();

                //menambah parameter yang di kirim ke web servis
                params.put(KEY_IMAGE, getStringImage(decoded));
                params.put(KEY_NAME, nama_penyakit.getText().toString().trim());
                params.put(KEY_KONDISI, kondisi.getText().toString().trim());
                params.put(KEY_SOLUSI, solusi.getText().toString().trim());
                params.put(KEY_USIA, usia.getText().toString().trim());
                params.put(KEY_PENULIS, String.valueOf(first_name+ ' ' + last_name));
                params.put(KEY_USERID, String.valueOf(user_id));



                //kembali ke parameters
                Log.e(TAG, "" + params);

//                Toast.makeText(getContext(), "Berhasil horeeee"
//                + "\nnama penyakit = " + nama_penyakit.getText().toString()
//                + "\n kondisi = " + kondisi.getText().toString()
//                + "\nsolusi = " + solusi.getText().toString()
//                + "\nusia = " + usia.getText().toString()
//                + "\npenulis = " + KEY_PENULIS
//                + "\nuser id = " + KEY_USERID, Toast.LENGTH_SHORT).show();
                return params;
            }
        };

        getInstance().addToRequestQueue(stringRequest, tag_json_obj);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                //mengambil fambar dari Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
                setToImageView(getResizedBitmap(bitmap, 512));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void kosong() {
        iv_gambar.setImageResource(0);
        nama_penyakit.setText(null);
        kondisi.setText(null);
        solusi.setText(null);
        usia.setText(null);
    }

    private void setToImageView(Bitmap bmp) {
        //compress image
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, bitmap_size, bytes);
        decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes.toByteArray()));

        //menampilkan gambar yang dipilih dari camera/gallery ke ImageView
        iv_gambar.setImageBitmap(decoded);
    }

    // fungsi resize image
    public Bitmap getResizedBitmap(Bitmap gambar, int maxSize) {
        int width = gambar.getWidth();
        int height = gambar.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(gambar, width, height, true);
    }









//    private void showFileChooser() {
//        Intent intent = new Intent();
//        intent.setType("images/");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select"), PICK_IMAGE_REQUEST);
//        }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri filePath = data.getData();
//            try {
//                //mengambil fambar dari Gallery
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                // 512 adalah resolusi tertinggi setelah image di resize, bisa di ganti.
//                setToImageView(getResizedBitmap(bitmap, 512));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//
//    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
//        return byteArrayOutputStream.toByteArray();
//    }
//
//
//    private void uploadBitmap(final Bitmap bitmap) {
//
//        //getting the tag from the edittext
////        final String tagNamaPenyakit = nama_penyakit.getText().toString().trim();
////        final String tagSolusi = solusi.getText().toString().trim();
////        final String tagKondisi = kondisi.getText().toString().trim();
////        final String tagUsia = usia.getText().toString().trim();
//
//        //our custom volley request
//        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UPLOAD_URL,
//                new Response.Listener<NetworkResponse>() {
//                    @Override
//                    public void onResponse(NetworkResponse response) {
//                        try {
//                            JSONObject obj = new JSONObject(new String(response.data));
//                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), "erronya apah ? " + error.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }) {
//
//            /*
//             * If you want to add more parameters with the image
//             * you can do it here
//             * here we have only one parameter with the image
//             * which is tags
//             * */
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("user_id", String.valueOf(user_id));
//                params.put("nama_penyakit", nama_penyakit.getText().toString());
//                params.put("solusi", solusi.getText().toString());
//                params.put("kondisi", kondisi.getText().toString());
//                params.put("penulis", String.valueOf(first_name+ ' ' +last_name));
//                params.put("usia", usia.getText().toString());
//                return params;
//            }
//
//            /*
//             * Here we are passing image by renaming it with a unique name
//             * */
//            @Override
//            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
//                Map<String, DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
//                return params;
//            }
//        };
//
//        //adding the request to volley
//        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
//
//        Toast.makeText(getContext(), "Berhasil horeeee"
//                + "\nnama penyakit = " + nama_penyakit.getText().toString()
//                + "\n kondisi = " + kondisi.getText().toString()
//                + "\nsolusi = " + solusi.getText().toString()
//                + "\nusia = " + usia.getText().toString()
//                + "\npenulis = " + String.valueOf(first_name+ ' ' +last_name)
//                + "\nuser id = " + String.valueOf(user_id), Toast.LENGTH_SHORT).show();
//
////        MySingleton.getInstance(getContext()).addToRequestQueue(volleyMultipartRequest);
//    }
}
