package com.zanlabs.common.utils.crash;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by link on 2015/5/7.
 */
public class CrashHelper {

    public static void goCrash(Context context){
        int exception=2-2;
        int a=3/exception;
        Toast.makeText(context, "GO EXCEPTION" + a, Toast.LENGTH_SHORT).show();
    }
}
