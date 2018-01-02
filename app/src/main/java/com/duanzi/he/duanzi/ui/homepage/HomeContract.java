package com.duanzi.he.duanzi.ui.homepage;

import com.duanzi.he.duanzi.base.BasePresenter;
import com.duanzi.he.duanzi.base.BaseView;

/**
 * Created by he on 2017/10/20.
 */

public interface HomeContract {

    interface View extends BaseView {
      void  getPostid();
      void showQueryResult(String result);
      void sendSMS(String phoneNumber,String message);

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void getDeliveryInfo(String companyName,String postid);
        public abstract void getLabelList();
    }
}
