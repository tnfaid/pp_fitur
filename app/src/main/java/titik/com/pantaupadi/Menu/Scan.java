package titik.com.pantaupadi.Menu;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.synnapps.carouselview.CarouselView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import titik.com.pantaupadi.AppSettings;
import titik.com.pantaupadi.Data.DataPenyakit;
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

import static android.app.Activity.RESULT_OK;

public class Scan extends Fragment {

        public static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
        public static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 2;

        int brightnessModifier;

        /**
         * This instance of AppSettings is used to access and store the settings.
         */
        public AppSettings appSettings;

        /**
         * The OpenCV camera view used for the preview camera image.
         */
        public CameraView cameraView;

        /**
         * The listener that handles events (new image frame) from the camera view.
         */
        public CameraViewListener cameraViewListener;

        /**
         * The implementation of the abstract class ResistorDetector used for the detection.
         * This instance can be changed at runtime to switch between different detection methods.
         */
        public ResistorDetector resistorDetector;


        /**
         * The ResultListener that handles the result of the resistorDetector
         * after a detection process finished.
         */
        public ResistorDetector.ResultListener resultListener;

        /**
         * The callback used to handle the load event of the OpenCV library.
         */

        public BaseLoaderCallback baseLoaderCallback = new BaseLoaderCallback(getContext()) {

            @Override
            public void onManagerConnected(int status) {
                switch (status) {
                    case LoaderCallbackInterface.SUCCESS: {
                        Log.i("PenyakitDaunDetector", "OpenCV loaded successfully");
                        cameraView.enableView();
//                        System.loadLibrary("opencv_java");
                    }
                    break;
                    default: {
                        super.onManagerConnected(status);
                    }
                    break;
                }
            }
        };



