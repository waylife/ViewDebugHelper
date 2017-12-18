package com.zanlabs.common.utils.log;

import android.util.Log;

import java.util.HashSet;

/**
 * log管理类，方便正式上线时不展示log,如果希望上线时显示log，请使用系统自带的Log类
 */
public class XLog {
    /**
     * 非线上环境会显示log
     */
    private final static boolean showLog = true;
    /**
     * 默认tag，默认值为appmall
     */
    private static String defaultTag = "XLog";
    private static final String SEPERATE = "=========";
    private static final HashSet<String> ALLOW_TAGS = new HashSet<String>();

    static {
        //请添加小写的，过滤时不区分大小写
        ALLOW_TAGS.add("tag");
        ALLOW_TAGS.add(defaultTag.toLowerCase());
    }


    /**
     * 判断tag是否是被允许的
     */
    public final static boolean isTagAllow(String tag) {
        return true;
//        if (tag == null)
//            return false;
//        return ALLOW_TAGS.contains(tag.toLowerCase());
    }

    private final static boolean isShowLog(String tag) {
        return showLog && isTagAllow(tag);
    }

    /**
     * 默认tag为{@link #defaultTag}
     */
    public static void v(String msg) {
        v(defaultTag, msg);
    }

    public static void v(String tag, String msg) {
        if (isShowLog(tag))
            Log.v(tag, msg);
    }

    /**
     * 默认tag为{@link #defaultTag}
     */
    public static void d(String msg) {
        d(defaultTag, msg);
    }

    public static void d(String tag, String msg) {
        if (isShowLog(tag))
            Log.d(tag, msg);
    }


    /**
     * 默认tag为{@link #defaultTag}
     */
    public static void i(String msg) {
        i(defaultTag, msg);
    }

    public static void i(String tag, String msg) {
        if (isShowLog(tag))
            Log.i(tag, msg);
    }

    /**
     * 默认tag为{@link #defaultTag}
     */
    public static void w(String msg) {
        w(defaultTag, msg);
    }

    public static void w(String tag, String msg) {
        if (isShowLog(tag))
            Log.w(tag, msg);
    }

    /**
     * 默认tag为{@link #defaultTag}
     */
    public static void e(String msg) {
        e(defaultTag, msg);
    }

    public static void e(String tag, String msg) {
        if (isShowLog(tag))
            Log.e(tag, msg);
    }


    public static void w(String tag, Throwable e) {
        if (showLog) {
            Log.w(tag, e);
        }
    }

    public static void w(String tag, String msg, Throwable e) {
        if (showLog) {
            Log.w(tag, msg, e);
        }
    }

    public static void e(Throwable e) {
        if (showLog)
            e.printStackTrace();
    }

    public static void e(String tag, Throwable e) {
        if (showLog) {
            Log.e(tag, SEPERATE + e.getClass().getSimpleName() + SEPERATE, e);
        }
    }

    public static void e(String tag, String msg, Throwable e) {
        if (showLog) {
            Log.e(tag, msg, e);
        }
    }
}
