package com.gidea.foroffice.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gidea.foroffice.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity
        implements AdapterView.OnItemClickListener {

    protected ListView list;
    protected List<String> types;
    protected List<String> files;

    private void setOfficeTypeList(){
        types = new ArrayList<String>();
        types.add("word2003");
        types.add("word2007");
        types.add("excel2003");
        types.add("excel2007");
        types.add("ppt2003");
        types.add("ppt2007");
        types.add("word2003");


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView)findViewById(R.id.office_list);
        setOfficeTypeList();
        list.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                types));
        list.setOnItemClickListener(this);

        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_main, menu);
//        return true;
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<String> getSplitString(String strText){
        ArrayList<String> data = new ArrayList<String>();
        String[] textArray = strText.split("\n");

        for(int i = 0;i < textArray.length;i ++){
            data.add(textArray[i]);
        }

        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int i, long l) {
        // TODO Auto-generated method stub
        Log.i("item", "selected " + types.get(i));
        ArrayList<String> contentData = new ArrayList<String>();
        //Word 2003
        if (i == 0){
            try {
                WordContent word = OfficeReader.readWord2003(this, "poems100.doc");
                word.showContent();
                contentData = word.getContentData();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //word 2007
        else if (i == 1){
            try {
                String strText = OfficeReader.readWord2007(this, "phpframework.docx");
                Log.i("Content", strText);
                contentData = getSplitString(strText);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //Excel 2003
        else if (i == 2){
            try {
                ExcelContent excel = OfficeReader.readExcel2003(this, "apmfr.xls");
                excel.showContent();
                contentData = excel.getContentData();
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //Excel 2007
        else if (i == 3){
            try {
                String strText = OfficeReader.readExcel2007(this, "fubushi.xlsx");
                Log.i("Content", strText);
                contentData = getSplitString(strText);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //Powerpoint 2003
        else if (i == 4){
//			try {
//				String strText = OfficeReader.readPowerpoint2003(this, "be_best_youself.ppt");
//				Log.i("Content", strText);
//
//			}
//			catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
            Toast.makeText(MainActivity.this,
                    "暂不支持", Toast.LENGTH_LONG)
                    .show();

            return;
        }
        //Powerpoint 2007
        else if (i == 5){
            try {
                String strText = OfficeReader.readPowerpoint2007(this, "be_best_youself.pptx");
                //String strText = OfficeReader.readPowerpoint2007(this, "be_best_youself.pptx");
                Log.i("Content", strText);
                contentData = getSplitString(strText);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //Visio
        else if (i == 6){
            try {
                String strText = OfficeReader.readVisio(this, "project_flow.vsd");
                Log.i("Content", strText);
                contentData = getSplitString(strText);
            }
            catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (contentData.size() > 0){
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ContentActivity.class);
            intent.putExtra(ContentActivity.CONTENT_TITLE, types.get(i));
//            intent.putExtra(ContentActivity.FILE_NAME, files.get(i));
            intent.putStringArrayListExtra(ContentActivity.CONTENT_DATA, contentData);

            startActivity(intent);
        }
    }

}
