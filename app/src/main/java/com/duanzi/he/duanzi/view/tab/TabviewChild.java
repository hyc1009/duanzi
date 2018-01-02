package com.duanzi.he.duanzi.view.tab;


import android.support.v4.app.Fragment;

/**
 * Created by he on 2017/10/18.
 */

public class TabviewChild {

    public Fragment getmFragment() {
        return mFragment;
    }

    public void setmFragment(Fragment mFragment) {
        this.mFragment = mFragment;
    }

    public String getTextViewText() {
        return textViewText;
    }

    public void setTextViewText(String textViewText) {
        this.textViewText = textViewText;
    }

    public int getImageViewSelIcon() {
        return imageViewSelIcon;
    }

    public void setImageViewSelIcon(int imageViewSelIcon) {
        this.imageViewSelIcon = imageViewSelIcon;
    }

    public int getImageViewUnselIcon() {
        return imageViewUnselIcon;
    }

    public void setImageViewUnselIcon(int imageViewUnselIcon) {
        this.imageViewUnselIcon = imageViewUnselIcon;
    }

    private  Fragment mFragment;
    private  String textViewText;
    private  int imageViewSelIcon;
    private  int imageViewUnselIcon;

    public TabviewChild(int imageViewSelIcon, int imageViewUnselIcon, String textViewText, Fragment mFragment) {
        this.imageViewSelIcon = imageViewSelIcon;
        this.imageViewUnselIcon = imageViewUnselIcon;
        this.textViewText = textViewText;
        this.mFragment = mFragment;
    }


}
