package com.duanzi.he.duanzi.ui;

import com.duanzi.he.duanzi.base.BasePresenter;
import com.duanzi.he.duanzi.base.BaseView;

/**
 * Created by he on 2017/9/19.
 */

public interface MainContract {

    interface View extends BaseView {

    }

     abstract class Presenter extends BasePresenter<View> {
        abstract void  showlog();
    }

}
