package com.gidea.foroffice.ui;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　┃
 * 　　┃　　　━　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　┣┓
 * 　　　　┃　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * 作者:jalong Han
 * 邮箱:hjl999@126.com
 * 时间:2017/3/16
 * 功能:
 */

public class ButtonItem{
    public ButtonItem(String title){
        this(title,-1,-1,true);
    }
    public ButtonItem(String title,int defaultBg){
        this(title,defaultBg,-1,true);
    }
    public ButtonItem(String title,int defaultBg,int clickedBg){
        this(title,defaultBg,clickedBg,true);
    }
    public ButtonItem(String title,int defaultBg,int clickedBg,boolean isVisible){
        mTitle = title;
        mDefaultBg = defaultBg;
        mClickedBg = clickedBg;
        mVisible = isVisible;
    }

    boolean mVisible = true;
    public void setVisible(boolean value){
        mVisible = value;
    }
    public boolean isVisible(){
        return mVisible;
    }

    private String mTitle = "";
    public String getTitle(){
        return mTitle;
    }
    public void setTitle(String value){
        mTitle = value;
    }

    private int mDefaultBg = -1;
    public int getDefaultBg(){
        return mDefaultBg;
    }
    public void setDefaultBg(int value){
        mDefaultBg = value;
    }

    private int mClickedBg = -1;
    public int getClickedBg(){
        return mClickedBg;
    }
    public void setClickedBg(int value){
        mClickedBg = value;
    }
}