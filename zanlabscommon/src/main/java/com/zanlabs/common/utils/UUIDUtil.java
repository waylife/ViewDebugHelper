package com.zanlabs.common.utils;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;


import java.util.Random;
import java.util.UUID;

public class UUIDUtil {
	private final static String UUID_KEY="_s_UUIDUtil";

	public static String getUUID(Context context) {
		if (context == null) {
			return "";
		}
		String str = Settings.System.getString(context.getContentResolver(), UUID_KEY);
		if (TextUtils.isEmpty(str)) {
			str = changeUUID(context);
			Settings.System.putString(context.getContentResolver(), UUID_KEY, str);
		}
		return str;
	}

	private static String changeUUID(Context context) {
		if (context == null) {
			return "";
		}
		String uuid = getInternalUUID(context);
		Settings.System.putString(context.getContentResolver(), UUID_KEY, uuid);
		return uuid;
	}

	private static String getInternalUUID(Context context) {
		return "_s_" + UUID.randomUUID().toString() + get15Random();
	}

	private static String get15Random() {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < 15; i++) {
			str += random.nextInt(10);
		}
		return str;
	}

}
