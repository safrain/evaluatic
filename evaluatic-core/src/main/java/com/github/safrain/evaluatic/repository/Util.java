package com.github.safrain.evaluatic.repository;

import java.io.*;

/**
 * @author safrain
 */
public class Util {
    static String readFully(InputStream is, String charset) {
        byte[] arr = new byte[8 * 1024]; // 8K at a time
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int read;
        try {
            while ((read = is.read(arr, 0, arr.length)) > 0) {
                bos.write(arr, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            return new String(bos.toByteArray(), charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    static byte[] readFully(InputStream is) {
        byte[] arr = new byte[8 * 1024]; // 8K at a time
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int read;
        try {
            while ((read = is.read(arr, 0, arr.length)) > 0) {
                bos.write(arr, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bos.toByteArray();
    }

    static void closeQuietly(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
        }
    }

    static void closeQuietly(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
        }
    }
}
