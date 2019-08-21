package ru.gastroafisha.MyApps.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by alex on 26.12.2017.
 */

public class StreamUtils {
    private static Integer bufferSize = 1024;

    public static void copy(InputStream from, OutputStream to) throws IOException {
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = from.read(buffer)) != -1) {
            to.write(buffer, 0, len);
        }
    }
}
