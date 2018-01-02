package com.duanzi.he.duanzi.base;

import android.databinding.ViewDataBinding;
import android.util.Log;

import java.lang.reflect.ParameterizedType;

/**
 * Created by he on 2017/10/20.
 */

public abstract class BaseFragment<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingFragment<B> {

    public P mPresenter;

    @Override
    @SuppressWarnings("unchecked")
    protected void initPresenter() throws IllegalAccessException, java.lang.InstantiationException {
        if (this instanceof BaseView && getClass().getGenericSuperclass() instanceof ParameterizedType && ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments().length > 0) {
            Class aClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Log.e("名==",aClass.getName());
            mPresenter = (P) aClass.newInstance();
//            Log.e("名==",mPresenter.toString());
            if (mPresenter != null) {
                mPresenter.setView(this);
            }
        }
    }
}
