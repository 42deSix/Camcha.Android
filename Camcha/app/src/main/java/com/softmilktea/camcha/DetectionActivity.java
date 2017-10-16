package com.softmilktea.camcha;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

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
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by SEJIN on 2017-10-03.
 */

public class DetectionActivity extends AppCompatActivity
        implements CameraBridgeViewBase.CvCameraViewListener2 {

    private static final String TAG = "opencv";
    private CameraBridgeViewBase mOpenCvCameraView;
    private Mat mMatInput;
    private Mat mMatResult;

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

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.activity_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        mOpenCvCameraView.setCameraIndex(0); // front-camera(1),  back-camera(0)
        mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "onResume :: Internal OpenCV library not found.");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "onResum :: OpenCV library found inside package. Using it!");
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


        ImageButton backButton = (ImageButton)findViewById(R.id.detection_back_button);
        ImageButton reportButton = (ImageButton)findViewById(R.id.detection_report_button);
        ImageButton takeSnapshotButton = (ImageButton)findViewById(R.id.detection_take_snapshot_button);
        ImageButton viewSnapshotButton = (ImageButton)findViewById(R.id.detection_view_snapshot_button);

        backButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reportButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        takeSnapshotButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!hasPermissions(PERMISSIONS)) {
                        requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                    else {
                        saveSnapshot();
                    }
                }
            }
        });
        viewSnapshotButton.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return mMatResult;
    }


    public void saveSnapshot() {
        String camchaRootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Camcha";
        File camchaDir = new File(camchaRootPath);
        if(!camchaDir.exists()) {
            camchaDir.mkdirs();
        }

        Bitmap captureView = Bitmap.createBitmap(mMatResult.width(), mMatResult.height(), Bitmap.Config.ARGB_8888); // 왜 되는지 모름. rows(), cols() 쓰면 안 됨. 값이 정확히 맞아야 되나...
        try {
            Utils.matToBitmap(mMatResult, captureView);
        }
        catch(CvException e) {
            Log.e(TAG, e.getMessage());
        }

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        Date currentLocalTime = cal.getTime();
        DateFormat date = new SimpleDateFormat("yyMMddHHmmssS");
        date.setTimeZone(TimeZone.getTimeZone("GMT+9"));
        String fileName = date.format(currentLocalTime) + ".png";

        try {
            File imageToSave = new File(camchaDir, fileName);
            FileOutputStream out = new FileOutputStream(imageToSave);
            if(!captureView.compress(Bitmap.CompressFormat.PNG, 0, out)) {
            }
            out.close();
            Toast.makeText(getApplicationContext(), "스냅샷이 저장되었습니다.", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }


    //여기서부턴 퍼미션 관련 메소드
    private final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    String[] PERMISSIONS = {"android.permission.WRITE_EXTERNAL_STORAGE"};


    private boolean hasPermissions(String[] permissions) {
        int result;
        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (String perms : permissions) {
            result = ContextCompat.checkSelfPermission(this, perms);
            if (result == PackageManager.PERMISSION_DENIED) {
                //허가 안된 퍼미션 발견
                return false;
            }
        }
        //모든 퍼미션이 허가되었음
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0) {
                    boolean writeExternalStoragePermissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if(!writeExternalStoragePermissionAccepted) {
                        showDialogForPermission("스냅샷을 저장하기 위해서는 저장공간에 대한 접근권한이 필요합니다.\n설정 > 애플리케이션 관리자 > Camcha > 앱 권한에 들어가서 저장공간 권한을 켜주세요.");
                    }
                    else {
                        saveSnapshot();
                    }
                }
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(DetectionActivity.this);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create().show();
    }

}
