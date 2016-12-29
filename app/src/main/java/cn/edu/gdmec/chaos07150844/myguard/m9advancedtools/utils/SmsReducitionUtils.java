package cn.edu.gdmec.chaos07150844.myguard.m9advancedtools.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Environment;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import cn.edu.gdmec.chaos07150844.myguard.m9advancedtools.entity.SmsInfo;

/**
 * Created by qaq on 2016/12/20.
 */
public class SmsReducitionUtils {
    public interface SmsReducitionCallBack{
        public void beforeSmsReducition(int size);
        public void onSmsReducition(int process);
    }
    private boolean flag=true;
    public void setFlag(boolean flag){
        this.flag=flag;
    }
    public boolean reducitionSms(Activity context,SmsReducitionCallBack callBack) throws XmlPullParserException,IOException{
        File file=new File(Environment.getExternalStorageDirectory(),"backup.xml");
        if(file.exists()){
            FileInputStream is=new FileInputStream(file);
            XmlPullParser parser= Xml.newPullParser();
            parser.setInput(is,"utf-8");
            SmsInfo smsInfo=null;
            int eventType=parser.getEventType();
            Integer max=null;
            int progress=0;
            ContentResolver resolver=context.getContentResolver();
            Uri uri=Uri.parse("content://sms/");
            while (eventType !=XmlPullParser.END_DOCUMENT & flag){
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("smss".equals(parser.getName())) {
                            String maxStr = parser.getAttributeValue(0);
                            max = new Integer(maxStr);
                            callBack.beforeSmsReducition(max);
                        } else if ("sms".equals(parser.getName())) {
                            smsInfo = new SmsInfo();
                        } else if ("body".equals(parser.getName())) {
                            try {
                                smsInfo.body = Crypto.decrypt("123", parser.nextText());
                            } catch (Exception e) {
                                e.printStackTrace();
                                smsInfo.body = "短信还原失败";
                            }
                        } else if ("address".equals(parser.getName())) {
                            smsInfo.address = parser.nextText();
                        } else if ("type".equals(parser.getName())) {
                            smsInfo.type = parser.nextText();
                        } else if ("date".equals(parser.getName())) {
                            smsInfo.date = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("sms".equals(parser.getName())) {
                            ContentValues values = new ContentValues();
                            values.put("address", smsInfo.address);
                            values.put("type", smsInfo.type);
                            values.put("date", smsInfo.date);
                            values.put("body", smsInfo.body);
                            resolver.insert(uri, values);
                            smsInfo = null;
                            progress++;
                            callBack.onSmsReducition(progress);
                        }
                        break;
                    }
                    eventType=parser.next();
                }
                if (eventType==XmlPullParser.END_DOCUMENT & max!=null){
                    if (progress<max){
                        callBack.onSmsReducition(max);
                    }
                }
            }else{
                UIUtils.showToast(context,"你没有备份短信");
            }
            return flag;
    }
}
