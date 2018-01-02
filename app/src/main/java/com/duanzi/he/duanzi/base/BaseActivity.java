package com.duanzi.he.duanzi.base;

import android.databinding.ViewDataBinding;
import android.util.Log;

import java.lang.reflect.ParameterizedType;

/**
 * Created by he on 2017/9/19.
 */

public abstract class BaseActivity<P extends BasePresenter, B extends ViewDataBinding> extends DataBindingActivity<B> {

    public P mPresenter;

    @Override
    @SuppressWarnings("unchecked")
    protected void initPresenter() throws IllegalAccessException, InstantiationException {
        if (this instanceof BaseView && getClass().getGenericSuperclass() instanceof ParameterizedType && ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments().length > 0) {
            Class aClass = (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            Log.e("名==",aClass.getName());
            mPresenter = (P) aClass.newInstance();
            if (mPresenter != null) {
                mPresenter.setView(this);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDetached();
        }
    }
}
