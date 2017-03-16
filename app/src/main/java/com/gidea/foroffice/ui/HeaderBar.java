package com.gidea.foroffice.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

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

public class HeaderBar extends RelativeLayout {
    private RelativeLayout.LayoutParams LP_WW = new RelativeLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    private final static int BASE_VIEW_ID = 1000;

    //button
    public final static int LEFT_BUTTON = 0;
    public final static int RIGHT_BUTTON = 1;

    //Logo align
    public final static int LOGO_ALIGN_LEFT = 0;
    public final static int LOGO_ALIGN_CENTER = 1;
    public final static int LOGO_ALIGN_RIGHT = 2;

    private Context mContext;
    //Header 標題
    private String mHeaderTitle = "";
    public void setHeaderTitle(String value){
        mHeaderTitle = value;
        refresh();
    }

    //背景圖片資源
    private int background = -1;
    public void setBackground(int value){
        background = value;
    }
    private int logo = -1;
    public void setLogo(int value){
        logo = value;
    }

    private int mLogoAlign = LOGO_ALIGN_LEFT ;//Left
    public void setLogoAlign(int value){
        mLogoAlign = value;
    }

    //定義事件響應
    private OnHeaderClickListener buttonClickListener;
    private int clickedButton = 0;
    public interface OnHeaderClickListener {
        public void onHeaderClick(int clickButton);
    }

    public void setOnHeaderClickListener(OnHeaderClickListener listener) {
        this.buttonClickListener = listener;
    }

    public int getClickButton(){
        return clickedButton;
    }

    private void refresh(){
        if (getChildCount() > 0){
            removeAllViews();
        }

        //background
        if (background != -1){
            setBackgroundResource(background);
        }

        //logo
        if (logo != -1){
            ImageView imgLogo = new ImageView(mContext);
            LP_WW = new RelativeLayout.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (mLogoAlign == LOGO_ALIGN_LEFT){
                LP_WW.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            }
            else if (mLogoAlign == LOGO_ALIGN_CENTER){
                LP_WW.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            }
            else{
                LP_WW.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            }

            LP_WW.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
            imgLogo.setLayoutParams(LP_WW);
            imgLogo.setBackgroundResource(logo);
            addView(imgLogo);
        }

        //Header Title
        TextView headerTitle = new TextView(mContext);
        LP_WW = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LP_WW.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
        headerTitle.setText(mHeaderTitle);
        headerTitle.setLayoutParams(LP_WW);
        headerTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        addView(headerTitle);

        //buttons 0 left return, 1 right about or other function
        for(int i = 0;i < buttons.size();i ++){
            ButtonItem curItem = getButton(i);
            if (curItem.isVisible()){
                String curTitle = curItem.getTitle();
                View btn;
                if (curTitle.equals("") == false){
                    btn = new Button(mContext);
                    ((Button)btn).setText(curTitle);
                }
                else{
                    btn = new ImageView(mContext);
                }
                btn.setId(BASE_VIEW_ID + i);

                LP_WW = new RelativeLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                //return button left
                if (i == LEFT_BUTTON){
                    LP_WW.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                }
                //About button,right
                else if (i == RIGHT_BUTTON){
                    LP_WW.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                }
                LP_WW.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                btn.setLayoutParams(LP_WW);

                if (clickedButton == i){
                    if (curItem.getClickedBg() != -1){
                        btn.setBackgroundResource(curItem.getClickedBg());
                    }
                }
                else{
                    if (curItem.getDefaultBg() != -1){
                        btn.setBackgroundResource(curItem.getDefaultBg());
                    }
                }
                btn.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        // Do stuff
                        onButtonClicked(v.getId() - BASE_VIEW_ID);
                    }
                });
                addView(btn);
            }
        }
    }

    public void onButtonClicked(int clickButton) {
        if(null != buttonClickListener) {
            clickedButton = clickButton;
            refresh();
            buttonClickListener.onHeaderClick(clickedButton);
        }
    }

    private List<ButtonItem> buttons;
    public ButtonItem getButton(int i){
        if (buttons != null && i < buttons.size()){
            return buttons.get(i);
        }
        else{
            return null;
        }
    }

    /**
     * @param context
     */
    public HeaderBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    /**
     * @param context
     * @param attrs
     */
    public HeaderBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public HeaderBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    public void initHeader(List<ButtonItem> arrBtn, String strTitle, int bg, int lg){
        buttons = arrBtn;

        clickedButton = -1;

        mHeaderTitle = strTitle;

        background = bg;

        logo = lg;

        refresh();
    }

    public void initHeader(List<ButtonItem> arrBtn,String strTitle,int bg){
        initHeader(arrBtn,strTitle,bg,-1);
    }

    public void initHeader(List<ButtonItem> arrBtn,String strTitle){
        initHeader(arrBtn,strTitle,-1,-1);
    }

    public void hide(){
        setVisibility(View.GONE);
    }

    public void show(){
        setVisibility(View.VISIBLE);
    }
}
