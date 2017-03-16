package com.gidea.foroffice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.gidea.foroffice.R;
import com.gidea.foroffice.ui.ButtonItem;
import com.gidea.foroffice.ui.HeaderBar;

import java.util.ArrayList;
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

public class ContentActivity extends Activity implements HeaderBar.OnHeaderClickListener {
    public final static String CONTENT_TITLE = "content_title";
    public final static String FILE_NAME = "file_name";
    public final static String CONTENT_DATA = "content_data";

    private List<ButtonItem> getHeaderBarItems(){
        List<ButtonItem> result = new ArrayList<ButtonItem>();

        result.add(new ButtonItem("", R.drawable.back, R.drawable.backh,true));

        return result;
    }

    private void initHeaderBar(String strFileName){
        HeaderBar header = (HeaderBar)findViewById(R.id.layout_header_bar);
        header.setLogoAlign(HeaderBar.LOGO_ALIGN_CENTER);
        //header.initHeader(getHeaderBarItems(), "",R.drawable.szx_title_bg,R.drawable.szx_logo);
        header.initHeader(getHeaderBarItems(), strFileName,-1,-1);
        header.setOnHeaderClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String title = intent.getStringExtra(CONTENT_TITLE);
        String file = intent.getStringExtra(FILE_NAME);
        List<String> data = intent.getStringArrayListExtra(CONTENT_DATA);

        setContentView(R.layout.activity_content);
        setTitle(title);

        initHeaderBar(file);

        ListView list = (ListView)findViewById(R.id.content_list);
        list.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                data));
    }

    @Override
    public void onHeaderClick(int clickButton) {
        // TODO Auto-generated method stub
        finish();
    }
}

