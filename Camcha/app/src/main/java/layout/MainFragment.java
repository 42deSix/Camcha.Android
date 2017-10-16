package layout;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softmilktea.camcha.R;

/**
 * Created by SEJIN on 2017-10-05.
 */

public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        AppCompatButton detectionButton = (AppCompatButton) view.findViewById(R.id.detection_button);
        AppCompatButton showMapButton = (AppCompatButton) view.findViewById(R.id.show_map_button);
        detectionButton.setOnClickListener(
                new AppCompatButton.OnClickListener() {
                    public void onClick(View v) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            //퍼미션 상태 확인
                            if (!hasPermissions(PERMISSIONS)) {
                                //퍼미션 허가 안되어있다면 사용자에게 요청
                                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CAMERA);
                            }
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.replace(R.id.root_view, new GpsPlzFragment());
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.addToBackStack(null);
                            transaction.commit();
                        }
                    }
                }
        );
        showMapButton.setOnClickListener(
                new AppCompatButton.OnClickListener() {
                    public void onClick(View v) {
//                        Log.e(TAG, "Show me the map");
////                        startActivity(new Intent(MainFragment.super.getContext(), ShowMapActivity.class));
//                        if(transaction != null) {
//                            transaction.replace(R.id.root_view, new ShowMapFragment());
//                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                            transaction.addToBackStack(null);
//                            transaction.commit();
//                        }
                    }
                }
        );
        return view;
    }


    //여기서부턴 퍼미션 관련 메소드
    private final int PERMISSIONS_REQUEST_CAMERA = 1;
    String[] PERMISSIONS = {"android.permission.CAMERA"};


    private boolean hasPermissions(String[] permissions) {
        int result;
        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (final String perms : permissions) {
            result = ContextCompat.checkSelfPermission(getActivity(), perms);
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
            case PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraPermissionAccepted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);

                    if (!cameraPermissionAccepted) {
                        showDialogForPermission("탐지를 위해서는 카메라에 대한 접근권한이 필요합니다.\n설정 > 애플리케이션 관리자 > Camcha > 앱 권한에 들어가서 카메라 권한을 켜주세요.");
                    }
                }
                break;
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    private void showDialogForPermission(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("알림");
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        builder.create().show();
    }

}
