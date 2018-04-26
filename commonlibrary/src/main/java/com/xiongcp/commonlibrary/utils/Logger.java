package com.xiongcp.commonlibrary.utils;

import android.util.Log;


/**
 * Created by Administrator on 2017/12/11
 */

public class Logger {

    private static final String TAG = "day";
    private static final String mClassName = Logger.class.getName();

    private static void log(int priority, String message) {
        StackTraceElement[] els = Thread.currentThread().getStackTrace();
        String className, methodName;

        className = methodName = "unknown";

        for (StackTraceElement element : els) {
            String currClassName = element.getClassName();
            if (currClassName.startsWith("com.") && !currClassName.equals(mClassName)) {
                className = currClassName.replace("com.xiongcp.daydayup", "");
                methodName = element.getMethodName();
                break;
            }
        }

        if (message == null)
            message = "(null)";

        Log.println(priority, TAG + "[" + className + "." + methodName + "]", message);
    }

    public static void debug(String message) {
        if (isDebug) {
            log(Log.DEBUG, message);
            SystemUtils.debugLogging(message);
        }
    }

    /**
     * 是否为调试模式
     */
    private static final boolean isDebug = true;

    public static void log2print(String message) {
        if (isDebug) {
            log(Log.DEBUG, message);
        }
    }

    public static void info(String message) {
        if (isDebug) {
            log(Log.INFO, message);
        }
    }

    public static void warning(String message) {
        if (isDebug) {
            log(Log.WARN, message);
        }
    }

    public static void error(String message) {
        if (isDebug) {
            log(Log.ERROR, message);
        }
    }


}
