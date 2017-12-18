package com.zanlabs.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.preference.PreferenceManager;

/**
 * 使用Preference来管理配置<br/>
 * 
 * @author wangyunsi0662
 */
public class PreferencesUtil {
	public final  static String SPLASH_VERSION="app_splash_version";

	/**
	 * 获得是否是第一次打开应用，包含以下两种情形<br/>
	 * 1.应用安装,应用第一次安装在该手机<br/>
	 * 2.应用升级,当前版本比原来版本高<br/>
	 * */
	public static boolean isFirstTimeOpenSplash(Context context) {
		boolean flag = false;
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			int currentVersion = info.versionCode;
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			int lastVersion = preferences.getInt(SPLASH_VERSION, 0);
			if (currentVersion > lastVersion) {
				flag = true;
			}
		} catch (Exception e) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			int lastVersion = preferences.getInt(SPLASH_VERSION, 0);
			if (lastVersion == 0)
				flag = true;
			else
				flag = false;
		}
		return flag;
	}



	public static void setFirstTimeOpenSplash(Context context) {
		PackageInfo info = null;
		try {
			info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			int currentVersion = info.versionCode;
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			int lastVersion = preferences.getInt(SPLASH_VERSION, 0);
			if (currentVersion > lastVersion) {
				preferences.edit().putInt(SPLASH_VERSION, currentVersion).commit();
			}
		} catch (Exception e) {
			SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
			preferences.edit().putInt(SPLASH_VERSION, 1).commit();
		}
	}



	
	/**有些时候MarketApplication.getInstance()是为null的，比如升级数据库的时候*/
	public static boolean setIntValue(Context context,String key, int value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putInt(key, value);
		return editor.commit();
	}


	/** 设置Key对应的long值 */
	public static boolean setLongValue(Context context,String key, long value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putLong(key, value);
		return editor.commit();
	}



	/**
	 * 获得Key对应的long值
	 *
	 * @param defaultValue
	 *            默认值
	 */
	public static long getLongValue(Context context,String key, long defaultValue) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getLong(key, defaultValue);
	}



	/** 设置Key对应的boolean值 */
	public static boolean setBoolValue(Context context,String key, boolean value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		return editor.commit();
	}



	/**
	 * 获得Key对应的boolean值
	 *
	 * @param defaultValue
	 *            默认值
	 */
	public static boolean getBoolValue(Context context,String key, boolean defaultValue) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean(key, defaultValue);
	}

	/** 设置Key对应的String值 */
	public static boolean setStringValue(Context context,String key, String value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putString(key, value);
		return editor.commit();
	}



	/**
	 * 获得Key对应的String值
	 *
	 * @param defaultValue
	 *            默认值
	 */
	public static String  getStringValue(Context context,String key, String defaultValue) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getString(key, defaultValue);
	}


}
