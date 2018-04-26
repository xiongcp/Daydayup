package com.xiongcp.commonlibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.SparseArray;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by DG on 2017/12/10
 */

public class SystemUtils {

    private static Context mContext;


    /**
     * 调试日志
     */
    public static final String ERROR_LOG_FILENAME = "wifi-debug-";


    /**
     * 日志加上时间
     */
    private static String logfilename = getLogFile();


    private static String mLastError = "";


    private static SparseArray<Long> clickTime = new SparseArray<>();
    private static String status = "";

    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * make true current connect service is wifi
     *
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }

    public static SharedPreferences getSettings(Context context) {
        if (mContext == null) {
            mContext = context;
        }
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }


    @SuppressLint("SimpleDateFormat")
    public static synchronized void debugLogging(String extraMessage) {

        if (mContext != null) {

            try {
                File file = new File(logfilename);
                FileWriter fWriter = null;
                BufferedWriter bWriter = null;
                if (!file.exists()) {
                    fWriter = new FileWriter(file, true);
                    bWriter = new BufferedWriter(fWriter);
                    //首次创建文件
                    bWriter.write("create at " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "\n");
                    bWriter.write(new Date().getTime() + "");
                } else {
                    fWriter = new FileWriter(file, true);
                    bWriter = new BufferedWriter(fWriter);
                }

                bWriter.write("\n\n " + new Date() + "\n");
                if (extraMessage != null && extraMessage.length() > 0) {
                    if (extraMessage.length() > 2000) {
                        extraMessage = extraMessage.substring(0, 2000);
                    }
                    bWriter.write("##debug message##" + extraMessage);
                }

                bWriter.close();
            } catch (IOException ioe) {
                Logger.error(ioe.toString());
            }
        }
    }


    public static synchronized void errorLogging(Throwable e, String extraMessage) {
        String message = "Unknown error.",
                trace = "Unknown trace.";

        if (e != null) {
            if (e.getMessage() != null && !e.getMessage().isEmpty())
                message = e.getMessage();

            else if (e.toString() != null)
                message = e.toString();

            if (message.equals(mLastError))
                return;

            Writer sWriter = new StringWriter();
            PrintWriter pWriter = new PrintWriter(sWriter);
            e.printStackTrace(pWriter);

            trace = sWriter.toString();

            if (mContext != null) {

                try {
                    File file = new File(logfilename);
                    FileWriter fWriter = null;
                    BufferedWriter bWriter = null;
                    if (!file.exists()) {
                        fWriter = new FileWriter(file, true);
                        bWriter = new BufferedWriter(fWriter);
                        //首次创建文件
                        bWriter.write("create at " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "\n");
                        bWriter.write(new Date().getTime() + "");
                    } else {
                        fWriter = new FileWriter(file, true);
                        bWriter = new BufferedWriter(fWriter);
                    }

                    bWriter.write("\n\n " + new Date() + "\n");
                    if (extraMessage != null && extraMessage.length() > 0) {
                        bWriter.write(extraMessage + "\n");
                    }
                    bWriter.write(trace);
                    bWriter.close();
                } catch (IOException ioe) {
                    Logger.error(ioe.toString());
                }
            }
        }

        setLastError(message);
        Logger.error(message);
        Logger.error(trace);
    }


    public static synchronized void errorLogging(String traceMessage, String extraMessage) {

        if (traceMessage != null) {

            if (traceMessage.equals(mLastError))
                return;

            if (mContext != null) {
                try {
                    File file = new File(logfilename);
                    FileWriter fWriter = null;
                    BufferedWriter bWriter = null;
                    if (!file.exists()) {
                        fWriter = new FileWriter(file, true);
                        bWriter = new BufferedWriter(fWriter);
                        //首次创建文件
                        bWriter.write("create at " + (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date()) + "\n");
                        bWriter.write(new Date().getTime() + "");
                    } else {
                        fWriter = new FileWriter(file, true);
                        bWriter = new BufferedWriter(fWriter);
                    }

                    bWriter.write("\n\n " + new Date() + "\n");
                    if (extraMessage != null && extraMessage.length() > 0) {
                        bWriter.write(extraMessage + "\n");
                    }
                    bWriter.write(traceMessage);
                    bWriter.close();
                } catch (IOException ioe) {
                    Logger.error(ioe.toString());
                }
            }
        }

        setLastError(traceMessage);
        Logger.error(traceMessage);
        Logger.error(traceMessage);
    }


    public static void setLastError(String error) {
        mLastError = error;
    }


    public static String millisecondToTime(String time) {
        long millisecond = Long.valueOf(time) * 1000;
        Date date = new Date(millisecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.getDefault());
        return format.format(date);
    }

    /**
     * 获取日志全路径
     *
     * @return
     */
    public static String getLogFile() {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(new Date());
        String fileName = ERROR_LOG_FILENAME + time + ".log";

        //return new File(Environment.getExternalStorageDirectory().toString(), fileName).getAbsolutePath();

        File dir = new File(FileSaveUtil.log_dir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return new File(FileSaveUtil.log_dir, fileName).getAbsolutePath();
    }


    /**
     * 检查点击事件是否过度频繁
     *
     * @param buttonId
     * @param CLICK_DELAY_TIME
     * @return
     */
    public static boolean isFastClick(int buttonId, int CLICK_DELAY_TIME) {
        Long lastClickTime = clickTime.get(buttonId);
        long curClickTime = System.currentTimeMillis();
        if (lastClickTime != null) {
            if ((curClickTime - lastClickTime) <= CLICK_DELAY_TIME) {
                return true;
            }
        }
        clickTime.put(buttonId, new Long(curClickTime));
        return false;
    }

    public static boolean networkIsAvailable(Context context) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                //UIUtils.showToast("WIFI已连接,移动数据已连接");\
                return true;
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                //UIUtils.showToast("WIFI已连接,移动数据已断开");
                return true;
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                //UIUtils.showToast("WIFI已断开,移动数据已连接");
                return true;
            } else {
                //UIUtils.showToast("WIFI已断开,移动数据已断开");
                return false;
            }
            //API大于23时使用下面的方式进行网络监听
        } else {

            //Logger.debug("ApiService level 大于23");
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            if (networks.length > 0) {
                for (int i = 0; i < networks.length; i++) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                    sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
                }
                return true;
            } else {
                return false;
            }
        }
    }

}
