package com.duanzi.he.duanzi.ui;

import android.util.Log;

import com.chao.he.mlib.RotateInject;
import com.duanzi.he.duanzi.R;
import com.duanzi.he.duanzi.base.BaseActivity;
import com.duanzi.he.duanzi.databinding.ActivityMainBinding;
import com.duanzi.he.duanzi.ui.homepage.HomePageFragment;
import com.duanzi.he.duanzi.ui.minepage.MineFragment;
import com.duanzi.he.duanzi.view.tab.TabviewChild;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements MainContract.View {


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;

    }

    @Override
    @RotateInject(value = 1)
    public void initView() {
        /*try {
            Class class_=Class.forName("com.duanzi.he.duanzi.ui.MainActivity");
            Annotation annotation = class_.getAnnotation(RotateInject.class);
            if (annotation !=null && annotation instanceof RotateInject) {
                Log.e("he==",""+((RotateInject) annotation).value()+((RotateInject) annotation).value());
                System.out.println();
            }
            for (Method method : class_.getDeclaredMethods()) {
                RotateInject methodAnnotation=method.getAnnotation(RotateInject.class);
                if (methodAnnotation!=null) {
                    System.out.println(methodAnnotation.value());
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/




//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ArrayList<TabviewChild> tabChildList = new ArrayList<>();
        TabviewChild tabViewChild01 = new TabviewChild(R.drawable.tab01_sel, R.drawable.tab01_unsel, "首页", new HomePageFragment());
        TabviewChild tabViewChild02 = new TabviewChild(R.drawable.tab02_sel, R.drawable.tab02_unsel, "我的", new MineFragment());
        tabChildList.add(tabViewChild01);
        tabChildList.add(tabViewChild02);
        mViewBinding.tabview.setDefaultPosition(0);
        mViewBinding.tabview.setTabChiildView(tabChildList, getSupportFragmentManager());
    /*           Observable.create(new ObservableOnSubscribe<Integer>() {

            // 1. 被观察者发送事件 = 参数为整型 = 1、2、3
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);

            }
            // 2. 使用Map变换操作符中的Function函数对被观察者发送的事件进行统一变换：整型变换成字符串类型
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "何云超" ;
            }
        }).subscribe(new Consumer<String>() {

            // 3. 观察者接收事件时，是接收到变换后的事件 = 字符串类型
            @Override
            public void accept(String s) throws Exception {
                Log.e("yun", s);
            }
        });*/
        mPresenter.showlog();

        int[] array = {5,2,0,1,3,1,4};

insertSort(array);

    }
    public static void insertSort(int[] array) {
        //从第2个开始往前插
        for (int i = 1, n = array.length; i < n; i++) {
            int temp = array[i];//保存第i个值
            int j = i - 1;//从有序数组的最后一个开始
            for (; j >= 0 && array[j] > temp; j--) {
                array[j + 1] = array[j];//从后往前比较，大于temp的值都得后移
            }
            array[j + 1] = temp;//碰到小于或等于的数停止，由于多减了1，所以加上1后，赋值为插入值temp
        }
        Log.e("排序=","直接插入排序后：" + Arrays.toString(array));
    }
}




