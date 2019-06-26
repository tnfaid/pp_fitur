package titik.com.pantaupadi.Menu;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.synnapps.carouselview.CarouselView;

import titik.com.pantaupadi.Data.DataPenyakit;
import titik.com.pantaupadi.R;

import static android.app.Activity.RESULT_OK;

public class Scan extends Fragment {

    RelativeLayout view;
    public Scan(){}

    final int CAMERA_CAPTURE = 1;
    private Uri picUri;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = (RelativeLayout) inflater.inflate(R.layout.menu_scan_daun, container, false);

        getActivity().setTitle("Scan Daun");
        Log.e("Scan Daun", "Scan Daun");

        try{
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(captureIntent, CAMERA_CAPTURE);
        }

        catch(ActivityNotFoundException anfe){
            String errorMessage = "Whoops - Perangkat anda tidak mendukung untuk mengambil gambar!";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            if(requestCode == CAMERA_CAPTURE){
                picUri = data.getData();
                performCrop();
            }
        }
    }

    private void performCrop() {
        try {

        }

        catch(ActivityNotFoundException anfe){
//            menampilkan sebuah error message
            String errorMessage = " oopss - perangkat anda tidak mendukung aksi crop";
            Toast toast = Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
