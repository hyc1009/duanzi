package com.duanzi.he.duanzi.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


/**
 * Created by he on 2017/9/19.
 */

public abstract class DataBindingActivity<B extends ViewDataBinding> extends AppCompatActivity {

    public Context mContext;
    public B mViewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = getLayoutInflater().inflate(getLayoutId(), null, false);
        mViewBinding = DataBindingUtil.bind(rootView);
        super.setContentView(rootView);
        mContext = this;
        try {
            initPresenter();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        initView();
//        AdManager.getInstance(this).init("72a708e035b3e9bf", "99ce54858450aab1", true);
    }

    public abstract int getLayoutId();
    public abstract void initView();
    protected void initPresenter () throws IllegalAccessException, InstantiationException {

    }
}
