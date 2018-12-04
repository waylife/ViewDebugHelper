package com.zanlabs.viewdebughelper.core;

import android.text.TextUtils;

import com.zanlabs.common.utils.log.XLog;

import java.util.LinkedList;
import java.util.Queue;

public class ActivityStackManager {

    private static ActivityStackManager sInstance;
    private static Object sLock = new Object();
    public static final String TAG = "ActivityStackManager";

    Queue<String> mActivityStack = new LinkedList<>();
    String mTopActivityName;


    private ActivityStackManager() {

    }

    public final static int MAX_STACK_SIZE = 300;

    public static ActivityStackManager getInstance() {
        if (sInstance == null) {
            synchronized (sLock) {
                if (sInstance == null) {
                    sInstance = new ActivityStackManager();
                }
            }
        }
        return sInstance;
    }

    public void offer(String s) {
        if (TextUtils.isEmpty(s)) {
            return;
        }
        if (TextUtils.equals(s, mTopActivityName)) {
            return;
        }
        mTopActivityName = s;
        XLog.e(TAG, "add activity:" + s);
        if (mActivityStack.size() >= MAX_STACK_SIZE) {
            mActivityStack.poll();
        }
        mActivityStack.offer(s);
    }


    public void clear() {
        mActivityStack.clear();
    }

}
