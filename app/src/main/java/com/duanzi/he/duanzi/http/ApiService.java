package com.duanzi.he.duanzi.http;

import android.animation.TypeEvaluator;
import android.view.View;
import android.view.ViewGroup;

import com.duanzi.he.duanzi.model.KuaidiBean;
import com.duanzi.he.duanzi.model.UserLabelBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by he on 2017/10/31.
 */

public interface ApiService {
    //获取所有的兴趣标签列表
    @POST("tag/gettaglists")
    @FormUrlEncoded
    Flowable<UserLabelBean> getLaleList(@Field("uid") String uid, @Field("id_type") int type);
    //获取快递信息
    @GET("query")
    Observable<KuaidiBean> getKuaidiInfo(@Query("type") String type, @Query("postid") String postid, @Query("id") String id);
}
