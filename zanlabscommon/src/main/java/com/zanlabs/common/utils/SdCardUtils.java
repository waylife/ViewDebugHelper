package com.zanlabs.common.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

import java.io.File;

public class SdCardUtils {

	/**with '/' character at the end*/
	public static String getSDCardPath() {
//		String szDir = Environment.getExternalStorageDirectory().getPath();
		String szDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		if (szDir.substring(szDir.length() - 1).equals(File.separator) == false) {
			szDir += File.separator;
		}
		return szDir;
	}

	/** 判断SD卡是否可用 */
	public static boolean isSdCardAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获得SD卡总大小
	 * 
	 * @return
	 */
	public static long getSDTotalSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return blockSize * totalBlocks;
	}

	/**
	 * 获得sd卡剩余容量，即可用大小
	 * 
	 * @return
	 */
	public static long getSDAvailableSize() {
		File path = Environment.getExternalStorageDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize =0;
		long availableBlocks=0;
		if(AppUtil.getSDKInt()>=18){
			blockSize= stat.getBlockSizeLong();
			availableBlocks=stat.getAvailableBlocksLong();
		}else{
			blockSize= stat.getBlockSize();
			availableBlocks=stat.getAvailableBlocks();
		}
		return blockSize * availableBlocks;
	}

	/**
	 * 获得机身内存总大小rom
	 * 
	 * @return
	 */
	public static long getRomTotalSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return blockSize * totalBlocks;
	}

	/**
	 * 获得机身可用内存rom
	 * 
	 * @return
	 */
	public static long getRomAvailableSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return blockSize * availableBlocks;
	}

	/**
	 * Formats a content size to be in the form of bytes, kilobytes, megabytes,
	 * etc
	 * 
	 * @return
	 */
	public static String formatFileSize(Context context, long fileSize) {
		return Formatter.formatFileSize(context, fileSize);
	}

	/**Check if get enough space to download specific file size*/
	public static boolean isGetEnoughSpace(long bytesize){
		long size = SdCardUtils.getSDAvailableSize();
		if (size > bytesize) {
			return true;
		}
		return false;
	}

}
