package com.zanlabs.viewdebughelper.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.zanlabs.viewdebughelper.R;
import com.zanlabs.viewdebughelper.ViewDebugHelperApplication;
import com.zanlabs.viewdebughelper.checkupdate.CheckUpdateUtil;
import com.zanlabs.viewdebughelper.ui.base.DispatchActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by rxread on 8/3/16.
 */
public class Notifier {
    private static Notifier instance = null;

    private NotificationManager notificationManager;

    private static Object INSTANCE_LOCK = new Object();

    public static final int TYPE_APP_UPDATE = 1;

    private static final String TAG = "Notifier";

    Intent mLauncherIntent = null;
//    Notification notification = null;


    public static Notifier getInstance() {
        if (instance == null)
            synchronized (INSTANCE_LOCK) {
                if (instance == null) {
                    instance = new Notifier();
                }
            }
        return instance;
    }

    private Notifier() {
        this.notificationManager = (NotificationManager) ViewDebugHelperApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void cleanAll() {
        if (notificationManager != null) {
            notificationManager.cancelAll();
        }
    }

    public void cancelByType(int type) {
        if (notificationManager != null) {
            notificationManager.cancel(type);
        }
    }

    public void notifyAppUpdate(CheckUpdateUtil.CheckUpdateItem item) {
        if (item.hasUpdate()) {
            notify(TYPE_APP_UPDATE, String.format("卧槽，发现新版本:%s", item.serverVersionName), "赶紧点此更新下载最新版吧！！！", true, item);
        }
    }

    /**
     */
    public void notify(int type, String title, String message, boolean canClear, Object extra) {
        try {
            Context context = ViewDebugHelperApplication.getInstance();
            Intent intent = new Intent();
            PendingIntent pendingIntent = null;

            switch (type) {
                case TYPE_APP_UPDATE:
                    intent.setClass(context, DispatchActivity.class);
                    intent.putExtra(DispatchActivity.S_TYPE_STRING, DispatchActivity.TYPE_CHECK_UPDATE);
                    if (extra instanceof CheckUpdateUtil.CheckUpdateItem) {
                        intent.putExtra(DispatchActivity.S_DISPATCH_DATA, (CheckUpdateUtil.CheckUpdateItem) extra);
                    }
                    pendingIntent = PendingIntent.getActivity(context, TYPE_APP_UPDATE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    break;
            }

            if (pendingIntent != null) {
                Notification nt = null;

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    nt = new Notification();
                    try {//setLatestEventInfo is not available in M, fuck!!!
                        Method deprecatedMethod = nt.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
                        deprecatedMethod.invoke(nt, context, title, message, pendingIntent);
                    } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
                            | InvocationTargetException e) {
                        Log.w(TAG, "Method not found", e);
                    }
                } else {
                    Notification.Builder builder = new Notification.Builder(context)
                            .setContentIntent(pendingIntent)
                            .setContentTitle(title)
                            .setContentText(message);
                    nt = builder.build();
                }

                nt.icon = R.mipmap.ic_launcher;
                nt.tickerText = title;
                nt.when = System.currentTimeMillis();

                if (canClear) {
                    nt.flags |= Notification.FLAG_AUTO_CANCEL;
                } else {
                    nt.flags |= Notification.FLAG_NO_CLEAR;
                }
                notificationManager.notify(type, nt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
