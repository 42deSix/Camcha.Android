package layout;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.softmilktea.camcha.DetectionActivity;
import com.softmilktea.camcha.R;

/**
 * Created by SEJIN on 2017-10-08.
 */

public class GpsPlzFragment extends Fragment {
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        mContext = GpsPlzFragment.super.getContext();
        LocationManager locationManager = (LocationManager) mContext.getSystemService(getContext().LOCATION_SERVICE);

        // Start DetectionActivity if all permissions are granted
        if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER) && ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(GpsPlzFragment.super.getContext(), DetectionActivity.class));
            backToMainFragment(transaction);
            return null;
        }
        // Make view requesting the location permission.
        else {
            View view = inflater.inflate(R.layout.view_gps_plz, container, false);

            Button agreeButton = (Button) view.findViewById(R.id.gps_plz_agree_button);
            Button disagreeButton = (Button) view.findViewById(R.id.gps_plz_disagree_button);

            agreeButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(GpsPlzFragment.super.getContext(), DetectionActivity.class));
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    // check the permission
                    if (Build.VERSION.SDK_INT >= 23 &&
                            ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(GpsPlzFragment.super.getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    }
                    // relocate the back button
                    backToMainFragment(transaction);
                }
            });
            disagreeButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(GpsPlzFragment.super.getContext(), DetectionActivity.class));
                    backToMainFragment(transaction);
                }
            });

            return view;
        }
    }

    /**
     * To let users encounter MainFragment insead of GpsPlzFragment when they press back button in DetectionActivity.
     * @author Sejin Jeon
     * @param transaction
     */
    public void backToMainFragment(FragmentTransaction transaction) {
        transaction.replace(R.id.root_view, new MainFragment());
        transaction.commit();
    }

}
