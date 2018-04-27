package com.xiongcp.commonlibrary.mvp;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.xiongcp.commonlibrary.R;
import com.xiongcp.commonlibrary.utils.ActivityCollector;
import com.xiongcp.commonlibrary.utils.HookViewClickUtil;
import com.xiongcp.commonlibrary.utils.StatusBarUtil;
import com.xiongcp.commonlibrary.utils.UIUtils;

import java.lang.reflect.ParameterizedType;


public abstract class MVPBaseActivity<V extends BaseView, T extends BasePresenterImpl<V>> extends AppCompatActivity implements BaseView {
    public T mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
        mPresenter.setContext(this);
        final View inflate = LayoutInflater.from(this).inflate(provideContentViewId(), null, false);
        setContentView(inflate);
        //沉浸式状态栏
        StatusBarUtil.setColor(this, UIUtils.getColor(R.color.colorPrimaryDark), 10);
        ActivityCollector.addActivity(this);
        initView();
        initData();
        initListener();
        //hook 处理频繁点击
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                HookViewClickUtil.hookView(inflate);
            }
        });
    }


    protected abstract void initListener();

    protected abstract void initData();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    protected abstract void initView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        ActivityCollector.removeActivity(this);
    }


    @Override
    public Context getContext() {
        return this;
    }

    public <T> T getInstance(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
}
