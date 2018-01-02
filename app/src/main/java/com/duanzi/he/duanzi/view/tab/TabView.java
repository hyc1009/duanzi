package com.duanzi.he.duanzi.view.tab;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.duanzi.he.duanzi.R;
import com.duanzi.he.duanzi.util.WindowUtils;
import com.duanzi.he.duanzi.view.tab.TabviewChild;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by he on 2017/10/12.
 *
 */

public class TabView extends RelativeLayout {

    private static final int RMP = RelativeLayout.LayoutParams.MATCH_PARENT;
    private static final int RWC = RelativeLayout.LayoutParams.WRAP_CONTENT;
    private static final int LMP = LinearLayout.LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;


    private int mTextviewSelectColor; //被选中的字体颜色
    private int mTextviewUnselectColor; //未选中的字体颜色
    private int mTabViewBackgroundColor; //导航栏的背景颜色
    private int mImageviewTextviewMargin;//导航栏的字体和图片的间隔
    private int mImageviewTopMargin;//导航栏的图片和顶部的间隔
    private int mTabviewheight; //导航栏的高度
    private int mTtextSize;//字体大小
    private int mImageviewWidth; //导航图片的宽
    private int mImageviewHeight; //导航图片的高
    private int mTabGravity = Gravity.BOTTOM; //导航的方向
    private int mTabviewDefaultPosition = 0; //默认选中的位置
    private List<TabviewChild> tabviewChildList;
    private FragmentManager supportFragmentManager;
    private int index;
    private int currentTabIndex;
    private LinearLayout tabview;
    private FrameLayout fragmentcontainer;
    private ArrayList<Integer> unSelectIconList;
    private Fragment[] mFragments;

    public TabView(Context context) {
        super(context);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDefaultAttrs(context);
        initCustomAttrs(context,attrs);
        initView(context);
    }



    /**
     * 初始化默认属性
     * @param context
     */
    private void initDefaultAttrs(Context context) {
        mTextviewSelectColor = Color.rgb(223,0,41);
        mTextviewUnselectColor = Color.rgb(54,54,54);
        mTabViewBackgroundColor = Color.rgb(88,200,88);
        mImageviewTextviewMargin = WindowUtils.dp2px(context,2);
        mImageviewTopMargin = WindowUtils.dp2px(context,2);
        mImageviewHeight = WindowUtils.dp2px(context,30);
        mImageviewWidth = WindowUtils.dp2px(context,30);
        mTabviewheight = WindowUtils.dp2px(context,52);
        mTtextSize = WindowUtils.sp2px(context,14);
    }

