package com.duanzi.he.duanzi.util;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by he on 2017/10/16.
 */

public class WindowUtils {

    public static int dp2px (Context context, float dpValue) {
       return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context,float spValue) {
        return  (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,spValue,context.getResources().getDisplayMetrics());
    }

}
