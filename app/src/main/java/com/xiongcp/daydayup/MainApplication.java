package com.xiongcp.daydayup;

import android.support.multidex.MultiDexApplication;

import com.xiongcp.commonlibrary.utils.ApplicationImpl;
import com.xiongcp.commonlibrary.utils.CrashHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DG on 2018/4/26
 */

public class MainApplication extends MultiDexApplication {
    private static final List<String> moduleList = new ArrayList<>();
    private static final String DETAILAPPLICATION = "com.xiongcp.commonlibrary.CommonApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        moduleList.add(DETAILAPPLICATION);
        if (BuildConfig.DEBUG) {
            if (com.squareup.leakcanary.LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            com.squareup.leakcanary.LeakCanary.install(this);
            com.facebook.stetho.Stetho.initializeWithDefaults(this);
        }

        for (String applicationName : moduleList) {
            try {
                Class<?> clz = Class.forName(applicationName);
                if (clz.newInstance() instanceof ApplicationImpl) {
                    ApplicationImpl dexApplication = (ApplicationImpl) clz.newInstance();
                    dexApplication.onLoadApplication(MainApplication.this);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
