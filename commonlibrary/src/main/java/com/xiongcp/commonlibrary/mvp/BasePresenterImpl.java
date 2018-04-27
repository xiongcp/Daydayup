package com.xiongcp.commonlibrary.mvp;


import android.os.SystemClock;

import com.xiongcp.commonlibrary.thread.ThreadPoolFactory;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import me.drakeet.materialdialog.MaterialDialog;

public class BasePresenterImpl<V extends BaseView> implements BasePresenter<V> {
    protected V mView;
    private MVPBaseActivity mContext;
    protected Reference<V> mViewRef;

    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<>(view);
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    private MaterialDialog mMaterialDialog;


    @Override
    public void showMaterialDialog(String title, String message) {
        mMaterialDialog = new MaterialDialog(mContext)
                .setTitle(title)
                .setMessage(message);
        mMaterialDialog.show();
    }

    @Override
    public void hideMaterialDialog() {
        if (mMaterialDialog != null) {
            mMaterialDialog.dismiss();
        }
    }

    @Override
    public void showMaterialDialogDeploy(String title, String message) {
        final MaterialDialog mMaterialDialog = new MaterialDialog(mContext)
                .setTitle(title)
                .setMessage(message);
        mMaterialDialog.show();
        ThreadPoolFactory.getNormalPool().submit(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMaterialDialog != null) {
                            mMaterialDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void setContext(MVPBaseActivity context) {
        mContext = context;
    }
}
