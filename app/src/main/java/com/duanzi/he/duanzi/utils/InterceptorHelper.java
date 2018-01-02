package com.duanzi.he.duanzi.utils;

import android.util.Log;

import java.io.EOFException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by he on 2017/11/1.
 */

public class InterceptorHelper {
    /**
     * 解析请求参数
     *
     * @param request
     * @return
     */
    public static Map<String, String> parseParams(Request request) {
        //GET POST DELETE PUT PATCH
        String method = request.method();
        Map<String, String> params = null;
        if ("GET".equals(method)) {
            params = doGet(request);
        } else if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method)) {
            RequestBody body = request.body();
            if (body != null && body instanceof FormBody) {
                params = doForm(request);
            }
        }
        return params;
    }

    /**
     * 获取get方式的请求参数
     *
     * @param request
     * @return
     */
    private static Map<String, String> doGet(Request request) {
        Map<String, String> params = null;
        HttpUrl url = request.url();
        Set<String> strings = url.queryParameterNames();
        if (strings != null) {
            Iterator<String> iterator = strings.iterator();
            params = new HashMap<>();
            int i = 0;
            while (iterator.hasNext()) {
                String name = iterator.next();
                String value = url.queryParameterValue(i);
                params.put(name, value);
                i++;
            }
        }
        return params;
    }

    /**
     * 获取表单的请求参数
     *
     * @param request
     * @return
     */
    private static Map<String, String> doForm(Request request) {
        Map<String, String> params = null;
        FormBody body = null;
        try {
            body = (FormBody) request.body();
        } catch (ClassCastException c) {
        }
        if (body != null) {
            int size = body.size();
            if (size > 0) {
                params = new HashMap<>();
                for (int i = 0; i < size; i++) {
                    params.put(body.name(i), body.value(i));
                    Log.e("key==", body.name(i) + "value==" + body.value(i));
                }
            }
        }
        return params;
    }

    public static Request handlerRequest(Request request) {
        Map<String, String> params = parseParams(request);
        if (params == null) {
            params = new HashMap<>();
        }
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            String value = entry.getValue();
            builder.append(value);
        }
        //这里为公共的参数
        params.put("sign", MD5Util.getInstance().getMD5String(builder.toString() + "SyTz1E5G9xAl2LcNE0R6BZFNZ3WgRJuh"));
        String method = request.method();
        if ("GET".equals(method)) {
            StringBuilder sb = new StringBuilder(request.url().toString());
//            sb.append("?").append(UrlUtil.map2QueryStr(params));
//            return request.newBuilder().url(sb.toString()).build();
        } else if ("POST".equals(method) || "PUT".equals(method) || "DELETE".equals(method) || "PATCH".equals(method)) {
            if (request.body() instanceof FormBody) {
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                Iterator<Map.Entry<String, String>> entryIterator = params.entrySet().iterator();
                while (entryIterator.hasNext()) {
                    Map.Entry<String, String> entry = entryIterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    bodyBuilder.add(key, value);
                }

                return request.newBuilder().put(bodyBuilder.build()).method(method, bodyBuilder.build()).build();
            }
        }
        return request;
    }


    /**
     * 读取Response返回String内容
     *
     * @param response
     * @return
     */
    private String readResponseStr(Response response) {
        ResponseBody body = response.body();
        BufferedSource source = body.source();
        try {
            source.request(Long.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        MediaType contentType = body.contentType();
        Charset charset = Charset.forName("UTF-8");
        if (contentType != null) {
            charset = contentType.charset(charset);
        }
        String s = null;
        Buffer buffer = source.buffer();
        if (isPlaintext(buffer)) {
            s = buffer.clone().readString(charset);
        }
        return s;
    }

    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    /**
     * 自定义Response对象，装载Chain和Response
     */
    public static class CustomResponse {
        public Response response;
        public Interceptor.Chain chain;

        public CustomResponse(Response response, Interceptor.Chain chain) {
            this.response = response;
            this.chain = chain;
        }
    }
}
