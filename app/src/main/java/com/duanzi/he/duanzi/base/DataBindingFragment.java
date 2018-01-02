package com.duanzi.he.duanzi.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by he on 2017/10/20.
 */

public abstract class DataBindingFragment <B extends ViewDataBinding> extends Fragment{


    public Context mContext;
    public B binding;
    private boolean mIsPrepare;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        try {
            initPresenter();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        try {
            initView();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        mIsPrepare = true;

        onLazyLoad();
        return binding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (mIsPrepare && isVisibleToUser) {
            onLazyLoad();
        }
    }

    protected abstract void onLazyLoad();


    public abstract int getLayoutId();
    public abstract void initView() throws ClassNotFoundException;
    protected void initPresenter () throws IllegalAccessException, java.lang.InstantiationException {

    }


}
