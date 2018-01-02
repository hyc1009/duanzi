package com.duanzi.he.duanzi;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.duanzi.he.duanzi.util.ActivityManager;

/**
 * Created by he on 2017/10/19.
 */

public class App extends Application {

    private static Context mContext;
    private static Thread mMainThread;
    private static long mMainThreadId;
    private static Looper mMainLooper;
    private static Handler mMainHandler;
    private static Resources mResource;

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(ActivityManager.getActivityLifecycleCallbacks());

        // 上下文
        mContext = getApplicationContext();
        // 主线程
        mMainThread = Thread.currentThread();
        // mMainThreadId = mMainThread.getId();
        mMainThreadId = android.os.Process.myTid();
        mMainLooper = getMainLooper();
        // 创建主线程的handler
        mMainHandler = new Handler();
        mResource = getResources();
    }

    public static Context getContext() {
        return mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainLooper;
    }

    public static Handler getMainHandler() {
        return mMainHandler;
    }

    public static Resources getResource() {
        return mResource;
    }

}
