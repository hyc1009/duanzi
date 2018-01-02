package com.duanzi.he.duanzi.ui.homepage;

import android.Manifest;
import android.animation.PointFEvaluator;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;

import com.duanzi.he.duanzi.R;
import com.duanzi.he.duanzi.base.BaseFragment;
import com.duanzi.he.duanzi.databinding.FragmentHomeBinding;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;


/**
 * Created by he on 2017/10/20.
 */

public class HomePageFragment extends BaseFragment<HomePresenter, FragmentHomeBinding> implements HomeContract.View {


    @Override
    protected void onLazyLoad() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() throws ClassNotFoundException {
//        getPostid();
        mPresenter.getLabelList();
    }

    @Override
    public void getPostid() {
        binding.query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getDeliveryInfo(binding.company.getText().toString(), binding.postid.getText().toString());
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        mPresenter.getDeliveryInfo(binding.company.getText().toString(), binding.postid.getText().toString());
                    }
                },1000,5000);
            }
        });

    }


    @Override
    public void showQueryResult(String result) {
        binding.result.setText(result);
    }

    @Override
    public void sendSMS(String phoneNumber,String message){

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 1);
        }


//        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, testSms.class), 0);

        SmsManager sms = SmsManager.getDefault();

        sms.sendTextMessage(phoneNumber, null, message, null, null);

    }

}
