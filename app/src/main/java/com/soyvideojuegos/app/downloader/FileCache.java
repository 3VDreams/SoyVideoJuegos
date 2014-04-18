package com.soyvideojuegos.app.downloader;


import android.content.Context;
import android.os.Environment;

import com.soyvideojuegos.app.conf.LogConf;

import java.io.File;

public class FileCache {

    private File cacheDir;

    public FileCache(Context context) {

        String mediaMounted = android.os.Environment.MEDIA_MOUNTED;
        String extStorageState = Environment.getExternalStorageState();

        if (extStorageState.equals(mediaMounted)) {
            File extStorageStateDir = Environment.getExternalStorageDirectory();
            cacheDir = new File(extStorageStateDir, LogConf.TAG);
        } else {
            cacheDir = context.getCacheDir();
        }

        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    public File getFile(String url) {
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename = String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

    public String getCacheFolder() {
        return cacheDir.getAbsolutePath();
    }

}