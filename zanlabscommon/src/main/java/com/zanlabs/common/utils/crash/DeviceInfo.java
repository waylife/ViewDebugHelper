package com.zanlabs.common.utils.crash;

/**
 * Created by link on 2015/11/3.
 */
public class DeviceInfo {
    public String uuid;
    public String imei;
    public String currentVersion;
    public String versionCode;
    public String resolution;
    public String channel;
    public String packageName;
    public String sdkVersion;
    public String ua;
    public String model;
    public String manufacturer;

    public String net;

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "uuid='" + uuid + '\'' +
                ", imei='" + imei + '\'' +
                ", currentVersion='" + currentVersion + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", resolution='" + resolution + '\'' +
                ", channel='" + channel + '\'' +
                ", packageName='" + packageName + '\'' +
                ", sdkVersion='" + sdkVersion + '\'' +
                ", ua='" + ua + '\'' +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", net='" + net + '\'' +
                '}';
    }
}
