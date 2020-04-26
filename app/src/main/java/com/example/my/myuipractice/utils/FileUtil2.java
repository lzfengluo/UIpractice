package com.example.my.myuipractice.utils;

/**
 * Created by yuanpk on 2018/8/2  14:35
 * <p>
 * Description:TODO
 */

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

import static android.os.Environment.getExternalStorageDirectory;

/**
 * 文件操作工具类
 *
 * @author ldm
 * @description：
 * @date 2016-4-28 下午3:17:10
 */
public final class FileUtil2 {
    public static final String DEFAULT_BIN_DIR = "usb";

    /**
     * 检测SD卡是否存在
     */
    public static boolean checkSDcard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 从指定文件夹获取文件
     *
     * @return 如果文件不存在则创建, 如果如果无法创建文件或文件名为空则返回null
     */
    public static File getSaveFile(String folderPath, String fileNmae) {
        File file = new File(getSavePath(folderPath) + File.separator + fileNmae);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 获取SD卡下指定文件夹的绝对路径
     *
     * @return 返回SD卡下的指定文件夹的绝对路径
     */
    public static String getSavePath(String folderName) {
        return getSaveFolder(folderName).getAbsolutePath();
    }

    /**
     * 获取文件夹对象
     *
     * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
     */
    public static File getSaveFolder(String folderName) {
        File file = new File(getExternalStorageDirectory()
                .getAbsoluteFile()
                + File.separator
                + folderName
                + File.separator);
        file.mkdirs();
        return file;
    }

    /**
     * 关闭流
     */
    public static void closeIO(Closeable... closeables) {
        if (null == closeables || closeables.length <= 0) {
            return;
        }
        for (Closeable cb : closeables) {
            try {
                if (null == cb) {
                    continue;
                }
                cb.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void redFileStream(OutputStream os, InputStream is) throws IOException {
        int bytesRead = 0;
        byte[] buffer = new byte[1024 * 8];
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush();
        os.close();
        is.close();
    }

    /**
     * 获取外置卡（可拆卸的）的目录。
     * Environment.getExternalStorageDirectory()获取的目录，有可能是内置卡的。
     * 在高版本上，能访问的外置卡目录只能是/Android/data/{package}。
     */
//    private String getAppRootOfSdCardRemovable() {
//        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            return null;
//        }
//
//        /**
//         * 这一句取的还是内置卡的目录。
//         * /storage/emulated/0/Android/data/com.newayte.nvideo.phone/cache
//         * 神奇的是，加上这一句，这个可移动卡就能访问了。
//         * 猜测是相当于执行了某种初始化动作。
//         */
//        StorageManager mStorageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
//        Class<?> storageVolumeClazz = null;
//        try {
//            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
//            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
//            Method getPath = storageVolumeClazz.getMethod("getPath");
//            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
//            Object result = getVolumeList.invoke(mStorageManager);
//            final int length = Array.getLength(result);
//            for (int i = 0; i < length; i++) {
//                Object storageVolumeElement = Array.get(result, i);
//                String path = (String) getPath.invoke(storageVolumeElement);
//                if ((Boolean) isRemovable.invoke(storageVolumeElement)) {
//                    return path;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
