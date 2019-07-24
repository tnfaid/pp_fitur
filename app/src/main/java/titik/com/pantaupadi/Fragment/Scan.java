package titik.com.pantaupadi.Fragment;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import titik.com.pantaupadi.Activity.AppSettings;
import titik.com.pantaupadi.Activity.DetectionResultHolder;
import titik.com.pantaupadi.R;
import titik.com.pantaupadi.FungsiDeteksi.ColumnsResistorDetector;
import titik.com.pantaupadi.FungsiDeteksi.ContoursModResistorDetector;
import titik.com.pantaupadi.FungsiDeteksi.DetectionResult;
import titik.com.pantaupadi.FungsiDeteksi.ExperimentsResistorDetector;
import titik.com.pantaupadi.FungsiDeteksi.ResistorDetector;
import titik.com.pantaupadi.Activity.Detection.CameraView;
import titik.com.pantaupadi.Activity.Detection.CameraViewListener;
import titik.com.pantaupadi.Activity.Detection.DetectionDetailsActivity;
import titik.com.pantaupadi.Activity.Detection.DetectionMode;
import titik.com.pantaupadi.Activity.Detection.SettingsActivity;

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


        double result = 0;
        int counter = 0;
        int hasil = 0;
        double temp = 0;
        int realResult = 0;
        ArrayList<Integer> value = new ArrayList<Integer>();
        ArrayList<Integer> firstDigit = new ArrayList<Integer>();
        ArrayList<Double> similarValue = new ArrayList<Double>();
        private DatePickerDialog datePickerDialog;
        private SimpleDateFormat dateFormatter;
        private TextView modus_nilai;
        private Button btn_tambah_tanggal;
        EditText txt_usia;

//        ArrayList<Integer> tempFirstDigit= new ArrayList<Integer>();
//        ArrayList<Integer> tempCounter= new ArrayList<Integer>();
//        ArrayList<Integer> tempIndex= new ArrayList<Integer>();
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
//                    setupZoomControl();
                    setupFlashControl();
                    setupStartDetectionControl();
                    setupSaveImageControl();
                    setupSettingsControl();
