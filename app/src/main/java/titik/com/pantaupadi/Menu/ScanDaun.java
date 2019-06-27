package titik.com.pantaupadi.Menu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import titik.com.pantaupadi.AppSettings;
import titik.com.pantaupadi.DetectionResultHolder;
import titik.com.pantaupadi.R;
import titik.com.pantaupadi.detection.ColumnsResistorDetector;
import titik.com.pantaupadi.detection.ContoursModResistorDetector;
import titik.com.pantaupadi.detection.DetectionResult;
import titik.com.pantaupadi.detection.ExperimentsResistorDetector;
import titik.com.pantaupadi.detection.ResistorDetector;
import titik.com.pantaupadi.ui.CameraView;
import titik.com.pantaupadi.ui.CameraViewListener;
import titik.com.pantaupadi.ui.DetectionDetailsActivity;
import titik.com.pantaupadi.ui.DetectionMode;
import titik.com.pantaupadi.ui.SettingsActivity;

public class ScanDaun extends Fragment {
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 2;
    private AppSettings settings;
    private CameraView cameraView;
    private CameraViewListener cameraViewListener;
    private ResistorDetector resistorDetector;
    private ResistorDetector.ResultListener resultListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_scan_daun, container, false);

        settings = new AppSettings(getContext());
        cameraViewListener = new CameraViewListener();

        cameraView = (CameraView) view.findViewById(R.id.main_activity_camera_view);
        cameraView.setVisibility(SurfaceView.VISIBLE);

        cameraView.setCvCameraViewListener(cameraViewListener);

        cameraView.setOnCameraInitializedCallback(new CameraView.OnCameraInitializedCallback() {
            @Override
            public void cameraViewInitialized() {
                setupZoomControl();
                setupFlashControl();
                setupStartDetectionControl();
                setupSaveImageControl();
                setupSettingsControl();
                setupNumberOfBandsControl();
            }
        });

        final TextView resultTextView = (TextView) view.findViewById(R.id.main_activity_result_text);
        final Button resultDetailsButton = (Button) view.findViewById(R.id.main_activity_details_button);


        resultDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DetectionDetailsActivity.class));
            }
        });

        resultListener = new ResistorDetector.ResultListener() {
            @Override
            public void resultReady(final DetectionResult detectionResult) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(detectionResult.getResistorValue() == DetectionResult.UNKNOWN_RESISTANCE_VALUE){
                            resultTextView.setText("N/A");
                        }else{
                            resultTextView.setText(detectionResult.getResistorValue() + " Ohm");
                        }
                        resultTextView.setVisibility(View.VISIBLE);

                        DetectionResultHolder.setDetectionResult(detectionResult);

                        resultDetailsButton.setVisibility(View.VISIBLE);
                        resultDetailsButton.setEnabled(true);
                    }
                });
            }
        };

        requestPermissions();

        return view;
    }

    private BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(getActivity()) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    Log.i("ResistorDetector", "OpenCV loaded successfully");
                    cameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    private void setupStartDetectionControl() {
        Button startDetectionButton = (Button) cameraView.findViewById(R.id.mainActivity_start_detection);

        startDetectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Mat resistorImage = cameraViewListener.getResistorImage();

                Imgproc.cvtColor(resistorImage, resistorImage, Imgproc.COLOR_RGBA2BGR);

                resistorDetector.detectResistorValue(resistorImage);

                resistorImage.release();
            }
        });

        startDetectionButton.setVisibility(View.VISIBLE);
    }

    private void setupSettingsControl() {
        Button settingsButton = (Button) cameraView.findViewById(R.id.mainActivity_settings);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingsActivity.class));
            }
        });

        settingsButton.setVisibility(View.VISIBLE);
    }

    private void setupNumberOfBandsControl() {
        Spinner numberOfBandsSelect = (Spinner) cameraView.findViewById(R.id.mainActivity_number_of_bands);

        final ArrayAdapter<String> modeElements = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);

        for (ResistorDetector.NumberOfBands numberOfBands : ResistorDetector.NumberOfBands.values()) {
            modeElements.add(numberOfBands.name());
        }

        modeElements.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        numberOfBandsSelect.setAdapter(modeElements);

        numberOfBandsSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ResistorDetector.NumberOfBands selectedNumberOfBands = ResistorDetector.NumberOfBands.valueOf(modeElements.getItem(position));

                resistorDetector.setNumberOfBands(selectedNumberOfBands);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        numberOfBandsSelect.setVisibility(View.VISIBLE);
    }


    private void setupSaveImageControl() {
        Button saveImageButton = (Button) cameraView.findViewById(R.id.mainActivity_save_image);

        saveImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Mat resistorImage = cameraViewListener.getResistorImage();

                Bitmap bitmap = null;

                try {
                    bitmap = Bitmap.createBitmap(resistorImage.cols(), resistorImage.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(resistorImage, bitmap);
                } catch (CvException e) {
                    Log.d("Exception", e.getMessage());
                }

                if (bitmap != null) {
                    try {
                        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "resistorDetector");
                        boolean isPresent = true;
                        if (!folder.exists()) {
                            isPresent = folder.mkdir();
                        }

                        if (isPresent) {
                            File file = new File(folder, UUID.randomUUID().toString() + ".png");
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                            out.close();

                            Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Log.d("Exception", e.getMessage());
                    }
                }
            }
        });

        saveImageButton.setVisibility(View.VISIBLE);
    }

    private void setupFlashControl() {
        final ToggleButton flashToggle = (ToggleButton) cameraView.findViewById(R.id.mainActivity_flash_toggle);

        if (cameraView.isFlashSupported()) {
            boolean initFlashState = settings.getFlashEnabled();

            flashToggle.setChecked(initFlashState);

            cameraView.setFlashState(initFlashState);

            flashToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cameraView.setFlashState(flashToggle.isChecked());

                    settings.saveFlashEnabled(flashToggle.isChecked());
                }
            });

            flashToggle.setVisibility(View.VISIBLE);
        } else {
            flashToggle.setVisibility(View.GONE);
        }
    }

    private void setupZoomControl() {
        SeekBar zoomControl = (SeekBar) cameraView.findViewById(R.id.main_activity_camera_zoom);

        if (cameraView.isZoomSupported()) {
            zoomControl.setMax(cameraView.getMaxZoom());

            int initZoomLevel = settings.getZoomLevel();

            if (initZoomLevel < 0 || initZoomLevel > cameraView.getMaxZoom()) {
                initZoomLevel = (int) (cameraView.getMaxZoom() * 0.3);  //30% of max zoom level
            }

            zoomControl.setProgress(initZoomLevel);

            cameraView.setZoomLevel(initZoomLevel);

            zoomControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    cameraView.setZoomLevel(progress);

                    settings.saveZoomLevel(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            zoomControl.setVisibility(View.VISIBLE);
        } else {
            zoomControl.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (cameraView != null) {
            cameraView.disableView();
        }
    }

    @Override
    public void onResume() {
        //remove all settings after the type of one settings item changed!
        //settings.removeAll();

        loadCameraListenerSettings();
        loadResistorDetecionSettings();

        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d("ResistorDetector", "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, getContext(), baseLoaderCallback);
        } else {
            Log.d("ResistorDetector", "OpenCV library found inside package. Using it!");
            baseLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cameraView != null)
            cameraView.disableView();
    }

    private void loadCameraListenerSettings() {
        int brightnessModifier = settings.getBrightnessModifier();
        cameraViewListener.setBrightnessModifier(brightnessModifier);

        float contrastModifier = settings.getContrastModifier();
        cameraViewListener.setContrastModifier(contrastModifier);

        int[] colorModifiers = settings.getColorModifiers();
        cameraViewListener.setColorModifiers(colorModifiers[0], colorModifiers[1], colorModifiers[2]);

        CameraViewListener.IndicatorSize indicatorSize = settings.getIndicatoreSize();
        cameraViewListener.setIndicatorSize(indicatorSize);

    }

    private void loadResistorDetecionSettings() {
        DetectionMode detectionMode = settings.getDetectionMode();

        switch (detectionMode) {
            case ColumnResistorDetection:
                resistorDetector = new ColumnsResistorDetector(resultListener);
                break;
            case ContoursModResistorDetection:
                resistorDetector = new ContoursModResistorDetector(resultListener);
                break;
            case ExperimentsResistorDetection:
                resistorDetector = new ExperimentsResistorDetector(resultListener);
                break;
        }
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
        }
    }

}
