package titik.com.pantaupadi.Activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import titik.com.pantaupadi.R;

import static org.opencv.imgcodecs.Imgcodecs.imread;

public class Tentang_CobaDeteksi extends AppCompatActivity {

    private static final String TAG = "MainActivity" ;
    ImageView imgView;
    Button processBtn;

    // Used to load the 'native-lib' library on application startup.
//    static {
//        System.loadLibrary("libopencv_java3");
//    }


    private BaseLoaderCallback _baseLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i(TAG, "OpenCV loaded successfully");

                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
            }
        }
    };

    //to check whether opencv is loaded successfully or not.
    static {
        if (OpenCVLoader.initDebug()){
            Log.d(TAG,"OpenCV loaded successfully");
        }else {
            Log.d(TAG,"OpenCV not loaded");

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tentang_coba_deteksi);

        ActivityCompat.requestPermissions(Tentang_CobaDeteksi.this,
                new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        processBtn = (Button) findViewById(R.id.processImgBtn);
        imgView = (ImageView) findViewById(R.id.img);


        processBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processImage();
            }
        });
    }

    private void processImage(){

        //reading input image from internal storage.
        Mat img = imread(Environment.getExternalStorageDirectory().getAbsolutePath() +"/BWD.png");

        Mat oImg = detectColor(img);

        // convert to bitmap:
        Bitmap bm = Bitmap.createBitmap(oImg.cols(), oImg.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(oImg,bm);
        imgView.setImageBitmap(bm);
    }

    private Mat detectColor(Mat srcImg) {
        Mat blurImg = new Mat();
        Mat hsvImage = new Mat();
        Mat color_range_red = new Mat();
        Mat color_range_green1 = new Mat();
        Mat color_range_green2 = new Mat();
        Mat color_range_green3 = new Mat();
        Mat color_range_green4 = new Mat();
        Mat color_range_blue = new Mat();
        Mat color_range = new Mat();

        //bluring image to filter noises
        Imgproc.GaussianBlur(srcImg, blurImg, new Size(5,5),0);

        //converting blured image from BGR to HSV
        Imgproc.cvtColor(blurImg, hsvImage, Imgproc.COLOR_BGR2HSV);

        //filtering red and green pixels based on given opencv HSV color range
        Core.inRange(hsvImage, new Scalar(0,50,50), new Scalar(5,255,255), color_range_red);
        Core.inRange(hsvImage, new Scalar(40,50,50), new Scalar(44,255,255), color_range_green1);
        Core.inRange(hsvImage, new Scalar(48,60,53), new Scalar(49,255,255), color_range_green2);
        Core.inRange(hsvImage, new Scalar(45,60,53), new Scalar(47,255,255), color_range_green3);
        Core.inRange(hsvImage, new Scalar(50.5 ,56,35), new Scalar(255,255,255), color_range_green4);
        Core.inRange(hsvImage, new Scalar(180,50,50), new Scalar(187,255,255), color_range_blue);

        //applying bitwise or to detect both red and green color.
        Core.bitwise_or(color_range_red,color_range_green4,color_range);

        return color_range;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(Tentang_CobaDeteksi.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            //try to load again
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, _baseLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            _baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

}