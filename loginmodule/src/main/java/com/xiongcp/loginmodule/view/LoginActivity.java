package com.xiongcp.loginmodule.view;


import com.xiongcp.commonlibrary.mvp.MVPBaseActivity;
import com.xiongcp.loginmodule.R;
import com.xiongcp.loginmodule.contract.LoginContract;
import com.xiongcp.loginmodule.presenter.LoginPresenter;

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {

    }
}
