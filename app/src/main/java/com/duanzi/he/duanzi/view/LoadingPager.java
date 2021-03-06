package com.duanzi.he.duanzi.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.duanzi.he.duanzi.util.UiUtils;

/**
 * Created by he on 2017/10/23.
 */

public abstract class LoadingPager extends FrameLayout{
    // 加载默认的状态
    private static final int STATE_UNLOADED = 1;
    // 加载的状态
    private static final int STATE_LOADING = 2;
    // 加载失败的状态
    private static final int STATE_ERROR = 3;
    // 加载空的状态
    private static final int STATE_EMPTY = 4;
    // 加载成功的状态
    private static final int STATE_SUCCEED = 5;
    private View mLoadingView;// 转圈的view
    private View mErrorView;// 错误的view
    private View mEmptyView;// 空的view
    private View mSucceedView;// 成功的view
    private int mState;// 默认的状态
    private int loadpage_empty;
    private int loadpage_error;
    private int loadpage_loading;

    public LoadingPager(Context context, int loading, int error, int empty) {
        super(context);
        loadpage_empty = empty;
        loadpage_error = error;
        loadpage_loading = loading;
        init();
    }

    public LoadingPager(Context context, AttributeSet attrs, int defStyle,int loading, int error, int empty) {
        super(context, attrs, defStyle);
        loadpage_empty = empty;
        loadpage_error = error;
        loadpage_loading = loading;
        init();
    }

    public LoadingPager(Context context, AttributeSet attrs, int loading,  int error, int empty) {
        super(context, attrs);
        init();
    }
    private void init() {
        // 初始化状态
        mState = STATE_UNLOADED;
        // 初始化三个状态的view 这个时候 三个状态的view叠加在一起了
        mLoadingView = createLoadingView();
        if (null != mLoadingView) {
            addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        mErrorView = createErrorView();
        if (null != mErrorView) {
            addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        mEmptyView = createEmptyView();
        if (null != mEmptyView) {
            addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        showSafePagerView();
    }
    private void showSafePagerView() {
        // 直接运行到主线程
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPagerView();
            }
        });
    }
    private void showPagerView() {

        // 這個時候 都不為空 mState默認是STATE_UNLOADED狀態所以只顯示 lodaing 下面的 error
        // 和empty暂时不显示
        if (null != mLoadingView) {
            mLoadingView.setVisibility(mState == STATE_UNLOADED
                    || mState == STATE_LOADING ? View.VISIBLE :View.INVISIBLE);
        }
        if (null != mErrorView) {
            mErrorView.setVisibility(mState == STATE_ERROR ? View.VISIBLE: View.INVISIBLE);
        }
        if (null != mEmptyView) {
            mEmptyView.setVisibility(mState == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        }

        if (mState == STATE_SUCCEED && mSucceedView == null) {
            mSucceedView = createSuccessView();
            addView(mSucceedView, new LayoutParams
                    (LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        if (null != mSucceedView) {
            mSucceedView.setVisibility(mState == STATE_SUCCEED ? View.VISIBLE : View.INVISIBLE);
        }
    }
    public void show() {
        // 第一次进来肯定要 转圈的 所以就算是 error和empty 也要让状态是 unload
        if (mState == STATE_ERROR || mState == STATE_EMPTY) {
            mState = STATE_UNLOADED;
        }
        // 如果是unload 就把状态 变为 loading了 这时候从服务器拿数据
        if (mState == STATE_UNLOADED) {
            mState = STATE_LOADING;
            final LoadResult loadResult = load();
//            SystemClock.sleep(500);
            UiUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mState = loadResult.getValue();
                    showPagerView();
                }
            });
//            TaskRunnable task = new TaskRunnable();
//            ThreadManager.getLongPool().execute(task);
        }
        showSafePagerView();
    }
    /**
     * 制作界面
     *
     * @return
     */
    protected abstract View createSuccessView();

    /**
     * 处理下载 耗时操作
     *
     * @return
     */
    protected abstract LoadResult load();

    /**
     * 空界面
     *
     * @return
     */
    public View createEmptyView() {
        if (loadpage_empty != 0) {
            return UiUtils.inflate(loadpage_empty);
        }
        return null;

    }

    /**
     * 失败的页面
     *
     * @return
     */
    public View createErrorView() {
        if (loadpage_empty != 0) {
            return UiUtils.inflate(loadpage_error);
        }
        return null;
    }

    /**
     * 正在旋转的页面
     *
     * @return
     */
    public View createLoadingView() {
        if (loadpage_empty != 0) {
            return UiUtils.inflate(loadpage_loading);
        }
        return null;
    }

    class TaskRunnable implements Runnable {
        @Override
        public void run() {
            final LoadResult loadResult = load();
//            SystemClock.sleep(500);
            UiUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mState = loadResult.getValue();
                    showPagerView();
                }
            });
        }
    }
    public enum LoadResult {
        ERROR(3), EMPTY(4), SUCCESS(5);
        int value;

        LoadResult(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
}