/**
 *
 */
package com.zanlabs.common.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * @author rxread
 */
public class AppUtil {

    private static String imei = null;

    /**
     * get the current version of current application
     */
    public static String getImei( Context context) {
        if (TextUtils.isEmpty(imei)) {
            if(context.checkCallingOrSelfPermission( Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED){
                TelephonyManager tm = (TelephonyManager) context.getApplicationContext()
                        .getSystemService(Context.TELEPHONY_SERVICE);
                imei = tm.getDeviceId();
            }
        }
        return imei;
    }

    /**
     * get the current version of current application
     */
    public static String getVersionName( Context context) {
        String versionName = "";
        String pkName = context.getPackageName();
        try {
            versionName = context.getPackageManager().getPackageInfo(pkName, 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * get the current version code of current application
     */
    public static int getVersionCode( Context context) {
        int versionCode = -1;
        String pkName = context.getPackageName();
        try {
            versionCode = context.getPackageManager().getPackageInfo(pkName, 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * get the screen resolution
     *
     */
    public static String getScreenResolution( Context context,String splitStr) {
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return String.valueOf(width)+splitStr+String.valueOf(height);
    }


    /**
     * 获得相应的版本号<br/>
     * <a href="http://developer.android.com/reference/android/os/Build.VERSION_CODES.html">SDK INT</a>
     */
    public static String getCorrectSDKVer(int version) {
        switch (version) {
            case 1:
            case 2:
                return "Android1.5Before";
            case 3:
                return "Android1.5";
            case 4:
                return "Android1.6";
            case 5:
                return "Android2.0";
            case 6:
                return "Android2.0.1";
            case 7:
                return "Android2.1";
            case 8:
                return "Android2.2";
            case 9:
                return "Android2.3.1";
            case 10:
                return "Android2.3.3";
            case 11:
                return "Android3.0";
            case 12:
                return "Android3.1";
            case 13:
                return "Android3.2";
            case 14:
                return "Android4.0";
            case 15:
                return "Android4.0.3";
            case 16:
                return "Android4.1.2";
            case 17:
                return "Android4.2.2";
            case 18:
                return "Android4.3.1";
            case 19:
                return "Android4.4.2";
            case 20://KITKAT_WATCH
                return "Android4.4W";
            case 21:
                return "Android5.0.1";
            case 22:
                return "Android5.1.1";
            case 23:
                return "Android6.0";
            case 24:
                return "AndroidAPI24";
            case 25:
                return "AndroidAPI25";
        }
        return "AndroidAPI" + version;
    }

    public static int getSDKInt() {
        return Build.VERSION.SDK_INT;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
//			e.printStackTrace();
        }
        return packageInfo!=null;
    }
}