package cn.edu.gdmec.chaos07150844.myguard.m2theftguard.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import cn.edu.gdmec.chaos07150844.myguard.m2theftguard.entity.ContactInfo;

/**
 * Created by asus on 2016/12/20.
 */
public class ContactInfoParser {
    public static List<ContactInfo> getSystemContact(Context context){
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri datauri = Uri.parse("content://com.android.contacts/data");
        List<ContactInfo> infos = new ArrayList<ContactInfo>();
        Cursor cursor = resolver.query(uri,new String[]{"contact_id"},
                null,null,null);
        while(cursor.moveToNext()){
            String id = cursor.getString(0);
            if(id!=null){
                System.out.println("联系人id"+id);
                ContactInfo info = new ContactInfo();
                info.id=id;
                Cursor dataCursor = resolver.query(datauri,new String[]{
                        "data1","mimetype"},"raw_contact_id=?",
                        new String[]{id},null);
                while(dataCursor.moveToNext()){
                    String data1 = dataCursor.getString(0);
                    String minetype= dataCursor.getString(1);
                    if("vnd.android.cursor.item/name".equals(minetype)){
                        System.out.println("姓名"+data1);
                        info.name=data1;

                    }else if("vnd.android.cursor.item/phone_v2".equals(minetype)){
                        System.out.println("电话="+data1);
                        info.phone=data1;
                    }
                }
                if(TextUtils.isEmpty(info.name)&&TextUtils.isEmpty(info.phone))
                    continue;
                    infos.add(info);
                    dataCursor.close();
                }
        }
        cursor.close();
        return infos;
    }
    public static List<ContactInfo> getSimContacts(Context context){
        Uri uri = Uri.parse("content://icc/adn");
        List<ContactInfo> infos  = new ArrayList<ContactInfo>();
        Cursor mCursor  = context.getContentResolver().query(uri,null,null,
        null,null);
        if(mCursor!=null){
            while(mCursor.moveToNext()){
                ContactInfo info = new ContactInfo();
                int nameFieldColumIndex =mCursor.getColumnIndex("name");
                info.name = mCursor.getString(nameFieldColumIndex);
                int numberFieldColumIndex = mCursor.getColumnIndex("number");
                info.phone = mCursor.getString(numberFieldColumIndex);
                infos.add(info);
            }
        }
        mCursor.close();
        return infos;
    }
}
