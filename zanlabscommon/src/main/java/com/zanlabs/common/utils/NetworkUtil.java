package com.zanlabs.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;

public class NetworkUtil {
	private static final String TAG = NetworkUtil.class.getName();

	public static boolean isNetworkAvailable(Context context) {
        if(context==null)
            return false;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
//			XLog.d(TAG, "+++couldn't get connectivity manager");
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//						XLog.d(TAG,
//								"+++network is available: "
//										+ info[i].getTypeName());
						return true;
					}
				}
			}
		}


		return false;
	}

	public static boolean isMobileNetworkActive(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		}

		final NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();
		if (activeInfo == null) {
			return false;
		} else if (activeInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			return true;
		}
		return false;
	}

	public static boolean isWifiActive(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		}

		final NetworkInfo activeInfo = connectivity.getActiveNetworkInfo();
		if (activeInfo == null) {
			return false;
		} else if (activeInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 判断当前网络是wifi或者3G
	 *
	 * @param context
	 * @return
	 */
	public static boolean isWifiOr3G(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			int type = networkInfo.getType();
			if (type == ConnectivityManager.TYPE_WIFI) {
				return true;
			} else if (type == ConnectivityManager.TYPE_MOBILE) {
				if (isFastMobileNetwork(context)) {
					return true;
				}
			}
		} else {
			return false;
		}
		return false;
	}


	/**<br/>
	 * http://www.binkery.com/post/368.html<br/>*/
	private static boolean isSlowMobileNetwork(Context context){
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		boolean result=true;
		if(telephonyManager!=null){
			switch(telephonyManager.getNetworkType()){
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN:
					result=true;//2g
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B:
				case TelephonyManager.NETWORK_TYPE_EHRPD:
				case TelephonyManager.NETWORK_TYPE_HSPAP:
					result=false;//3g
					break;
				case TelephonyManager.NETWORK_TYPE_LTE:
					result=false;//4g
					break;
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
					result=true;
					break;
			}
		}else{
			result=true;
		}
		return result;
	}

	/**
	 * 判断是否为快速网络(3g,4g网络)
	 *
	 * @param context
	 * @return
	 */
	private static boolean isFastMobileNetwork(Context context) {
		return !isSlowMobileNetwork(context);
	}

	/**
	 * 判断当前网络是wifi或者3G<br/>
	 * 调试时使用<br/>
	 * @param context
	 * @return
	 */
	public static String getNetworkTypeForLog(Context context) {
		StringBuilder netTypeBuilder = new StringBuilder();
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		netTypeBuilder.append("activeNetWorkInfo:");
		if (networkInfo != null && networkInfo.isConnected()) {
			int type = networkInfo.getType();
			switch (type) {
				case ConnectivityManager.TYPE_WIFI:
					netTypeBuilder.append("wifi;");
					break;
				case ConnectivityManager.TYPE_MOBILE:
					netTypeBuilder.append("mobile;");
					break;
				case ConnectivityManager.TYPE_WIMAX:
					netTypeBuilder.append("wimax;");
					break;
				case ConnectivityManager.TYPE_MOBILE_DUN:
					netTypeBuilder.append("mobile_dun;");
					break;
				case ConnectivityManager.TYPE_MOBILE_HIPRI:
					netTypeBuilder.append("mobile_hipri;");
					break;
				case ConnectivityManager.TYPE_MOBILE_MMS:
					netTypeBuilder.append("mobile_mms;");
					break;
				case ConnectivityManager.TYPE_MOBILE_SUPL:
					netTypeBuilder.append("mobile_supl;");
					break;
				default:
					netTypeBuilder.append("unknown;");
					break;
			}
		} else {
			netTypeBuilder.append("net not available;");
		}
		//all networks
		netTypeBuilder.append("+++allNetWork:");
		NetworkInfo[] info =manager.getAllNetworkInfo();
		if(info!=null&&info.length>0){
			for(int i=0;i<info.length;i++ ){
				if(!info[i].isConnectedOrConnecting())
					continue;
				int type = info[i].getType();
				switch (type) {
					case ConnectivityManager.TYPE_WIFI:
						netTypeBuilder.append("wifi;");
						break;
					case ConnectivityManager.TYPE_MOBILE:
						netTypeBuilder.append("mobile;");
						break;
					case ConnectivityManager.TYPE_WIMAX:
						netTypeBuilder.append("wimax;");
						break;
					case ConnectivityManager.TYPE_MOBILE_DUN:
						netTypeBuilder.append("mobile_dun;");
						break;
					case ConnectivityManager.TYPE_MOBILE_HIPRI:
						netTypeBuilder.append("mobile_hipri;");
						break;
					case ConnectivityManager.TYPE_MOBILE_MMS:
						netTypeBuilder.append("mobile_mms;");
						break;
					case ConnectivityManager.TYPE_MOBILE_SUPL:
						netTypeBuilder.append("mobile_supl;");
						break;
					default:
						netTypeBuilder.append("unknown;");
						break;
				}
			}
		}

		return netTypeBuilder.toString();
	}

	/** 获得IP信息 */
	public static String getOneIPV4() {
		ArrayList<String> list = new ArrayList<String>();
		String result = "ip get failed";
		try {
			for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
					if (inetAddress instanceof Inet4Address) {
						if (!inetAddress.isLoopbackAddress()) {
							list.add(inetAddress.getHostAddress());
						}
					}
				}
			}
		} catch (SocketException ex) {
		}
		if (list.size() > 0)
			result = list.get(0);
		return result;
	}

	/** 获得IP信息 */
	public static String getAllIP() {
		ArrayList<String> list = new ArrayList<String>();
		StringBuffer sBuffer = new StringBuffer();
		try {
			for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
					list.add(inetAddress.getHostAddress());
				}
			}
		} catch (SocketException ex) {
		}
		int length = list.size();
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				sBuffer.append(list.get(i));
				if (i != length - 1) {
					sBuffer.append(";");
				}
			}
		} else {
			return "ip get failed";
		}
		return sBuffer.toString();
	}
}
