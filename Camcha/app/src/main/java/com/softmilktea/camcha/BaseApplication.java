package com.softmilktea.camcha;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * Created by SEJIN on 2017-10-16.
 */

public class BaseApplication extends Application {
    public static boolean DEBUG;
    public static final float DP = Resources.getSystem().getDisplayMetrics().density;
    public static final int SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;

    /* fragment transaction */
    public static final int NUM_ITEMS = 2;
    public static boolean GO_TO_REPORT_FRAGMENT = false;

    /* permission */
    public static final int PERMISSIONS_REQUEST_CAMERA = 1;
    public static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 2;
    public static final int PERMISSIONS_REQUEST_FINE_LOCATION = 3;

    /* safety map */
    public static final String CLIENT_ID = "BR56NxCjsDHKMVGf0WyJ"; // Naver API application client id
    public static final String SERVER_ADDRESS = "http://52.79.151.80";
    public static final String[] QUERY_LIST = {
            "SEND_DETECTION_RESULT",
            "RECEIVE_DETECTION_DATA"
    };
//    public static Map<String, String> RESPONSE_DATA = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        this.DEBUG = isDebuggable(this);
    }

    /**
     * Returns whether currently debug mode is on or not.
     *
     * @param context
     * @return
     */
    private boolean isDebuggable(Context context) {
        boolean debuggable = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
        /* debuggable variable will remain false */
        }

        return debuggable;
    }

    /**
     * Check if all permissions are granted
     * @param context
     * @param permissions
     * @return boolean
     */
    public static boolean hasPermissions(Context context, String[] permissions) {
        int result;
        for (final String perms : permissions) {
            result = ContextCompat.checkSelfPermission(context, perms);
            if (result == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Float a dialog box requesting a permission with a text given.
     * @param context
     * @param fragmentManager
     * @param msg
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void showDialogForPermission(Context context, final FragmentManager fragmentManager, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                fragmentManager.popBackStack();
            }
        });
        builder.create().show();
    }
}
