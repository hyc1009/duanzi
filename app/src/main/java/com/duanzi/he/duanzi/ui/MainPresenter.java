package com.duanzi.he.duanzi.ui;

import android.util.Log;

/**
 * Created by he on 2017/9/19.
 */

public class MainPresenter extends MainContract.Presenter {


    @Override
    public  void showlog() {
        Log.e("我的==","得到了mainresenter");
    }
}
