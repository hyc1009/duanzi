package com.duanzi.he.duanzi.util;

/**
 * 和Ui相关的工具类
 */

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.duanzi.he.duanzi.App;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UiUtils {
    // 资源类
    public static Resources getResource() {
        return App.getResource();
    }

    /* 得到上下文 */
    public static Context getContext() {
        return App.getContext();
    }

    /**
     * 获取到string字符数组
     *
     * @param tabNames 字符数组id
     * @return
     */
    public static String[] getStringArray(int tabNames) {
        return getResource().getStringArray(tabNames);
    }

    /**
     * dip转换px
     *
     * @param dip
     * @return
     */
    public static int dip2px(int dip) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换dip
     *
     * @param px
     * @return
     */
    public static int px2dip(int px) {
        final float scale = getResource().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        final float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 把Runnable 提交到主线程
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        // 在主线程运行
        if (android.os.Process.myTid() == App.getMainThreadId()) {
            runnable.run();
        } else {
            //获取handler
            App.getMainHandler().post(runnable);
        }
    }

    /**
     * 绑定布局文件
     * <p/>
     * Fragment的initView()可使用
     *
     * @param id layout布局文件
     * @return
     */
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    /**
     * 得到图片 - use Resource by id
     *
     * @param id
     * @return
     */
    public static Drawable getDrawalbe(int id) {
        return getResource().getDrawable(id);
    }

    /**
     * 得到颜色 - use Resource by id
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return getResource().getColor(id);
    }

    /**
     * 获取dimens中的值 - use Resource by id
     *
     * @param homePictureHeight id
     * @return
     */
    public static int getDimens(int homePictureHeight) {
        return (int) getResource().getDimension(homePictureHeight);
    }

    /**
     * 验证手机号
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobile(String mobiles) {
        Pattern pattern = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(16[0-9]))\\d{8}$");
        Matcher m = pattern.matcher(mobiles);
        return m.matches();
    }

    /**
     * 显示Toast
     *
     * @param message
     */
    public static void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 得到屏幕宽度
     *
     * @return px
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);// 取得窗口属性

        return dm.widthPixels;
    }

    /**
     * 得到屏幕高度
     *
     * @return px
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);// 取得窗口属性

        return dm.heightPixels;
    }

    /**
     * 图片高度(宽度全屏,得到图片的显示高度)
     * <p/>
     * 通过比例公式:screenWidth/x = 2/1 (微绘图片规定尺寸)
     *
     * @return
     */
    public static int getPicShowHeight() {
        int h = Math.round((float) (getScreenWidth() * 1) / (float) 2);
        return h;
    }

    /**
     * 图片高度(宽度输入,得到图片的显示高度)
     * <p/>
     * 通过比例公式:screenWidth/x = 750/560 (微绘图片规定尺寸)
     *
     * @return
     */
    public static int getPicShowHeight(int width) {
        int h = Math.round((float) (width * 560) / (float) 750);
        return h;
    }

    /**
     * 得到状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResource().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResource().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 判断是否有网络
     */
    public static boolean isNetworkAvailable() {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
//                    LogUtils.d("当前网络:类型:" + networkInfo[i].getTypeName() + "===状态:" + networkInfo[i].getState());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 得到标签主题Drawable
     *
     * @param type 需要类型：1、2、3、4、5
     * @return
    /* *//*
    public static Drawable getLabelDrawable(String type) {
        if (type.equals("1")) {
            return UiUtils.getDrawalbe(R.drawable.shape_label_text_1);
        } else if (type.equals("2")) {
            return UiUtils.getDrawalbe(R.drawable.shape_label_text_2);
        } else if (type.equals("3")) {
            return UiUtils.getDrawalbe(R.drawable.shape_label_text_3);
        } else if (type.equals("4")) {
            return UiUtils.getDrawalbe(R.drawable.shape_label_text_4);
        } else if (type.equals("5")) {
            return UiUtils.getDrawalbe(R.drawable.shape_label_text_5);
        } else {
            return UiUtils.getDrawalbe(R.drawable.shape_label_text_1);
        }
    }*/

    /**
     * 得到标签字体颜色
     *
     * @param type 需要类型：1、2、3、4、5
     * @return
     */
  /*  public static int getLabelColor(String type) {
        if (type.equals("1")) {
            return UiUtils.getColor(R.color.label_text_1_text);
        } else if (type.equals("2")) {
            return UiUtils.getColor(R.color.label_text_2_text);
        } else if (type.equals("3")) {
            return UiUtils.getColor(R.color.label_text_3_text);
        } else if (type.equals("4")) {
            return UiUtils.getColor(R.color.label_text_4_text);
        } else if (type.equals("5")) {
            return UiUtils.getColor(R.color.label_text_5_text);
        } else {
            return UiUtils.getColor(R.color.label_text_1_text);
        }
    }*/
}
