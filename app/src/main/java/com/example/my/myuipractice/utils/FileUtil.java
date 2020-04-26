package com.example.my.myuipractice.utils;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;


import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;




/**
 * Created by chaos on 2016/5/9.
 */
public class FileUtil {
    private static final String TAG = "FileUtil ";
    // 文件分隔符
    private static final String FILE_SEPARATOR = "/";
    // 外存sdcard存放路径
    private static final String FILE_PATH = Environment.getExternalStorageDirectory() + FILE_SEPARATOR + "config" + FILE_SEPARATOR;
    // private static final String FILE_PATH = Environment.getExternalStorageDirectory() + FILE_SEPARATOR ;
    public static final String FILE_NAME = FILE_PATH + "gateway_config.txt";

    // 根缓存目录
    private static String cacheRootPath = "";

    public static void write(String content) {
        if (content != null && !content.equals("")) {
            File fileDirectory = new File(FILE_PATH);
            if (!fileDirectory.exists()) {
                fileDirectory.mkdir();
            }
            FileOutputStream fileOutputStream = null;
            try {
                byte[] buffer = content.getBytes();
                fileOutputStream = new FileOutputStream(new File(FILE_NAME));
                fileOutputStream.write(buffer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    /**
     * sd卡是否可用
     *
     * @return
     */
    public boolean isSdCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 创建根缓存目录
     *
     * @return
     */
    public String createRootPath(Context mContext) {
        if (isSdCardAvailable()) {
            // /sdcard/Android/data/<application package>/cache
            cacheRootPath = mContext.getApplicationContext().getExternalCacheDir()
                    .getPath();
        } else {
            // /data/data/<application package>/cache
            cacheRootPath = mContext.getCacheDir().getPath();
        }
        return cacheRootPath;
    }

    /**
     * 创建文件夹
     *
     * @param dirPath
     * @return 创建失败返回""
     */
    private static String createDir(String dirPath) {
        try {
            File dir = new File(dirPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dirPath;
    }


    /**
     * 删除文件或者文件夹
     *
     * @param file
     */
    public static void deleteFileOrDirectory(File file) {
        try {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                File[] childFiles = file.listFiles();
                // 删除空文件夹
                if (childFiles == null || childFiles.length == 0) {
                    file.delete();
                    return;
                }
                // 递归删除文件夹下的子文件
                for (int i = 0; i < childFiles.length; i++) {
                    deleteFileOrDirectory(childFiles[i]);
                }
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将内容写入文件
     *
     * @param filePath eg:/mnt/sdcard/demo.txt
     * @param content  内容
     */
    public static void writeFileSdcard(String filePath, String content,
                                       boolean isAppend) {

        try {
            FileOutputStream fout = new FileOutputStream(filePath, isAppend);
            byte[] bytes = content.getBytes();

            fout.write(bytes);

            fout.close();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }


    /**
     * 判断当前线程是否为主线程
     *
     * @return true:是主线程
     */
    public static boolean isInMainThread() {
        Looper myLooper = Looper.myLooper();
        Looper mainLooper = Looper.getMainLooper();
        Log.i(TAG, " myLooper=" + myLooper + ";mainLooper=" + mainLooper);
        return myLooper == mainLooper;
    }


}
