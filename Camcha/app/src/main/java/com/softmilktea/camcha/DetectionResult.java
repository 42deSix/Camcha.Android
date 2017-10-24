package com.softmilktea.camcha;

/**
 * Created by SEJIN on 2017-10-20.
 */

public class DetectionResult {
    private String device_id;
    private String lat;
    private String lng;
//    private String police_report;

    public DetectionResult(String deviceId, String lat, String lng) {
        this.device_id = deviceId;
        this.lat = lat;
        this.lng = lng;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