//                    setupNumberOfBandsControl();
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
// INILOOOOOOOOOO
            resultListener = new ResistorDetector.ResultListener() {
                @Override
                public void resultReady(final DetectionResult detectionResult) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (detectionResult.getResistorValue() == DetectionResult.UNKNOWN_RESISTANCE_VALUE) {
                                resultTextView.setText("N/A");
                            } else {
                                resultTextView.setText( " Warna " + detectionResult.getResistorValue() );
//                                result[0] = detectionResult.getResistorValue();
                                if(value.size() >= 5){
                                    Toast.makeText(getContext(), "FULL SAM", Toast.LENGTH_SHORT).show();
//                                    result = iconList.get(0) +
//                                    Toast.makeText(getContext(),""+iconList.size() + "warna "+ colorRgba, Toast.LENGTH_SHORT).show();
//                                    Toast.makeText(getContext(),""+value.size() + "warna "+ firstDigit, Toast.LENGTH_SHORT).show();

                                    //ini untuk pop up dialog input usia tanaman


//

//                                    Intent intent = new Intent(getActivity(), DetectionDetailsActivity.class);
//                                    intent.putExtra("hasil",hasil);
//                                    getActivity().startActivity(intent);

                                } else {
                                    Toast.makeText(getContext(), "" + value.size() + "warnaaa " + firstDigit, Toast.LENGTH_SHORT).show();
//                                    value.add(detectionResult.getResistorValue());
                                    firstDigit.add(Integer.parseInt(Integer.toString(detectionResult.getResistorValue()).substring(0, 1)));

                                    for (int h = 0; h < firstDigit.size(); h++) {
                                        for (int i = h + 1; i < firstDigit.size(); i++) {
                                            if (firstDigit.get(h) > firstDigit.get(i)) {
                                                counter = firstDigit.get(i);
                                                firstDigit.set(i, firstDigit.get(h));
                                                firstDigit.set(h, counter);
                                            }
                                        }
                                        value.add(firstDigit.get(h));
                                        result = result + value.get(h);
                                        Toast.makeText(getContext(), "gambar ke " + h, Toast.LENGTH_SHORT).show();
                                    }
                                    //rata"
                                    result = result / (double) value.size();

//                                    hold dulu yaaaah !
                                    temp = (double) value.get(0) - result;
                                    for (int h = 0; h < value.size(); h++) {
                                        similarValue.add(Math.abs((double) value.get(h) - result));
                                        if (similarValue.get(h) < temp) {
                                            temp = similarValue.get(h);
                                            hasil = h;
                                        }
                                    }
                                    Toast.makeText(getContext(), "Rata2 : " + result, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getContext(), "Hasil : " + value.get(hasil), Toast.LENGTH_LONG).show();
                                }
//                                Toast.makeText(getContext(), ""+detectionResult.getResistorValue(), Toast.LENGTH_SHORT).show();
                                }
                            resultTextView.setVisibility(View.VISIBLE);

                            DetectionResultHolder.setDetectionResult(detectionResult);

                            resultDetailsButton.setVisibility(View.VISIBLE);
                            resultDetailsButton.setEnabled(true);
                        }

                        private void showDateDialog(){

                            /**
                             * Calendar untuk mendapatkan tanggal sekarang
                             */
                            Calendar newCalendar = Calendar.getInstance();

                            /**
                             * Initiate DatePicker dialog
                             */
                            datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                    /**
                                     * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                                     */

                                    /**
                                     * Set Calendar untuk menampung tanggal yang dipilih
                                     */
                                    Calendar newDate = Calendar.getInstance();
                                    newDate.set(year, monthOfYear, dayOfMonth);

                                    /**
                                     * Update TextView dengan tanggal yang kita pilih
                                     */
//                                    tvDateResult.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()));
                                }

                            },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                            /**
                             * Tampilkan DatePicker dialog
                             */
                            datePickerDialog.show();
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

        Mat colorRgba;
        int cobaHasil = 5;

        public void setupStartDetectionControl() {
            ImageView startDetectionButton = (ImageView) getActivity().findViewById(R.id.mainActivity_start_detection);

            startDetectionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(value.size() >= 5) {
                        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getContext());
                        View mView = layoutInflaterAndroid.inflate(R.layout.add_date_dialog, null);
                        android.support.v7.app.AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getContext());
                        alertDialogBuilderUserInput.setView(mView);

                        modus_nilai = (TextView) mView.findViewById(R.id.tv_nilai_modus);
                        txt_usia = (EditText) mView.findViewById(R.id.txt_usia_tanaman);

                        modus_nilai.setText("Hasil nilai perhitungan " + cobaHasil);
                        alertDialogBuilderUserInput
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        // ToDo get user input here'
                                        Intent intent = new Intent(getActivity(), DetectionDetailsActivity.class);
                                        intent.putExtra("hasil", cobaHasil);
                                        intent.putExtra("usia", txt_usia.getText().toString());
                                        getActivity().startActivity(intent);
                                    }
                                })

                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                dialogBox.cancel();
                                            }
                                        });

                        android.support.v7.app.AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                        alertDialogAndroid.show();
                    }
                    Mat resistorImage = cameraViewListener.getResistorImage();

                    Imgproc.cvtColor(resistorImage, resistorImage, Imgproc.COLOR_RGBA2BGR);
                    colorRgba = resistorImage;
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
//        public void setupNumberOfBandsControl() {
//            Spinner numberOfBandsSelect = (Spinner) getActivity().findViewById(R.id.mainActivity_number_of_bands);
//
//            final ArrayAdapter<String> modeElements = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item);
//
//            for (ResistorDetector.NumberOfBands numberOfBands : ResistorDetector.NumberOfBands.values()) {
//                modeElements.add(numberOfBands.name());
//            }
//
//            modeElements.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//            numberOfBandsSelect.setAdapter(modeElements);
//
//            numberOfBandsSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    ResistorDetector.NumberOfBands selectedNumberOfBands = ResistorDetector.NumberOfBands.valueOf(modeElements.getItem(position));
//
//                    resistorDetector.setNumberOfBands(selectedNumberOfBands);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });
//
//            numberOfBandsSelect.setVisibility(View.VISIBLE);
//        }

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
//        public void setupZoomControl() {
//            SeekBar zoomControl = (SeekBar) getActivity().findViewById(R.id.main_activity_camera_zoom);
//
//            if (cameraView.isZoomSupported()) {
//                zoomControl.setMax(cameraView.getMaxZoom());
//
//                int initZoomLevel = appSettings.getZoomLevel();
//
//                if (initZoomLevel < 0 || initZoomLevel > cameraView.getMaxZoom()) {
//                    initZoomLevel = (int) (cameraView.getMaxZoom() * 0.3);  //30% of max zoom level
//                }
//
//                zoomControl.setProgress(initZoomLevel);
//
//                cameraView.setZoomLevel(initZoomLevel);
//
//                zoomControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                        cameraView.setZoomLevel(progress);
//
//                        appSettings.saveZoomLevel(progress);
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });
//
//                zoomControl.setVisibility(View.VISIBLE);
//            } else {
//                zoomControl.setVisibility(View.GONE);
//            }
//        }

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
