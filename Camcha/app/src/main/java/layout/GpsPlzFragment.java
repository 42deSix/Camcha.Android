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
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.softmilktea.camcha.DetectionActivity;
import com.softmilktea.camcha.R;

/**
 * Created by SEJIN on 2017-10-08.
 */

public class GpsPlzFragment extends Fragment {
    private final String TAG = "GpsPlzFragment";
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Inflate the layout for this fragment */
        View view = inflater.inflate(R.layout.view_gps_plz, container, false);
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        mContext = GpsPlzFragment.super.getContext();
        LocationManager locationManager = (LocationManager) mContext.getSystemService(getContext().LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER) && ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startActivity(new Intent(GpsPlzFragment.super.getContext(), DetectionActivity.class));
            backToMainFragment(transaction);
        }
        else {
            AppCompatButton agreeButton = (AppCompatButton) view.findViewById(R.id.gps_plz_agree_button);
            AppCompatButton disagreeButton = (AppCompatButton) view.findViewById(R.id.gps_plz_disagree_button);

            agreeButton.setOnClickListener(new AppCompatButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(GpsPlzFragment.super.getContext(), DetectionActivity.class));
                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    if (Build.VERSION.SDK_INT >= 23 &&
                            ContextCompat.checkSelfPermission(mContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(GpsPlzFragment.super.getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                    }
                    backToMainFragment(transaction);
                }
            });
            disagreeButton.setOnClickListener(new AppCompatButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(GpsPlzFragment.super.getContext(), DetectionActivity.class));
                    backToMainFragment(transaction);
                }
            });
        }

        return view;
    }

    // To let users encounter MainFragment insead of GpsPlzFragment when they press back button in DetectionActivity.
    public void backToMainFragment(FragmentTransaction transaction) {
        transaction.replace(R.id.root_view, new MainFragment());
        transaction.commit();
    }

}
