package com.xiongcp.commonlibrary.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.xiongcp.commonlibrary.BuildConfig;
import com.xiongcp.commonlibrary.CommonApplication;


public class UIUtils {

    private static Toast mToast;
    private static int screenWidth;
    private static int screenHeight;
    private static int screenMin;// 宽高中，小的一边
    private static int screenMax;// 宽高中，较大的值
    private static float density;
    private static float scaleDensity;
    private static float xdpi;
    private static float ydpi;
    private static int densityDpi;
    private static int statusbarheight;
    private static int navbarheight;

    /**
     * 测量View的宽高
     *
     * @param view View
     */
    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

    public static void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    public static void showErrorToast(String msg) {
        if (BuildConfig.DEBUG) {
            showToast(msg, Toast.LENGTH_SHORT);
        }
    }

    public static void showToast(String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(CommonApplication.getContext(), "", duration);
        }
        mToast.setText(msg);
        mToast.show();
    }

    /**
     * 用于在线程中执行弹土司操作
     */
    public static void showToastSafely(final String msg) {
        UIUtils.getMainThreadHandler().post(new Runnable() {

            @Override
            public void run() {
                if (mToast == null) {
                    mToast = Toast.makeText(CommonApplication.getContext(), "", Toast.LENGTH_SHORT);
                }
                mToast.setText(msg);
                mToast.show();
            }
        });
    }


    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getCommonApplication() {
        return CommonApplication.getContext();
    }

    /**
     * 得到resources对象
     *
     * @return
     */
    public static Resources getResource() {
        return getCommonApplication().getResources();
    }

    /**
     * 得到string.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 得到string.xml中的字符串，带点位符
     *
     * @return
     */
    public static String getString(int id, Object... formatArgs) {
        return getResource().getString(id, formatArgs);
    }

    /**
     * 得到string.xml中和字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId) {
        return getResource().getStringArray(resId);
    }

    /**
     * 得到colors.xml中的颜色
     *
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getResource().getColor(colorId);
    }

    /**
     * 得到应用程序的包名
     *
     * @return
     */
    public static String getPackageName() {
        return getCommonApplication().getPackageName();
    }

    /**
     * 得到主线程Handler
     *
     * @return
     */
    public static Handler getMainThreadHandler() {
        return CommonApplication.getMainHandler();
    }

    /**
     * 得到主线程id
     *
     * @return
     */
    public static long getMainThreadId() {
        return CommonApplication.getMainThreadId();
    }

    /**
     * 安全的执行一个任务
     *
     * @param task
     */
    public static void postTaskSafely(Runnable task) {
        int curThreadId = android.os.Process.myTid();
        // 如果当前线程是主线程
        if (curThreadId == getMainThreadId()) {
            task.run();
        } else {
            // 如果当前线程不是主线程
            getMainThreadHandler().post(task);
        }
    }

    /**
     * 延迟执行任务
     *
     * @param task
     * @param delayMillis
     */
    public static void postTaskDelay(Runnable task, int delayMillis) {
        getMainThreadHandler().postDelayed(task, delayMillis);
    }

    /**
     * 移除任务
     */
    public static void removeTask(Runnable task) {
        getMainThreadHandler().removeCallbacks(task);
    }

    /**
     * dip-->px
     */
    public static int dip2Px(int dip) {
        // px/dip = density;
        // density = dpi/160
        // 320*480 density = 1 1px = 1dp
        // 1280*720 density = 2 2px = 1dp

        float density = getResource().getDisplayMetrics().density;
        int px = (int) (dip * density + 0.5f);
        return px;
    }

    /**
     * px-->dip
     */
    public static int px2dip(int px) {

        float density = getResource().getDisplayMetrics().density;
        int dip = (int) (px / density + 0.5f);
        return dip;
    }

    /**
     * sp-->px
     */
    public static int sp2px(int sp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResource().getDisplayMetrics()) + 0.5f);
    }






    public static String macFormat(String mac) {
        if (mac.length() == 0 || mac.equals("")) {
            return null;
        }
        String regex = "(.{2})";
        String address = mac.replaceAll(regex, "$1:");
        address = address.substring(0, address.length() - 1);
        return address;
    }

}