package com.softmilktea.camcha;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.nhn.android.maps.NMapItemizedOverlay;
import com.nhn.android.maps.NMapOverlayItem;
import com.nhn.android.maps.NMapView;
import com.nhn.android.maps.maplib.NGeoPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEJIN on 2017-10-24.
 */

public class CamchaMapOverlay extends NMapItemizedOverlay {
    private List<NMapOverlayItem> mLocations = new ArrayList<>();
    private Drawable mMarker;
    private NMapView mMapView;

    public CamchaMapOverlay(Drawable marker, NGeoPoint geoPoint, NMapView mapView) {
        super(marker);
        this.mMarker = marker;
        this.mMapView = mapView;
        mMapView.getMapController().setMapCenter(geoPoint);
        mMapView.getMapController().setZoomLevel(19);
        mLocations.add(new NMapOverlayItem(geoPoint, "test", "test", marker));
        populate();
    }

    @Override
    public void draw(Canvas canvas, NMapView mapView, boolean shadow) {
        super.draw(canvas, mapView, shadow);
//        boundCenterBottom(mMarker);
    }

    @Override
    protected NMapOverlayItem createItem(int i) {
        return mLocations.get(i);
    }

    @Override
    public int size() {
        return mLocations.size();
    }

    public void addOverlay(NMapOverlayItem overlay) {
        mLocations.add(overlay);
        setLastFocusedIndex(-1);
        populate();
    }

    public void clear() {
        mLocations.clear();
        mMapView.removeAllViews();
        setLastFocusedIndex(-1);
        populate();
    }
}
