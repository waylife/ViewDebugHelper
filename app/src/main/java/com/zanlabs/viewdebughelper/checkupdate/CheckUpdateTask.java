package com.zanlabs.viewdebughelper.checkupdate;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;

import com.zanlabs.common.utils.AppUtil;
import com.zanlabs.common.utils.SafeUtil;
import com.zanlabs.common.utils.log.XLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by rxread on 8/2/16.
 */
public class CheckUpdateTask extends AsyncTask<String, String, CheckUpdateUtil.CheckUpdateItem> {
    private Context mContext;

    private CheckUpdateUtil.OnCheckUpdateListener mOnCheckUpdateListener;
    public static final String UPDATE_URL = "https://api.github.com/repos/waylife/ViewDebugHelper/releases/latest";
    private static final boolean DEBUG = true;

    public CheckUpdateTask(Context context) {
        this.mContext = context;
    }

    public static void log(String msg) {
        XLog.i("CheckUpdateTask", msg);
    }

    public void setOnCheckUpdateListener(CheckUpdateUtil.OnCheckUpdateListener onCheckUpdateListener) {
        this.mOnCheckUpdateListener = onCheckUpdateListener;
    }

    @Override
    protected CheckUpdateUtil.CheckUpdateItem doInBackground(String... uri) {
        disableConnectionReuseIfNecessary();
        InputStream in = null;
        try {
            //a simple solution, lots details not handled
            URL url = new URL(UPDATE_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                in = urlConnection.getInputStream();
                String result = inputStream2String(in);
                return parseResult(mContext, result);
            }
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(CheckUpdateUtil.CheckUpdateItem result) {
        super.onPostExecute(result);
        if (result == null) {
            result = getFailedItem();
        }
        if (mOnCheckUpdateListener != null) {
            if (result.resultCode == CheckUpdateUtil.CheckUpdateItem.RESULT_SUCCESS) {
                mOnCheckUpdateListener.onSuccess(result);
            } else {
                mOnCheckUpdateListener.onFailed(result);
            }
            log(String.format("checkupdate result: hashUpdate=%s,item data=%s", result.hasUpdate(), result));
        }
    }


    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    private CheckUpdateUtil.CheckUpdateItem getFailedItem() {
        CheckUpdateUtil.CheckUpdateItem item = new CheckUpdateUtil.CheckUpdateItem();
        item.resultCode = CheckUpdateUtil.CheckUpdateItem.RESULT_FAIL;
        return item;
    }

    private CheckUpdateUtil.CheckUpdateItem parseResult(Context context, String result) {
        CheckUpdateUtil.CheckUpdateItem item = new CheckUpdateUtil.CheckUpdateItem();
        if (!TextUtils.isEmpty(result)) {
            try {
                JSONObject releaseJson = new JSONObject(result);
                //version code and version name are hiding in tag_name, as tag_name format is always like "xxxx#VersionCode#VersionName"
                String latestTag = releaseJson.optString("tag_name");
                int serverVersionCode = 0;
                String serverVersionName = "";
                boolean isPreRelease = releaseJson.optBoolean("prerelease");
                String body = releaseJson.optString("body");
                JSONArray assetsJsonArray = releaseJson.optJSONArray("assets");
                String downloadUrl = "";
                if (assetsJsonArray != null && assetsJsonArray.length() > 0) {
                    JSONObject assetJsonObject = assetsJsonArray.getJSONObject(0);
                    if (assetJsonObject != null) {
                        downloadUrl = assetJsonObject.optString("browser_download_url");
                        //"VDH-${defaultConfig.versionName}-${defaultConfig.versionCode}" + time + ".apk"
                        String name = assetJsonObject.optString("name");
                        if (!TextUtils.isEmpty(name)) {
                            String arr[] = name.split("-");
                            if (arr.length >= 3) {
                                serverVersionName = arr[1];
                                serverVersionCode = SafeUtil.toInt(arr[2], 0);
                            }
                        }
                    }
                }

                item.isAlphaVersion = isPreRelease;
                item.currentVersionCode = AppUtil.getVersionCode(context);
                item.currentVersionName = AppUtil.getVersionName(context);
                item.updateBody = body;
                item.serverVersionCode = serverVersionCode;
                item.serverVersionName = serverVersionName;
                item.downloadUrl = downloadUrl;
                item.resultCode = CheckUpdateUtil.CheckUpdateItem.RESULT_SUCCESS;
            } catch (JSONException e) {
                item.resultCode = CheckUpdateUtil.CheckUpdateItem.RESULT_FAIL;
            }
        } else {
            item.resultCode = CheckUpdateUtil.CheckUpdateItem.RESULT_FAIL;
        }
        XLog.i("CheckUpdate", "item=" + (item != null ? item.toString() : "null"));
        return item;
    }

    private void disableConnectionReuseIfNecessary() {
        // Work around pre-Froyo bugs in HTTP connection reuse.
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
        }
    }
}