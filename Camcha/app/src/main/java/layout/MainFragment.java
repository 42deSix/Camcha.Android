package layout;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softmilktea.camcha.BaseApplication;
import com.softmilktea.camcha.R;

/**
 * Created by SEJIN on 2017-10-05.
 */

public class MainFragment extends Fragment {

    private String[] mPermissions = {"android.permission.CAMERA"};

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
                            if (!BaseApplication.hasPermissions(getActivity(), mPermissions)) {
                                requestPermissions(mPermissions, BaseApplication.PERMISSIONS_REQUEST_CAMERA);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case BaseApplication.PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraPermissionAccepted = (grantResults[0] == PackageManager.PERMISSION_GRANTED);

                    if (!cameraPermissionAccepted) {
                        BaseApplication.showDialogForPermission(getActivity(), getFragmentManager(), "탐지를 위해서는 카메라에 대한 접근권한이 필요합니다.\n설정 > 애플리케이션 관리자 > Camcha > 앱 권한에 들어가서 카메라 권한을 켜주세요.");
                    }
                }
                break;
        }
    }
}
