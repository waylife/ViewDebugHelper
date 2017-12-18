package com.zanlabs.common.utils;

import android.content.Context;
import android.widget.Toast;

import com.zanlabs.common.R;

/**
 * Created by rxread on 12/11/15.
 */
public class T {
    public static void s(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void l(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void s(Context context, int resId) {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
    }

    public static void l(Context context, int resId) {
        Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show();
    }
}
