package com.duanzi.he.duanzi.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.Stack;

/**
 * Created by he on 2017/11/7.
 */

public class ActivityManager {

    private static Stack<Activity> activityStack = new Stack<>();
    private static final ActivityLifecycleCallbacks instance = new ActivityLifecycleCallbacks();

    private static class ActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activityStack.push(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activityStack.remove(activity);
        }
    }

    public static Application.ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return instance;
    }

    /**
     * 获得当前栈顶Activity
     *
     * @return
     */
    public static Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.isEmpty()){

            activity = activityStack.peek();
        }
        return activity;
    }

    /**
     * 主动退出Activity
     *
     * @param activity
     */
    public static void closeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 关闭所有的Activity
     */
    public void closeAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (null == activity) {
                break;
            }
            closeActivity(activity);
        }
    }

    /**
     * 通过Activity的全类名关闭Activity
     *
     * @param name 例如: com.jude.utils.Activity_B
     */
    public static void closeActivityByName(String name) {
        int index = activityStack.size() - 1;

        while (true) {
            Activity activity = activityStack.get(index);

            if (null == activity) {
                break;
            }

            String activityName = activity.getComponentName().getClassName();
            if (!TextUtils.equals(name, activityName)) {
                index--;
                if (index < 0) {//avoid index out of bound.
                    break;
                }
                continue;
            }
            closeActivity(activity);
            break;
        }
    }

    /**
     * 获得当前ACTIVITY 名字
     */
    public static String getCurrentActivityName() {
        Activity activity = currentActivity();
        String name = "";
        if (activity != null) {
            name = activity.getComponentName().getClassName().toString();
        }
        return name;
    }

    /**
     * 获得Activity的栈
     *
     * @return
     */
    public static Stack<Activity> getActivityStack() {
        Stack<Activity> stack = new Stack<>();
        stack.addAll(activityStack);
        return stack;
    }


}
