package com.xiongcp.commonlibrary.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

/**
 * Created by DG on 2018/4/19
 */

public class ApplicationUtils {

    private static String app_ver = "";
    private static String app_name = "";
    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context mContext;//上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler

    public void onCreate(Application application) {
        mContext = application.getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        ApplicationUtils.mContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        ApplicationUtils.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        ApplicationUtils.mMainThreadId = mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static void setMainThreadLooper(Looper mMainLooper) {
        ApplicationUtils.mMainLooper = mMainLooper;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void setMainHandler(Handler mHandler) {
        ApplicationUtils.mHandler = mHandler;
    }

    /**
     * 获得app版本号
     *
     * @return string
     */
    public String getAppVer() {
        if (!TextUtils.isEmpty(app_ver))
            return app_ver;

        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            app_ver = packageInfo.versionName;
            return app_ver;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getAppName() {

        if (!TextUtils.isEmpty(app_name))
            return app_name;

        try {
            app_name = mContext.getApplicationInfo().loadLabel(mContext.getPackageManager()).toString();
            return app_name;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
