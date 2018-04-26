package com.xiongcp.commonlibrary;

import android.app.Application;

import com.xiongcp.commonlibrary.utils.ApplicationImpl;
import com.xiongcp.commonlibrary.utils.ApplicationUtils;

/**
 * Created by DG on 2018/4/26
 */

public class CommonApplication  extends ApplicationUtils implements ApplicationImpl {
    @Override
    public void onLoadApplication(Application application) {
        onCreate(application);
    }
}
