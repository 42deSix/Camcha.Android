package layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.nhn.android.maps.NMapContext;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;
import com.softmilktea.camcha.BaseApplication;
import com.softmilktea.camcha.Dlog;
import com.softmilktea.camcha.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by SEJIN on 2017-10-23.
 */

public class ShowMapFragment extends Fragment {
    private NMapContext mMapContext;
    private static final String CLIENT_ID = "BR56NxCjsDHKMVGf0WyJ"; // Naver API application client id

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
        NMapView mapView = (NMapView) getView().findViewById(R.id.map_view);
        mapView.setClientId(CLIENT_ID);
        mMapContext.setupMapView(mapView);
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

    public void setCoordsOnMap() {
        String coordJsonString = BaseApplication.RESPONSE_DATA.get(BaseApplication.QUERY_LIST[1]);
        Dlog.e(coordJsonString);
        Gson gson = new Gson();

        /**
         * NGeoPoint랑 값들을 맞춰주는 게 좋을 듯 하다.
         */

        List<NGeoPoint> coordList = new LinkedList<>();
//        List<String> tmp = new LinkedList<>();
//        gson.fromJson(coordJsonString, new TypeToken<List<String>>(){}.getType());
//        String tmpStr = tmp.get(0);
//        Dlog.e(tmpStr);
    }
}
