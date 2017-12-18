package com.zanlabs.viewdebughelper.xpose.ipc;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.zanlabs.viewdebughelper.ViewDebugHelperApplication;

/**
 * Created by rxread on 11/22/15.
 */
public class XPoseService extends Service {
    public final static String ACTION="XPOSE_ACTION";
    public final static int ACTION_CLEAR_FRAGMENTS= 0x1;
    public final static int ACTION_ADD_FRAGMENT=0x2;

    public final static String EXTRA_CURRENT_ACTIVITY="EXTRA_CURRENT_ACTIVITY";
    public final static String EXTRA_NEW_FRAGMENT="EXTRA_NEW_GRAMENT";

    public static void actionTo(Context context, int action ,String activity, String fragment){
        Intent intent=new Intent(context,XPoseService.class);
        intent.putExtra(ACTION,action);
        intent.putExtra(EXTRA_CURRENT_ACTIVITY,activity);
        intent.putExtra(EXTRA_NEW_FRAGMENT,fragment);
        context.startService(intent);
    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        if(intent!=null){
            handleIntent(intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }



    public void handleIntent(Intent intent){
        int action=intent.getIntExtra(ACTION, 0);
        if((action&ACTION_CLEAR_FRAGMENTS)==ACTION_CLEAR_FRAGMENTS){
            ViewDebugHelperApplication.getInstance().removeAllXposeFragments();
            ViewDebugHelperApplication.getInstance().setXposeCurrentActivity(intent.getStringExtra(EXTRA_CURRENT_ACTIVITY));
        }

        if((action&ACTION_ADD_FRAGMENT)==ACTION_ADD_FRAGMENT){
            ViewDebugHelperApplication.getInstance().addXposeFragment(intent.getStringExtra(EXTRA_NEW_FRAGMENT));
        }

    }


    @Override
    public IBinder onBind(Intent intent) {
        throw  new UnsupportedOperationException("not support action");
    }
}
