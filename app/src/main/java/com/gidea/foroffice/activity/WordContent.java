package com.gidea.foroffice.activity;

import android.util.Log;

import java.util.ArrayList;

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

public class WordContent {
    private String fileName;
    public String getFileName(){
        return fileName;
    }
    public void setFileName(String value){
        fileName = value;
    }

    private String text;
    public String getText(){
        return text;
    }
    public void setText(String value){
        text = value;
    }

    private String textFromPieces;
    public String getTextFromPieces(){
        return textFromPieces;
    }
    public void setTextFromPieces(String value){
        textFromPieces = value;
    }

    private String[] paragraphs;
    public String[] getParagraphs(){
        return paragraphs;
    }
    public void setParagraphs(String[] value){
        paragraphs = value;
    }
    public String getTextFromParagraphs(){
        String text = "";
        for (int i = 0;i < paragraphs.length;i ++){
            if (paragraphs[i].trim().equals("") == false){
                Log.i("Paragraph", paragraphs[i]);
                text += text.equals("") ? paragraphs[i].trim() :
                        "\n" + paragraphs[i].trim();
            }
        }

        return text;
    }

    private String[] comments;
    public String[] getComments(){
        return comments;
    }
    public void setComments(String[] value){
        comments = value;
    }

    private String[] endNoteText;
    public String[] getEndNoteText(){
        return endNoteText;
    }
    public void setEndNoteText(String[] value){
        endNoteText = value;
    }

    private String headerText;
    public String getHeaderText(){
        return headerText;
    }
    public void setHeaderText(String value){
        headerText = value;
    }

    private String footerText;
    public String getFooterText(){
        return footerText;
    }
    public void setFooterText(String value){
        footerText = value;
    }

    private String footNoteText;
    public String getFootNoteText(){
        return footNoteText;
    }
    public void setFootNoteText(String value){
        footNoteText = value;
    }

    private String[] mainTextboxText;
    public String[] getMainTextboxText(){
        return mainTextboxText;
    }
    public void setMainTextboxText(String[] value){
        mainTextboxText = value;
    }

    public void showContent(){
        Log.i(fileName, "Contents");
        Log.i("header",headerText);
        Log.i("paragraphs", getTextFromParagraphs());
        Log.i("footer",footerText);
    }

    public ArrayList<String> getContentData(){
        ArrayList<String> data = new ArrayList<String>();

        for(int i = 0;i < paragraphs.length;i ++){
            data.add(paragraphs[i]);
        }

        return data;
    }
}