        /**
         * Sets up the view and initializes the view elements (controls).
         *
         * @param savedInstanceState see android documentation
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
            final View view = inflater.inflate(R.layout.menu_scan_daun, container, false);


            getActivity().setTitle("Scan");
            Log.e("Scan", "Scan");

            cameraViewListener = new CameraViewListener();

            cameraView = (CameraView) view.findViewById(R.id.main_activity_camera_view);
            cameraView.setVisibility(SurfaceView.VISIBLE);

            // to use front camera:
            // cameraView.setCameraIndex(1);

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
            final ImageView resultDetailsButton = (ImageView) view.findViewById(R.id.main_activity_details_button);

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
                            if (detectionResult.getResistorValue() == DetectionResult.UNKNOWN_RESISTANCE_VALUE) {
                                resultTextView.setText("N/A");
                            } else {
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


        /**
         * Sets up and initializes the button which starts the detection process.
         * When the button is pressed, the image matrix of the inside of the displayed indicator
         * will be used to start the resistor detection.
         */
        public void setupStartDetectionControl() {
            ImageView startDetectionButton = (ImageView) getActivity().findViewById(R.id.mainActivity_start_detection);

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

        /**
         * Sets up and initializes the button which opens the settings activity.
         * A click on this button opens the SettingsActivity.
         */
        public void setupSettingsControl() {
            ImageView settingsButton = (ImageView) getActivity().findViewById(R.id.mainActivity_settings);

            settingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(), SettingsActivity.class));
                }
            });

            settingsButton.setVisibility(View.VISIBLE);
        }

        /**
         * Sets up and initializes the spinner to select the number of bands the resistor has.
         * The values of the ResistorDetector.NumberOfBands enum are used as values for the spinner.
         * If the spinner is changed, the selected enum value is set in the resistor detector.
         */
        public void setupNumberOfBandsControl() {
            Spinner numberOfBandsSelect = (Spinner) getActivity().findViewById(R.id.mainActivity_number_of_bands);

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

        /**
         * Sets up and initializes the button which saves the current image content
         * of the indicator to a file.
         */
        public void setupSaveImageControl() {
            ImageView saveImageButton = (ImageView) getActivity().findViewById(R.id.mainActivity_save_image);

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

                                Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("Exception", e.getMessage());
                        }
                    }
                }
            });

            saveImageButton.setVisibility(View.VISIBLE);
        }

        /**
         * Sets up and initializes the button which toggles the flash of the camera.
         * The initial value is set to the last saved value (if there is any)
         * or to the default value.
         * <p>
         * If the button is toggled, the new flash state is set in the camera view.
         * This button is only displayed if the camera supports a flashlight.
         */
        public void setupFlashControl() {
            final ToggleButton flashToggle = (ToggleButton) getActivity().findViewById(R.id.mainActivity_flash_toggle);

            if (cameraView.isFlashSupported()) {
                boolean initFlashState = appSettings.getFlashEnabled();

                flashToggle.setChecked(initFlashState);

                cameraView.setFlashState(initFlashState);

                flashToggle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cameraView.setFlashState(flashToggle.isChecked());

                        appSettings.saveFlashEnabled(flashToggle.isChecked());
                    }
                });

                flashToggle.setVisibility(View.VISIBLE);
            } else {
                flashToggle.setVisibility(View.GONE);
            }
        }

        /**
         * Sets up and initializes the seekbar which is used to zoom he camera image.
         * The initial value is set to the last saved value (if there is any)
         * or to the default value.
         * <p>
         * If the value is changed, the zoom factor of the camera view is set in realtime.
         * <p>
         * It is only displayed if the camera supports a flashlight.
         */
        public void setupZoomControl() {
            SeekBar zoomControl = (SeekBar) getActivity().findViewById(R.id.main_activity_camera_zoom);

            if (cameraView.isZoomSupported()) {
                zoomControl.setMax(cameraView.getMaxZoom());

                int initZoomLevel = appSettings.getZoomLevel();

                if (initZoomLevel < 0 || initZoomLevel > cameraView.getMaxZoom()) {
                    initZoomLevel = (int) (cameraView.getMaxZoom() * 0.3);  //30% of max zoom level
                }

                zoomControl.setProgress(initZoomLevel);

                cameraView.setZoomLevel(initZoomLevel);

                zoomControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        cameraView.setZoomLevel(progress);

                        appSettings.saveZoomLevel(progress);
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

        /**
         * Called when the app is paused (e.g. moved to background).
         * Stops the preview of the camera image.
         */
        @Override
        public void onPause() {
            super.onPause();
            if (cameraView != null) {
                cameraView.disableView();
            }
        }

        /**
         * Called after the app is resumed (e.g. after start).
         * Loads the last saved settings and uses them to initialize the app.
         * Loads the OpenCV libs.
         */
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

        /**
         * Called when the app is stopped.
         * Stops the preview of the camera image.
         */
        @Override
        public void onDestroy() {
            super.onDestroy();
            if (cameraView != null)
                cameraView.disableView();
        }

        /**
         * Loads the saved settings (or the default values)
         * and configures the camera view listener.
         */
        public void loadCameraListenerSettings()
        {
            appSettings = new AppSettings(getContext());
            brightnessModifier = appSettings.getBrightnessModifier();
            cameraViewListener.setBrightnessModifier(brightnessModifier);

            float contrastModifier = appSettings.getContrastModifier();
            cameraViewListener.setContrastModifier(contrastModifier);

            int[] colorModifiers = appSettings.getColorModifiers();
            cameraViewListener.setColorModifiers(colorModifiers[0], colorModifiers[1], colorModifiers[2]);

            CameraViewListener.IndicatorSize indicatorSize = appSettings.getIndicatoreSize();
            cameraViewListener.setIndicatorSize(indicatorSize);


        }

        /**
         * Loads the saved settings (or the default values)
         * and configures resistor detector.
         */
        public void loadResistorDetecionSettings() {
            DetectionMode detectionMode = appSettings.getDetectionMode();

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

        /**
         * Asks the user for the permissions the app needs.
         */
        public void requestPermissions() {
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
