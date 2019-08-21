package ru.gastroafisha.MyApps.Utils.Services;

import java.io.File;

/**
 * Created by alex on 27.01.2018.
 */

public class FileUtils {

    public static boolean deleteDirectoryRecursive(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectoryRecursive(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
