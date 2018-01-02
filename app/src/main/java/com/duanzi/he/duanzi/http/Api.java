package com.duanzi.he.duanzi.http;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.duanzi.he.duanzi.App;
import com.duanzi.he.duanzi.base.BaseResult;
import com.duanzi.he.duanzi.util.ActivityManager;
import com.duanzi.he.duanzi.util.UiUtils;
import com.duanzi.he.duanzi.utils.InterceptorHelper;
import com.duanzi.he.duanzi.utils.NetWorkUtil;
import com.example.interceptorhelper.NetLogInterceptor;
import com.example.interceptorhelper.StatusInterceptor;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.platform.Platform;

import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by he on 2017/10/31.
 */

public class Api {
    public static final String baseUrl = "http://testchatapp.lenovo.com.cn/serviceApp/v2/";
    //    public static final String baseUrl = "https://www.kuaidi100.com/";
    public ApiService apiService;

    private Api() {
   /*     HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Platform.get().log(5, Html.fromHtml(message+"<font color=\"#ff000000\">").toString(), null);
            }
        };*/
        NetLogInterceptor.Logger logger = new NetLogInterceptor.Logger() {
            @Override
            public void log(String s) {
                Platform.get().log(5, s, null);
            }
        };
        NetLogInterceptor httpLoggingInterceptor = new NetLogInterceptor(logger);
        httpLoggingInterceptor.setLevel(NetLogInterceptor.Level.BODY);

        StatusInterceptor statusInterceptor = new StatusInterceptor();
        statusInterceptor.setStatuscodeCallbackListener(new StatusInterceptor.OnStatuscodeCallbackListener() {
            @Override
            public void OnStatuscodeCallback(String s) {
                try {
                    JSONObject obj = new JSONObject(s);
                    String statusCode = (String) obj.get("status_code");
                    switch (statusCode) {
                        case "422":
                            break;
                        case "404":
                            break;
                        case "500":
                            break;
                        default:
                            break;


                    }
                    Log.e("结果==", statusCode.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(statusInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())

                .baseUrl(baseUrl)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static Api getInsttancce() {
        return SingleHolder.INSTANCE;
    }

    private static class SingleHolder {
        private static final Api INSTANCE = new Api();
    }

   /* class StatusInterceptor implements Interceptor {

        private final Charset UTF8 = Charset.forName("UTF-8");

        @Override
        public Response intercept(Chain chain) throws IOException {
            if (!NetWorkUtil.isNetConnected(ActivityManager.currentActivity())) {
                return null;
            }

            Request request = chain.request();

            Response response = chain.proceed(request);

            ResponseBody responseBody = response.body();
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (contentLength != 0) {
                String result = buffer.clone().readString(charset);
                //得到所需的string，开始判断是否异常
                Log.e("url==", "" + InterceptorHelper.parseParams(request));
                BaseResult baseResult = new Gson().fromJson(result, BaseResult.class);
                Log.e("url==", "" + baseResult.getMessage());
                Log.e("url==", "" + baseResult.getStatus_code());

                Log.e("结果==", result.toString());
                switch (baseResult.getStatus_code()) {
                    case 423:
//                        RequestBody.create(contentType,)
//                    new Request().newBuilder().put()
                        ActivityManager.currentActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ActivityManager.currentActivity(),"接口错误",Toast.LENGTH_LONG).show();
                            }
                        });
                        break;
                    case 422:



                        return chain.proceed(InterceptorHelper.handlerRequest(request));
                        default:
                }

            }


            return response;


       *//*    return response.newBuilder()
//                    .addHeader(key.value);
                    .body(ResponseBody.create(mediaType, content))
                    .build();*//*
        }


    }*/


}
