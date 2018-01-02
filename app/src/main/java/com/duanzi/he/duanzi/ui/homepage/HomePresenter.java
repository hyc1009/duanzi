package com.duanzi.he.duanzi.ui.homepage;

import android.util.Log;

import com.duanzi.he.duanzi.http.Api;
import com.duanzi.he.duanzi.model.KuaidiBean;
import com.duanzi.he.duanzi.model.UserLabelBean;
import com.duanzi.he.duanzi.util.ActivityManager;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by he on 2017/10/20.
 */

public class HomePresenter extends HomeContract.Presenter {
    public boolean received = false;
    @Override
    public void getDeliveryInfo(String companyName, String postid) {
//        192.168.31.89
        Log.e("he", "\"b5cd359fcbe62cff9364ce7d78473c77\"");
        mCompositeDisposable.add(Api.getInsttancce()
                .apiService.getLaleList("10088378720",1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserLabelBean>() {
                    @Override
                    public void accept(UserLabelBean userLabelBean) throws Exception {

                    }
                })
        );

       /* mCompositeDisposable.add(
                Api.getInsttancce().apiService
                        .getKuaidiInfo(companyName, postid, "1")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<KuaidiBean>() {


                            @Override
                            public void accept(KuaidiBean kuaidiBean) throws Exception {
                                if (kuaidiBean.getStatus().equals("200")) {


                                Log.e("biu", "请求了 ");
                                mView.showQueryResult(kuaidiBean.getData().get(0).getContext() + "\n" + kuaidiBean.getData().get(1).getContext());
                                if (kuaidiBean.getData().get(0).getContext().contains("鹿邑") && !received) {
                                    received = true;
                                    mView.sendSMS("17600798778", "你的大鱼卡快递送到了");
                                }
                            }
                            }
                        })
        );
*/
    }

    @Override
    public void getLabelList() {
        mCompositeDisposable.add(Api.getInsttancce()
                .apiService.getLaleList("10088378720",1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserLabelBean>() {
                    @Override
                    public void accept(UserLabelBean userLabelBean) throws Exception {

                    }
                })
        );
    }


}
