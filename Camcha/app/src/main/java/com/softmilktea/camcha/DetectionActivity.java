package com.softmilktea.camcha;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by SEJIN on 2017-10-03.
 */

public class DetectionActivity extends AppCompatActivity
        implements CameraBridgeViewBase.CvCameraViewListener2 {

    private CameraBridgeViewBase mOpenCvCameraView;
    private Mat mMatInput;
    private Mat mMatResult;
    private String mCamchaRootPath;
    private String mFileName;
    private String[] mPermissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};

    private ImageButton mBackButton;
    private ImageButton mReportButton;
    private ImageButton mTakeSnapshotButton;
    private ImageButton mViewSnapshotButton;

    private DetectionResult mDetectionResult;
    private LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
//            mDetectionResult = new DetectionResult("test", Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));    // "Samsung phones have this problem." - https://stackoverflow.com/questions/14700755/locationmanager-requestlocationupdates-not-working
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    public native void ConvertRGBtoGray(long matAddrInput, long matAddrResult);


    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("native-lib");
    }


    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS: {
                    mOpenCvCameraView.enableView();
                }
                break;
                default: {
                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_detection);

        /* get camera view */
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.activity_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setCameraIndex(0); // front-camera(1),  back-camera(0)
        mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);

        /* storage access */
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("yyMMddHHmmssS");
        date.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        mCamchaRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Camcha";
        mFileName = date.format(currentLocalTime) + ".png";

        /* buttons */
        mBackButton = (ImageButton)findViewById(R.id.detection_back_button);
        mReportButton = (ImageButton)findViewById(R.id.detection_report_button);
        mTakeSnapshotButton = (ImageButton)findViewById(R.id.detection_take_snapshot_button);
        mViewSnapshotButton = (ImageButton)findViewById(R.id.detection_view_snapshot_button);
        changeImageOnImageButton(mCamchaRootPath, mViewSnapshotButton);

        /* server connection */
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( ContextCompat.checkSelfPermission(DetectionActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if(locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
                List<String> providers = locationManager.getProviders(true);
                for(String provider : providers) {
                    locationManager.requestLocationUpdates(provider, 0, 0, mLocationListener);
                    if(provider != null) {
                        Location location = locationManager.getLastKnownLocation(provider);
                        if(location != null) {
                            mDetectionResult = new DetectionResult("test", Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));
                        }
                    }
                }

                Gson gson = new Gson();
                String jsonString = gson.toJson(mDetectionResult);
                new ConnectToServerAsync(jsonString).execute();

                locationManager.removeUpdates(mLocationListener);
            }
        }
    }

    @Override
    public void onPause(    ) {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()) {
            Dlog.d("onResume :: Internal OpenCV library not found.");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        } else {
            Dlog.d("onResum :: OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();

        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        mMatInput = inputFrame.rgba();

        if (mMatResult != null) mMatResult.release();
        mMatResult = new Mat(mMatInput.rows(), mMatInput.cols(), mMatInput.type());

        ConvertRGBtoGray(mMatInput.getNativeObjAddr(), mMatResult.getNativeObjAddr());


        mBackButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mReportButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mTakeSnapshotButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!BaseApplication.hasPermissions(DetectionActivity.this, mPermissions)) {
                        requestPermissions(mPermissions, BaseApplication.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                    else {
                        saveSnapshot(mMatResult, mCamchaRootPath, mFileName);
                        changeImageOnImageButton(mCamchaRootPath, mViewSnapshotButton);
                    }
                }
            }
        });
        mViewSnapshotButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetectionActivity.this, SnapshotGalleryActivity.class));
            }
        });

        return mMatResult;
    }

    /**
     * @author Sejin Jeon
     * It saves a snapshot of mMatResult as a png file in the Android file system.
     */
    public void saveSnapshot(Mat mat, String rootPath, String fileName) {
        File camchaDir = new File(rootPath);
        if(!camchaDir.exists()) {
            if (!camchaDir.mkdirs()) {
                Dlog.e("Failed to create directory");
            }
        }

        Bitmap captureView = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888); // 왜 되는지 모름. rows(), cols() 쓰면 안 됨. 값이 정확히 맞아야 되나...
        try {
            Utils.matToBitmap(mat, captureView);
            File imageToSave = new File(camchaDir, fileName);
            FileOutputStream out = new FileOutputStream(imageToSave);
            if(!captureView.compress(Bitmap.CompressFormat.PNG, 0, out)) {
            }
            out.close();
            Toast.makeText(getApplicationContext(), "스냅샷이 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (CvException e) {
            Dlog.e(e.getMessage());
        } catch (FileNotFoundException e) {
            Dlog.e(e.getMessage());
        } catch (IOException e) {
            Dlog.e(e.getMessage());
        }
    }

    /**
     * Get the most recent snapshot in bitmap format
     * @author Sejin Jeon
     * @param dir
     * @return Bitmap
     */
    public Bitmap getSnapshotPreview(String dir) {
        Bitmap resultBitmap;
        File targetDirectory = new File(dir);
        if(!targetDirectory.exists()) {
            resultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_none);
            if(!targetDirectory.mkdir()) {
                Dlog.e("Cannot make directory");
            }
        }
        else {
            File[] pngFiles = targetDirectory.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".png");
                }
            });
            int numFiles = pngFiles.length;
            if(numFiles > 0) {
                resultBitmap = BitmapFactory.decodeFile(pngFiles[numFiles - 1].getPath());
            }
            else {
                resultBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_none);
            }
        }
        return resultBitmap;
    }

    /**
     * Literally change image on an image button
     * @author Sejin Jeon
     */
    public void changeImageOnImageButton(String path, ImageButton imageButton) {
        Bitmap snapshotPreview = getSnapshotPreview(path);
        BitmapDrawable snapshotPreviewBitmapDrawable = new BitmapDrawable(getResources(), snapshotPreview);
        imageButton.setBackground(snapshotPreviewBitmapDrawable);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case BaseApplication.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0) {
                    boolean writeExternalStoragePermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(!writeExternalStoragePermissionAccepted) {
                        BaseApplication.showDialogForPermission(DetectionActivity.this, getSupportFragmentManager(), "스냅샷을 저장하기 위해서는 저장공간에 대한 접근권한이 필요합니다.\n설정 > 애플리케이션 관리자 > Camcha > 앱 권한에 들어가서 저장공간 권한을 켜주세요.");
                    }
                    else {
                        saveSnapshot(mMatResult, mCamchaRootPath, mFileName);
                    }
                }
        }
    }
}
