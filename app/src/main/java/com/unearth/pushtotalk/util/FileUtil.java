package com.unearth.pushtotalk.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Matthew Roberts on 12/16/2018.
 */

public class FileUtil {

    private static String EXT = ".mp4";

    public static void createAppDirectory(String appName) {
        File dir = new File(Environment.getExternalStorageDirectory(), appName);
        if (!dir.exists())
            dir.mkdir();
    }

    public static String getNewAudioFileName(String appName, String fileName) {
        return Environment.getExternalStorageDirectory() + File.separator + appName + File.separator + fileName + EXT;
    }
}
