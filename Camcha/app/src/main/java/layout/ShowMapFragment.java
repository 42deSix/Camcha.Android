package layout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.softmilktea.camcha.BaseApplication;
import com.softmilktea.camcha.CamchaMapOverlay;
import com.softmilktea.camcha.Dlog;
import com.softmilktea.camcha.R;
import com.softmilktea.camcha.ReceiveFromServerAsync;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by SEJIN on 2017-10-23.
 */

public class ShowMapFragment extends Fragment {
    private NMapContext mMapContext;
    private final int MARKER_SIZE = (int)(40 * BaseApplication.DP);
    private LocationListener mLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
//            mDetectionResult = new DetectionResult("test", Double.toString(location.getLatitude()), Double.toString(location.getLongitude()));    // "Samsung phones have this problem." - https://stackoverflow.com/questions/14700755/locationmanager-requestlocationupdates-not-working
        }
        public void onProviderDisabled(String provider) {}
        public void onProviderEnabled(String provider) {}
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_map, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapContext =  new NMapContext(super.getActivity());
        mMapContext.onCreate();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /* get current location */
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        NGeoPoint currentCoord = null;
        if ( ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(locationManager.GPS_PROVIDER)) {
                List<String> providers = locationManager.getProviders(true);
                for (String provider : providers) {
                    locationManager.requestLocationUpdates(provider, 0, 0, mLocationListener);
                    if (provider != null) {
                        Location location = locationManager.getLastKnownLocation(provider);
                        if (location != null) {
                            currentCoord = new NGeoPoint(location.getLongitude(), location.getLatitude());
                        }
                    }
                }
            }
        }

        /* set mapView */
        NMapView mapView = (NMapView) getView().findViewById(R.id.map_view);
        mapView.setClientId(BaseApplication.CLIENT_ID);
        mapView.setClickable(true);
        mMapContext.setupMapView(mapView);
        if(currentCoord != null) {
            mapView.getMapController().setMapCenter(currentCoord);
        }

        /* locate markers */
        List<CamchaGeoPoint> coordList = receiveCoords();
        for(CamchaGeoPoint camchaGeoPoint : coordList) {
            Drawable marker = getResources().getDrawable(R.drawable.icon_red_marker, getActivity().getTheme());
            marker.setBounds(0, 0, MARKER_SIZE, MARKER_SIZE);
            CamchaMapOverlay hotPlace = new CamchaMapOverlay(marker, camchaGeoPoint.getmGeoPoint(), mapView);
            mapView.getOverlays().add(hotPlace);
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        mMapContext.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapContext.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mMapContext.onPause();
    }
    @Override
    public void onStop() {
        mMapContext.onStop();
        super.onStop();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @Override
    public void onDestroy() {
        mMapContext.onDestroy();
        super.onDestroy();
    }

    /**
     * Get all coords in json format from DB and mark them on the Naver map.
     * @author Sejin Jeon
     */
    public List<CamchaGeoPoint> receiveCoords() {
        String coordJsonStringChunk = null;
        try {
            coordJsonStringChunk = new ReceiveFromServerAsync(BaseApplication.QUERY_LIST[1], getContext()).execute().get();  // get() locks UI thread with sleep() and get return data from doInBackground()
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JsonArray coordJsonStringArray = new JsonParser().parse(coordJsonStringChunk).getAsJsonArray();
        List<CamchaGeoPoint> coordList = new LinkedList<>();
        for(JsonElement coordJsonElement : coordJsonStringArray) {
            Dlog.e(coordJsonElement.toString());
            JsonObject coordJsonObject = coordJsonElement.getAsJsonObject();
            String deviceId = coordJsonObject.get("device_id").getAsString();
            String lng = coordJsonObject.get("lng").getAsString();              // 여기 double로 바꿔달라고
            String lat = coordJsonObject.get("lat").getAsString();              // 희탁이한테 얘기해야겠음. 예외 극혐임.
            String created = coordJsonObject.get("created").getAsString();
            String policeReport = coordJsonObject.get("police_report").getAsString();
            if(!lng.equals("") && !lat.equals("")) {
                NGeoPoint nGeoPoint = new NGeoPoint(Double.parseDouble(lng), Double.parseDouble(lat));
                CamchaGeoPoint camchaGeoPoint = new CamchaGeoPoint(deviceId, nGeoPoint, created, policeReport);
                coordList.add(camchaGeoPoint);
            }
        }
        return coordList;
    }

    private class CamchaGeoPoint {
        String mDeviceId;
        String mCreated;
        String mPoliceReport;
        NGeoPoint mGeoPoint;

        public CamchaGeoPoint(String deviceId, NGeoPoint geoPoint, String created, String policeReport) {
            this.mDeviceId = deviceId;
            this.mCreated = created;
            this.mGeoPoint = geoPoint;
            this.mPoliceReport = policeReport;

        }
        public String getmDeviceId() {
            return mDeviceId;
        }

        public void setmDeviceId(String mDeviceId) {
            this.mDeviceId = mDeviceId;
        }

        public NGeoPoint getmGeoPoint() {
            return mGeoPoint;
        }

        public void setmGeoPoint(NGeoPoint mGeoPoint) {
            this.mGeoPoint = mGeoPoint;
        }

        public String getmPoliceReport() {
            return mPoliceReport;
        }

        public void setmPoliceReport(String mPoliceReport) {
            this.mPoliceReport = mPoliceReport;
        }

        public String getmCreated() {
            return mCreated;
        }

        public void setmCreated(String mCreated) {
            this.mCreated = mCreated;
        }
    }
}
