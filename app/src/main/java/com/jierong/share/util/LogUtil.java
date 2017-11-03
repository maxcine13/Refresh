package com.jierong.share.util;

import android.util.Log;

/**
 * 日志工具类
 *
 * @author qingf
 */
public class LogUtil {
    private static final String Tag = "Test_Q";
    public static boolean isOpen = false;

    private LogUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void d(String msg) {

        if (!isOpen) {
            Log.d(Tag, msg);
        }
    }

    public static void e(String msg) {

        if (!isOpen) {
            Log.e(Tag, msg);
        }
    }

}
