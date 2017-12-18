package com.zanlabs.viewdebughelper.xpose;

import android.app.Activity;
import android.app.Fragment;
import android.util.Log;

import com.zanlabs.viewdebughelper.ViewDebugHelperApplication;
import com.zanlabs.viewdebughelper.xpose.ipc.XPoseService;

public class XposeHook{}
/**
// * Created by rxread on 11/8/15.
// */
//public class XposeHook implements IXposedHookLoadPackage {
//
//    public final static String ACTIVITY_NAME="android.app.Activity";
//
//    public final static String FRAGMENT_NAME="android.app.Fragment";
//    public final static String FRAGMENT_V4_NAME="android.support.v4.app.Fragment";
//
//    public static void log(String msg){
//        Log.e("viewhelper_xpose",msg);
//    }
//    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
//
//        //activity
//        XposedHelpers.findAndHookMethod(ACTIVITY_NAME, lpparam.classLoader, "onPause", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                String currentObjectName=param.thisObject.getClass().getName();
//                log(String.format("beforeHookedMethod->method:%s->className:%s",param.method.getName(),currentObjectName));
//            }
//        });
//        XposedHelpers.findAndHookMethod(ACTIVITY_NAME, lpparam.classLoader, "onResume", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                String currentObjectName=param.thisObject.getClass().getName();
//                log("packagename/processname:"+lpparam.packageName+"-"+lpparam.processName);
//                if(param.thisObject instanceof Activity){
//                    Activity activity= (Activity) param.thisObject;
//                    XPoseService.actionTo(activity,XPoseService.ACTION_CLEAR_FRAGMENTS,currentObjectName,"");
//                }
//                log(String.format("beforeHookedMethod->method:%s->className:%s", param.method.getName(), currentObjectName));
//            }
//        });
//
//        //fragment
//        XposedHelpers.findAndHookMethod(FRAGMENT_NAME, lpparam.classLoader, "onResume", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                String currentObjectName=param.thisObject.getClass().getName();
//                if(param.thisObject instanceof Fragment){
//                    Fragment fragment= (Fragment) param.thisObject;
//                    XPoseService.actionTo(fragment.getActivity(),XPoseService.ACTION_ADD_FRAGMENT,"",currentObjectName);
//                }
//                log(String.format("beforeHookedMethod->method:%s->className:%s",param.method.getName(),currentObjectName));
//            }
//        });
//
//        XposedHelpers.findAndHookMethod(FRAGMENT_NAME, lpparam.classLoader, "onPause", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                String currentObjectName=param.thisObject.getClass().getName();
//                log(String.format("beforeHookedMethod->method:%s->className:%s",param.method.getName(),currentObjectName));
//            }
//        });
//
//        //v4 fragment
//        XposedHelpers.findAndHookMethod(FRAGMENT_V4_NAME, lpparam.classLoader, "onResume", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                String currentObjectName=param.thisObject.getClass().getName();
//                ViewDebugHelperApplication.getInstance().addXposeFragment(currentObjectName);
//                if(param.thisObject instanceof android.support.v4.app.Fragment){
//                    android.support.v4.app.Fragment fragment=(android.support.v4.app.Fragment)param.thisObject;
//                    XPoseService.actionTo(fragment.getActivity(),XPoseService.ACTION_ADD_FRAGMENT,"",currentObjectName);
//                }
//                log(String.format("beforeHookedMethod->method:%s->className:%s", param.method.getName(), currentObjectName));
//            }
//        });
//        //fragment
//        XposedHelpers.findAndHookMethod(FRAGMENT_V4_NAME, lpparam.classLoader, "onPause", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                String currentObjectName=param.thisObject.getClass().getName();
//                log(String.format("beforeHookedMethod->method:%s->className:%s",param.method.getName(),currentObjectName));
//            }
//        });
//
//
//    }
//}
