package com.zanlabs.common.utils;

import com.zanlabs.common.utils.log.XLog;

/**
 * Created by rxread on 20/12/2017.
 */

public class SafeUtil {
    private final static String TAG = "SafeUtil";

    public static int toInt(String value, int defaultValue) {
        int result = 0;
        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException ne) {
            result = defaultValue;
            XLog.e("SafeUtil", "toInt error," + ne.getMessage());
        }
        return result;
    }
}
