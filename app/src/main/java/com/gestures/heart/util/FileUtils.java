package com.gestures.heart.util;

import java.io.File;

/**
 * Created by 25623 on 2017/12/7.
 */

public class FileUtils {

    /**
     * 删除目录下的所有文件即文件夹
     * @param rootPath
     */
    public static void deleteAllFiles(String rootPath) {
        File root = new File(rootPath);

        if(!root.exists())
            return;

        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f.getAbsolutePath());
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f.getAbsolutePath());
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }
}
