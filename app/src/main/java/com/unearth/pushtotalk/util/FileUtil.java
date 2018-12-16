package com.unearth.pushtotalk.util;

import android.os.Environment;

import java.io.File;

/**
 * Created by Matthew Roberts on 12/16/2018.
 */

public class FileUtil {

    public static void createAppDirectory(String appName) {
        File dir = new File(Environment.getExternalStorageDirectory(), appName);
        if (!dir.exists())
            dir.mkdir();
    }

    public static File getNewAudioFile(String appName, String fileName) {
        File file =  new File(Environment.getExternalStorageDirectory(),
                appName + File.separator + fileName);
        file.mkdir();
        return file;
    }
}
