package com.xiongcp.commonlibrary.mvp;



public interface  BasePresenter <V extends BaseView>{

    void attachView(V view);

    void detachView();

    void showMaterialDialog(String title, String message);

    void hideMaterialDialog();

    void showMaterialDialogDeploy(String title, String message);

    void setContext(MVPBaseActivity context);
}
