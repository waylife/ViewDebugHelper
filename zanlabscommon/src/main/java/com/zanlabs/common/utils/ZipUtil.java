package com.zanlabs.common.utils;

import android.text.TextUtils;

import java.io.IOException;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

public class ZipUtil {
	/**check if a zip file(can be apk file) is valid*/
	public  static boolean isZipValid(String filePath){
			if(TextUtils.isEmpty(filePath))
				return false;
		   ZipFile zipfile = null;
		    try {
		        zipfile = new ZipFile(filePath);
		        return true;
		    } catch (ZipException e) {
		        return false;
		    } catch (IOException e) {
		        return false;
		    } finally {
		        try {
		            if (zipfile != null) {
		                zipfile.close();
		                zipfile = null;
		            }
		        } catch (IOException e) {
		        }
		    }
	}
}
