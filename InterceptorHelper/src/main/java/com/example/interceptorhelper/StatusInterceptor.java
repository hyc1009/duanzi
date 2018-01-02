package com.example.interceptorhelper;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import sun.rmi.runtime.Log;

/**
 * Created by he on 2017/11/29.
 */

public class StatusInterceptor implements Interceptor {

    private final Charset UTF8 = Charset.forName("UTF-8");
    private OnStatuscodeCallbackListener mStatuscodeCallbackListener;

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
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
            if (mStatuscodeCallbackListener != null) {
                mStatuscodeCallbackListener.OnStatuscodeCallback(result);
            }
        }


        return response;
    }

    public interface OnStatuscodeCallbackListener {
        void OnStatuscodeCallback(String result);
    }

    public void setStatuscodeCallbackListener(OnStatuscodeCallbackListener listener) {
        mStatuscodeCallbackListener = listener;
    }
}
