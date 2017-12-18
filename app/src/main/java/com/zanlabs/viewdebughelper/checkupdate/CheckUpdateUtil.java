package com.zanlabs.viewdebughelper.checkupdate;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by rxread on 8/2/16.
 */
public class CheckUpdateUtil {
    public static class CheckUpdateItem implements Serializable{
        public final static int RESULT_SUCCESS = 1;
        public final static int RESULT_FAIL = 2;
        private boolean hasUpdate;
        public boolean isAlphaVersion;
        public int currentVersionCode;
        public String currentVersionName;
        public int serverVersionCode;
        public String serverVersionName;
        public String updateBody;
        public String downloadUrl;

        public int resultCode;
        public int resultMessage;

        public boolean hasUpdate() {
            hasUpdate = (serverVersionCode > 0 && serverVersionCode > currentVersionCode);
            return hasUpdate;
        }

        @Override
        public String toString() {
            return "CheckUpdateItem{" +
                    "hasUpdate=" + hasUpdate +
                    ", isAlphaVersion=" + isAlphaVersion +
                    ", currentVersionCode=" + currentVersionCode +
                    ", currentVersionName='" + currentVersionName + '\'' +
                    ", serverVersionCode=" + serverVersionCode +
                    ", serverVersionName='" + serverVersionName + '\'' +
                    ", updateBody='" + updateBody + '\'' +
                    ", downloadUrl='" + downloadUrl + '\'' +
                    ", resultCode=" + resultCode +
                    ", resultMessage=" + resultMessage +
                    '}';
        }
    }

    public static void checkUpdate(Context context, OnCheckUpdateListener listener) {
        CheckUpdateTask task = new CheckUpdateTask(context);
        task.setOnCheckUpdateListener(listener);
        task.execute();
    }

    public interface OnCheckUpdateListener {
        public void onSuccess(CheckUpdateItem item);

        public void onFailed(CheckUpdateItem item);
    }
}