    /**
     * 初始化自定义属性
     * @param context
     * @param attrs
     */
    private void initCustomAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabView);
        int count = typedArray.getIndexCount();

        for (int i = 0; i < count; i++) {
            initCustomAttr(typedArray.getIndex(i),typedArray);
        }
        typedArray.recycle();
    }


    /**
     * 对每个属性赋值
     * @param index
     * @param typedArray
     */
    private void initCustomAttr(int index,  TypedArray typedArray) {
        if (index == R.styleable.TabView_tab_textViewSelectColor) {
            mTextviewSelectColor = typedArray.getColor(index,mTextviewSelectColor);
        } else if (index == R.styleable.TabView_tab_textViewUnselectColor) {
            mTextviewUnselectColor = typedArray.getColor(index,mTextviewUnselectColor);
        } else if (index == R.styleable.TabView_tab_tabviewBackgroundColor) {
            mTabViewBackgroundColor = typedArray.getColor(index,mTabViewBackgroundColor);
        } else if (index == R.styleable.TabView_tab_imageviewTextviewMargin) {
            mImageviewTextviewMargin = typedArray.getDimensionPixelSize(index,mImageviewTextviewMargin);
        } else if (index == R.styleable.TabView_tab_imageviewTopMargin) {
            mImageviewTopMargin = typedArray.getDimensionPixelSize(index,mImageviewTopMargin);
        } else if (index == R.styleable.TabView_tabviewHeight) {
            mTabviewheight = typedArray.getDimensionPixelSize(index,mTabviewheight);
        } else if (index == R.styleable.TabView_tab_textviewSize) {
            mTtextSize = typedArray.getDimensionPixelSize(index,mTtextSize);
        } else if (index == R.styleable.TabView_tab_imageviewWidth) {
            mImageviewWidth = typedArray.getDimensionPixelSize(index,mImageviewWidth);
        } else if (index == R.styleable.TabView_tab_imageviewHeight) {
            mImageviewHeight = typedArray.getDimensionPixelOffset(index,mImageviewHeight);
        } else if (index == R.styleable.TabView_tabview_gravity) {
            mTabGravity = typedArray.getInt(index,mTabGravity);
        } else if (index == R.styleable.TabView_tabview_defaultposition) {
            mTabviewDefaultPosition = typedArray.getInteger(index,mTabviewDefaultPosition);
        }
    }

    /**
     * 初始化view
     * @param context
     */
    private void initView(Context context) {
        tabview = new LinearLayout(context);
        tabview.setId(R.id.tabview_id);
        fragmentcontainer = new FrameLayout(context);
        fragmentcontainer.setId(R.id.tabview_fragment_container);
        RelativeLayout.LayoutParams fragmentContainerParams = new RelativeLayout.LayoutParams(RMP, RMP);
        RelativeLayout.LayoutParams tabviewParams = null;
        if (mTabGravity == Gravity.BOTTOM) {
            tabviewParams = new RelativeLayout.LayoutParams(RMP,mTabviewheight);
            tabview.setOrientation(LinearLayout.HORIZONTAL);
            tabviewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            fragmentContainerParams.addRule(RelativeLayout.ABOVE,R.id.tabview_id);
        }
        tabview.setLayoutParams(tabviewParams);
        fragmentcontainer.setLayoutParams(fragmentContainerParams);
        tabview.setBackgroundColor(mTabViewBackgroundColor);
        this.addView(tabview);
        this.addView(fragmentcontainer);
    }

    public void setTabChiildView(List<TabviewChild> tabviewChildList, FragmentManager supportFragmentManager) {
        this.tabviewChildList = tabviewChildList;
        this.supportFragmentManager = supportFragmentManager;
        if (mTabviewDefaultPosition >= tabviewChildList.size()) {
            index = 0;
            currentTabIndex = 0;
            mTabviewDefaultPosition = 0;
        }
        initTabChildView();
    }

    private void initTabChildView() {
        tabview.removeAllViews();
        unSelectIconList = new ArrayList<>();
        mFragments = new Fragment[tabviewChildList.size()];
        for (int i = 0; i < mFragments.length; i++) {
            TabviewChild tabviewChild = tabviewChildList.get(i);
            mFragments[i]  = tabviewChild.getmFragment();
        }
        if (mTabviewDefaultPosition >= tabviewChildList.size()) {
            supportFragmentManager.beginTransaction().add(R.id.tabview_fragment_container, mFragments[0]).show(mFragments[0]).commit();
        } else {
            supportFragmentManager.beginTransaction().add(R.id.tabview_fragment_container, mFragments[mTabviewDefaultPosition]).show(mFragments[mTabviewDefaultPosition]).commit();
        }

        for (int i = 0; i <tabviewChildList.size(); i++) {
            final TabviewChild tabviewChild = tabviewChildList.get(i);
            LinearLayout tabChild = new LinearLayout(getContext());
            tabChild.setGravity(Gravity.CENTER);
            tabChild.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams tabChildParams = null;
            if (mTabGravity == Gravity.BOTTOM) {
                tabChildParams = new LinearLayout.LayoutParams(0,LMP,1.0f);
                tabChildParams.gravity = Gravity.CENTER_HORIZONTAL;
            }
            tabChild.setLayoutParams(tabChildParams);

            /**
             * 导航的tab图标
             */
            final ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams imageviewParams = new LinearLayout.LayoutParams(mImageviewWidth, mImageviewHeight);
            imageviewParams.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
            imageView.setImageResource(tabviewChild.getImageViewUnselIcon());
            unSelectIconList.add(tabviewChild.getImageViewUnselIcon());
            imageviewParams.topMargin = mImageviewTopMargin;
            imageView.setLayoutParams(imageviewParams);
            tabChild.addView(imageView);

            /**
             * 导航的tab文字
             */
            final TextView textView = new TextView(getContext());
            textView.setText(tabviewChild.getTextViewText());
            textView.setTextColor(mTextviewUnselectColor);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,mTtextSize);
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(LWC, LWC);
            textViewParams.gravity = Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL;
            textViewParams.topMargin = mImageviewTextviewMargin;
            textView.setLayoutParams(textViewParams);
            tabChild.addView(textView);
            tabview.addView(tabChild);

            final int currentPosition = i;
            if (mTabviewDefaultPosition >= tabviewChildList.size()) {
                if (i == 0) {
                    imageView.setImageResource(tabviewChild.getImageViewSelIcon());
                    textView.setTextColor(mTextviewSelectColor);

                }else {
                    imageView.setImageResource(tabviewChild.getImageViewSelIcon());
                    textView.setTextColor(mTextviewSelectColor);
                }
            }

            tabChild.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (currentPosition == index) {
                        return;
                    }
                    restAll();
                    imageView.setImageResource(tabviewChild.getImageViewSelIcon());
                    ObjectAnimator.ofFloat(imageView,"scaleX",0.0F,1.0F).start();
                    ObjectAnimator.ofFloat(imageView,"scaleY",0.0F,1.0F).start();
                    textView.setText(tabviewChild.getTextViewText());
                    textView.setTextColor(mTextviewSelectColor);
                    index = currentPosition;
                    showOrHide();
                    if (listener != null) {
                        listener.onTabChildClick(currentPosition,imageView,textView);
                    }

                }
            });
        }
    }

    /**
     * 根据tab的选中状态，展示或者隐藏fragment
     */
    private void showOrHide() {
            if (currentTabIndex != index) {
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                fragmentTransaction.hide(mFragments[currentTabIndex]);
                if (!mFragments[index].isAdded()) {
                    fragmentTransaction.add(R.id.tabview_fragment_container,mFragments[index]);
                }
                fragmentTransaction.show(mFragments[index]).commitAllowingStateLoss();
                currentTabIndex = index;
            }
    }

    /**
     * 重置tabview的状态
     */
    private void restAll() {

        for (int i = 0; i <tabview.getChildCount(); i++) {
            LinearLayout tabviewChild = (LinearLayout) tabview.getChildAt(i);
            for (int j = 0; j <tabviewChild.getChildCount(); j++) {
                ImageView imageView = (ImageView)tabviewChild.getChildAt(0);
                TextView textView = (TextView)tabviewChild.getChildAt(1);
                imageView.setImageResource(unSelectIconList.get(i));
                textView.setTextColor(mTextviewUnselectColor);
            }
        }
    }



    public void setDefaultPosition (int position) {
        currentTabIndex = position;
        mTabviewDefaultPosition = position;
        index = position;
    }
    private OnTabChildClickListener listener;
    public void setOnTabChildClickListener(OnTabChildClickListener l) {
        listener = l;
    }
    public interface OnTabChildClickListener {

        void onTabChildClick(int positon, ImageView imageView, TextView textView);
    }


}
