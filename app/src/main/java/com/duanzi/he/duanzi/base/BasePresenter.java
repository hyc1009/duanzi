package com.duanzi.he.duanzi.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 *
 * Created by he on 2017/9/19.
 */

public abstract class BasePresenter<V> {

    protected V mView;
  protected CompositeDisposable mCompositeDisposable =  new CompositeDisposable();
    public void setView(V v) {
        mView = v;
        this.onAttached();
    }

    public  void  onAttached() {}

    public void  onDetached() {
        mCompositeDisposable.dispose();
    }
}
