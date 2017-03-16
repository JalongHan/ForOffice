package com.gidea.foroffice.activity;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.util.Xml;

import org.apache.poi.hdgf.HDGFDiagram;
import org.apache.poi.hdgf.chunks.Chunk;
import org.apache.poi.hdgf.pointers.Pointer;
import org.apache.poi.hdgf.streams.ChunkStream;
import org.apache.poi.hdgf.streams.PointerContainingStream;
import org.apache.poi.hdgf.streams.Stream;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

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

public class OfficeReader {
    public static String getExternalStoragePath(){
        if (Environment.getExternalStorageState().toLowerCase().equals("mounted")){
            String strExternalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            if (strExternalStoragePath.endsWith("/") == false){
                strExternalStoragePath += "/";
            }

            return strExternalStoragePath;
        }
        else {
            //Log.i("Debug", "SDCard not present!");
            return "";
        }
    }

    public static String saveAssetFile(Context context, String strFile){
        InputStream is;
        String newFilePath = getExternalStoragePath() + strFile;
        try {
            is = context.getAssets().open(strFile);

            FileOutputStream fos = new FileOutputStream(new File(newFilePath));
            byte[] buffer = new byte[1024];
            while (true) {
                int len = is.read(buffer);
                if (len == -1) {
                    break;
                }
                fos.write(buffer, 0, len);
            }
            is.close();
            fos.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return newFilePath;
    }

    public static WordContent readWord2003(Context context,String strFile)
            throws IOException{
        InputStream docIS = context.getAssets().open(strFile);

        WordExtractor extractor = new WordExtractor(docIS);
        WordContent content = new WordContent();
        content.setFileName(strFile);

        content.setHeaderText(extractor.getHeaderText());

        content.setText(extractor.getText());
        content.setTextFromPieces(extractor.getTextFromPieces());
        content.setParagraphs(extractor.getParagraphText());
        content.setMainTextboxText(extractor.getMainTextboxText());

        content.setComments(extractor.getCommentsText());
        content.setEndNoteText(extractor.getEndnoteText());
        content.setFooterText(extractor.getFooterText());

        //extractor.getMetadataTextExtractor();

        return content;
    }

    public static String readWord2007(Context context,String strFile) throws IOException{
        String strNewFile = saveAssetFile(context, strFile);

        String content = "";
        try {
            ZipFile docxFile = new ZipFile(new File(strNewFile));
            ZipEntry sharedStringXML = docxFile.getEntry("word/document.xml");
            InputStream inputStream = docxFile.getInputStream(sharedStringXML);

            XmlPullParser xmlParser = Xml.newPullParser();
            xmlParser.setInput(inputStream, "utf-8");
            int evtType = xmlParser.getEventType();
            while (evtType != XmlPullParser.END_DOCUMENT) {
                switch (evtType) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlParser.getName();
                        //String text2 = xmlParser.getText();
                        //System.out.println(tag + "," + text2);
                        if (tag.equalsIgnoreCase("t")) {
                            String text = xmlParser.nextText();
                            if ((text != null) && (text.trim().equals("") == false)){
                                content += text + "\n";
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                evtType = xmlParser.next();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        if (content == null) {
            content = "解析文件出现问题";
        }

        return content;
    }

    public static ExcelContent readExcel2003(Context context,String strFile) throws IOException{
        InputStream xlsIS = context.getAssets().open(strFile);

        HSSFWorkbook wb = new HSSFWorkbook(xlsIS);
        wb.getNumberOfSheets();
        ExcelContent content = new ExcelContent();

        content.setFileName(strFile);
        for (int i = 0;i < wb.getNumberOfSheets();i ++){
            Sheet sheet = wb.getSheetAt(i);
            content.setSheetName(sheet.getSheetName());
            Log.i("Sheet Name:" , sheet.getSheetName());
            List<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
            for(Row row:sheet){
                ArrayList<String> rowData = new ArrayList<String>();
                for(Cell cell:row){
                    String cellValue = "";
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            cellValue = cell.getRichStringCellValue().getString();
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                cellValue = cell.getDateCellValue().toString();
                            } else {
                                cellValue = String.valueOf(cell.getNumericCellValue());
                            }
                            break;
                        case Cell.CELL_TYPE_BOOLEAN:
                            cellValue = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case Cell.CELL_TYPE_FORMULA:
                            cellValue = cell.getCellFormula();
                            break;
                        default:
                            cellValue = cell.getStringCellValue();
                    }

                    rowData.add(cellValue);
                    Log.i("Cell value:", cellValue);
                }
                data.add(rowData);
            }

            content.setSheetData(data);
        }

        return content;
    }

    public static String readExcel2007(Context context,String strFile) throws IOException{
        String strNewFile = saveAssetFile(context, strFile);

        String content = "";
        String v = null;
        boolean flat = false;
        List<String> ls = new ArrayList<String>();
        try {
            ZipFile xlsxFile = new ZipFile(new File(strNewFile));
            ZipEntry sharedStringXML = xlsxFile
                    .getEntry("xl/sharedStrings.xml");
            InputStream inputStream = xlsxFile.getInputStream(sharedStringXML);
            XmlPullParser xmlParser = Xml.newPullParser();
            xmlParser.setInput(inputStream, "utf-8");
            int evtType = xmlParser.getEventType();
            while (evtType != XmlPullParser.END_DOCUMENT) {
                switch (evtType) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlParser.getName();
                        if (tag.equalsIgnoreCase("t")) {
                            ls.add(xmlParser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                evtType = xmlParser.next();
            }

            ZipEntry sheetXML = xlsxFile.getEntry("xl/worksheets/sheet1.xml");
            InputStream inputStreamsheet = xlsxFile.getInputStream(sheetXML);
            XmlPullParser xmlParsersheet = Xml.newPullParser();
            xmlParsersheet.setInput(inputStreamsheet, "utf-8");
            int evtTypesheet = xmlParsersheet.getEventType();
            while (evtTypesheet != XmlPullParser.END_DOCUMENT) {
                switch (evtTypesheet) {
                    case XmlPullParser.START_TAG:
                        String tag = xmlParsersheet.getName();
                        if (tag.equalsIgnoreCase("row")) {
                        }
                        else if (tag.equalsIgnoreCase("c")) {
                            String t = xmlParsersheet.getAttributeValue(null, "t");
                            if (t != null) {
                                flat = true;
                                System.out.println(flat + "有");
                            }
                            else{
                                System.out.println(flat + "没有");
                                flat = false;
                            }
                        }
                        else if (tag.equalsIgnoreCase("v")) {
                            v = xmlParsersheet.nextText();
                            if (v != null) {
                                if (flat) {
                                    content += ls.get(Integer.parseInt(v)) + "  ";
                                }
                                else{
                                    content += v + "  ";
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlParsersheet.getName().equalsIgnoreCase("row") && v != null) {
                            content += "\n";
                        }
                        break;
                }
                evtTypesheet = xmlParsersheet.next();
            }
            System.out.println(content);
        }
        catch (ZipException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        if (content == null) {
            content = "解析文件出现问题";
        }
        return content;
    }


    public static String readPowerpoint2003(Context context,String strFile) throws IOException{
        InputStream pptIS = context.getAssets().open(strFile);

        String content = "";
        try{
            PowerPointExtractor extractor = new PowerPointExtractor(pptIS);

            content = extractor.getText();
            Log.i("content", content);
        }
        catch (Exception e){

        }
		/*SlideShow ppt = new SlideShow(new HSLFSlideShow(pptIS));
		//get slides
		Slide[] slides = ppt.getSlides();
		Log.i("count", slides.length + " slides");
		for (int i = 0; i < slides.length; i++){
		    Shape[] sh = slides[i].getShapes();
		    Log.i("count", sh.length + " shapes");

		    for (int j = 0; j < sh.length; j++){
		        //name of the shape
		        String name = sh[j].getShapeName();
		        Log.i("shape " + j, name);
		        //shapes's anchor which defines the position of this shape in the slide
		        //java.awt.Rectangle anchor = sh[j].getAnchor();

		        if (sh[j] instanceof Line){
		            Line line = (Line)sh[j];
		            //work with Line
		        }
		        else if (sh[j] instanceof AutoShape){
		            AutoShape shape = (AutoShape)sh[j];
		            //work with AutoShape
		        }
		        else if (sh[j] instanceof TextBox){
		            TextBox shape = (TextBox)sh[j];
		            //work with TextBox

		            content += content.equals("") ? shape.getText() : "\n" + shape.getText();
		        }
		        else if (sh[j] instanceof Picture){
		            Picture shape = (Picture)sh[j];
		            //work with Picture
		        }
		    }
		}*/

        return content;
    }

    public static String readPowerpoint2007(Context context,String strFile) throws IOException{
        String strNewFile = saveAssetFile(context, strFile);

        String content = "";
        List<String> ls = new ArrayList<String>();
        ZipFile pptxFile = null;
        try {
            pptxFile = new ZipFile(new File(strNewFile));// pptx按照读取zip格式读取
        }
        catch (ZipException e1) {
            e1.printStackTrace();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }



        try {
            ZipEntry sharedStringXML = pptxFile.getEntry("[Content_Types].xml");// 找到里面存放内容的文件
            InputStream inputStream = pptxFile.getInputStream(sharedStringXML);// 将得到文件流
            XmlPullParser xmlParser = Xml.newPullParser();// 实例化pull
            xmlParser.setInput(inputStream, "utf-8");// 将流放进pull中
            int evtType = xmlParser.getEventType();// 得到标签类型的状态
            while (evtType != XmlPullParser.END_DOCUMENT) {// 循环读取流
                switch (evtType) {
                    case XmlPullParser.START_TAG: // 判断标签开始读取
                        String tag = xmlParser.getName();// 得到标签
                        if (tag.equalsIgnoreCase("Override")) {
                            String s = xmlParser.getAttributeValue(null, "PartName");
                            if (s.lastIndexOf("/ppt/slides/slide") == 0) {
                                ls.add(s);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:// 标签读取结束
                        break;
                    default:
                        break;
                }
                evtType = xmlParser.next();// 读取下一个标签
            }
        }
        catch (ZipException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        for (int i = 1; i < (ls.size() + 1); i++) {// 假设有6张幻灯片
            content += "第" + i + "张················" + "\n";
            try {
                ZipEntry sharedStringXML = pptxFile.getEntry("ppt/slides/slide"
                        + i + ".xml");// 找到里面存放内容的文件
                InputStream inputStream = pptxFile.getInputStream(sharedStringXML);// 将得到文件流
                XmlPullParser xmlParser = Xml.newPullParser();// 实例化pull
                xmlParser.setInput(inputStream, "utf-8");// 将流放进pull中
                int evtType = xmlParser.getEventType();// 得到标签类型的状态
                while (evtType != XmlPullParser.END_DOCUMENT) {// 循环读取流
                    switch (evtType) {
                        case XmlPullParser.START_TAG: // 判断标签开始读取
                            String tag = xmlParser.getName();// 得到标签
                            if (tag.equalsIgnoreCase("t")) {
                                content += xmlParser.nextText() + "\n";
                            }
                            break;
                        case XmlPullParser.END_TAG:// 标签读取结束
                            break;
                        default:
                            break;
                    }
                    evtType = xmlParser.next();// 读取下一个标签
                }
            }
            catch (ZipException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (XmlPullParserException e) {
                e.printStackTrace();
            }
        }
        if (content == null) {
            content = "解析文件出现问题";
        }

        return content;
    }

    //这个方法copy from POI的官方sample
    private static String dumpStream(Stream stream, int indent) {
        String ind = "";
        for(int i=0; i<indent; i++) {
            ind += "    ";
        }
        String ind2 = ind  + "    ";
        String ind3 = ind2 + "    ";

        Pointer ptr = stream.getPointer();
        String content = ind + "Stream at\t" + ptr.getOffset() +
                " - " + Integer.toHexString(ptr.getOffset());
        content += ind + "  Type is\t" + ptr.getType() +
                " - " + Integer.toHexString(ptr.getType());
        content += ind + "  Format is\t" + ptr.getFormat() +
                " - " + Integer.toHexString(ptr.getFormat());
        content += ind + "  Length is\t" + ptr.getLength() +
                " - " + Integer.toHexString(ptr.getLength());
        if(ptr.destinationCompressed()) {
            int decompLen = stream._getContentsLength();
            content += ind + "  DC.Length is\t" + decompLen +
                    " - " + Integer.toHexString(decompLen);
        }
        content += ind + "  Compressed is\t" + ptr.destinationCompressed();
        content += ind + "  Stream is\t" + stream.getClass().getName();

        byte[] db = stream._getStore()._getContents();
        String ds = "";
        if(db.length >= 8) {
            for(int i=0; i<8; i++) {
                if(i>0) ds += ", ";
                ds += db[i];
            }
        }
        content += ind + "  First few bytes are\t" + ds;

        if(stream instanceof PointerContainingStream) {
            PointerContainingStream pcs = (PointerContainingStream)stream;
            content += ind + "  Has " +
                    pcs.getPointedToStreams().length + " children:";

            //for(int i=0; i<pcs.getPointedToStreams().length; i++) {
            //	content += dumpStream(pcs.getPointedToStreams()[i], (indent+1));
            //}
        }
        if(stream instanceof ChunkStream) {
            ChunkStream cs = (ChunkStream)stream;
            content += ind + "  Has " + cs.getChunks().length +
                    " chunks:";

            for(int i=0; i<cs.getChunks().length; i++) {
                Chunk chunk = cs.getChunks()[i];
                content += ind2 + "" + chunk.getName();
                content += ind2 + "  Length is " + chunk._getContents().length + " (" + Integer.toHexString(chunk._getContents().length) + ")";
                content += ind2 + "  OD Size is " + chunk.getOnDiskSize() + " (" + Integer.toHexString(chunk.getOnDiskSize()) + ")";
                content += ind2 + "  T / S is " + chunk.getTrailer() + " / " + chunk.getSeparator();
                content += ind2 + "  Holds " + chunk.getCommands().length + " commands";
                for(int j=0; j<chunk.getCommands().length; j++) {
                    Chunk.Command command = chunk.getCommands()[j];
                    content += ind3 + "" +
                            command.getDefinition().getName() +
                            " " + command.getValue();
                }
            }
        }

        return content;
    }

    public static String readVisio(Context context,String strFile) throws IOException {
        String strNewFile = saveAssetFile(context, strFile);

        HDGFDiagram hdgf = new HDGFDiagram(
                new NPOIFSFileSystem(new File(strNewFile))
        );

        String content = "";
        content += "Opened " + strFile + "\n";
        content += "The document claims a size of " +
                hdgf.getDocumentSize() + "   (" +
                Long.toHexString(hdgf.getDocumentSize()) + ")\n";;

        content += dumpStream(hdgf.getTrailerStream(), 0);

        return content;
    }
}


