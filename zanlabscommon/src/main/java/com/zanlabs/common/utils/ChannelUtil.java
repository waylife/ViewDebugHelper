package com.zanlabs.common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ChannelUtil {
    private final static String CHANNEL_FILE_NAME = "channel";

    /**
     * get the path  data from local file<br/>
     */
    public static String getChannelId(Context context) {
        return getChannelFromManifest(context);
    }

    /**
     * 从AndroidManifest.xml中获取UMENG_CHANNEL
     */
    public static String getChannelFromAssets(Context context) {
        String channel = "DefaultChannel";
        AssetManager assetManager = null;
        InputStreamReader inputReader = null;
        BufferedReader bufReader = null;
        try {
            assetManager = context.getAssets();
            inputReader = new InputStreamReader(assetManager.open(CHANNEL_FILE_NAME));
            bufReader = new BufferedReader(inputReader);
            String line = "";
            //read the first line
            while ((line = bufReader.readLine()) != null) {
                line = line.trim();
                if (TextUtils.isEmpty(line)) {//empty line
                    continue;
                }
                if (line.startsWith("#")) {//Comments, ignore
                    continue;
                }
                channel = line;
                break;
            }
        } catch (Exception e) {
        } finally {
            if (bufReader != null) {
                try {
                    bufReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return channel;
    }

    /**
     * 从AndroidManifest.xml中获取UMENG_CHANNEL
     */
    public static String getChannelFromManifest(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null)
                return applicationInfo.metaData.getString("UMENG_CHANNEL");
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "DefaultChannel";
    }
}
