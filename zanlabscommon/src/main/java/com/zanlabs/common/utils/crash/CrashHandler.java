package com.zanlabs.common.utils.crash;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.zanlabs.common.utils.AppUtil;
import com.zanlabs.common.utils.ChannelUtil;
import com.zanlabs.common.utils.SdCardUtils;
import com.zanlabs.common.utils.UUIDUtil;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Calendar;
import java.util.List;

public class CrashHandler implements UncaughtExceptionHandler {
    private static CrashHandler myCrashHandler;
    private Context mContext;

    private CrashHandler() {

    }

    public static synchronized CrashHandler getInstance() {
        if (myCrashHandler != null) {
            return myCrashHandler;
        } else {
            myCrashHandler = new CrashHandler();
            return myCrashHandler;
        }
    }

    public void init(Context context) {
        mContext = context;
    }


    DeviceInfo getDeviceInfo() {
        DeviceInfo deviceInfo;
        deviceInfo = new DeviceInfo();
        deviceInfo.currentVersion = AppUtil.getVersionName(mContext);
        deviceInfo.imei = AppUtil.getImei(mContext);
        deviceInfo.uuid = UUIDUtil.getUUID(mContext);
        deviceInfo.versionCode = String.valueOf(AppUtil.getVersionCode(mContext));
        deviceInfo.resolution = AppUtil.getScreenResolution(mContext, "*");
        deviceInfo.sdkVersion = AppUtil.getCorrectSDKVer(android.os.Build.VERSION.SDK_INT);
        deviceInfo.channel = ChannelUtil.getChannelId(mContext);
        deviceInfo.packageName = mContext.getPackageName();
        deviceInfo.model = Build.MODEL;
        deviceInfo.manufacturer = Build.MANUFACTURER;
        return deviceInfo;
    }

    @Override
    public void uncaughtException(Thread arg0, Throwable exception) {
        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));
        String threadState = arg0 != null ? arg0.getName() + "/" + arg0.getId() : "thread=null";

        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("time:").append(Calendar.getInstance().toString()).append("\n");
        stringBuilder.append("process:").append(getProcessName(mContext)).append("\n");
        stringBuilder.append("thread(name/id):").append(threadState).append("\n");
        stringBuilder.append("device information:").append(getDeviceInfo().toString()).append("\n");
        stringBuilder.append("stackTrace:").append("\n");
        stringBuilder.append(stackTrace.toString());
        String errorLog = stringBuilder.toString();

        //保存崩溃日志到本地
        saveErrorLog(errorLog);
        ActivityCrashReport.actionReport(mContext, errorLog);
    }


    private void saveErrorLog(final String errorLog) {
        new Thread() {
            public void run() {
                try {
                    String logFileFolder=getCrashFileFolder(mContext);
                    //ensure that folder exist
                    ensureFileDirectoryExists(logFileFolder);
                    String logFile = logFileFolder+"/dobluetoothcrashlog.txt";
                    writeToFile(logFile, errorLog);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
                System.exit(1);
            }
        }.start();

    }


    public static void writeToFile(String fileName, String msg) throws IOException {
        File file=new File(fileName);
        if(file.exists()&&file.length()>1024*1024*5){//>5M
            file.delete();
        }
        if(!file.exists())
            file.createNewFile();
        RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
        long fileLength = randomFile.length();
        randomFile.seek(fileLength);
        randomFile.write(msg.getBytes());
        randomFile.close();
    }


    private String getProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> runningProcess = am.getRunningAppProcesses();
            StringBuilder stringBuilder = new StringBuilder();
            int size = runningProcess.size();
            for (int i = 0; i < size; i++) {
                ActivityManager.RunningAppProcessInfo info = runningProcess.get(i);
                if (info.pid == pid) {
                    stringBuilder.append(info.processName);
                }
            }
            return stringBuilder.toString();
        } catch (Exception e) {
        }
        return "";
    }


    public static void ensureFileDirectoryExists(String folderPath){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File filePath=new File(folderPath);
            if(filePath.isFile()){
                filePath.delete();
            }
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
        }
    }

    public static String getCrashFileFolder(Context context) {
        String basePath = SdCardUtils.getSDCardPath();
        if (!SdCardUtils.isSdCardAvailable()) {//sd card does not exist
            basePath = context.getCacheDir().getAbsolutePath();
        }
        return basePath  + "log";
    }
}
