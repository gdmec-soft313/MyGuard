package cn.edu.gdmec.chaos07150844.myguard.m5virusscan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;

/**
 * Created by flora on 2016/12/21.
 */
public class AntiVirusDao {
    private static Context context;
    private static String dbname;
    public AntiVirusDao(Context context){
        this.context = context;
        dbname = "/data/data/"+context.getPackageName()+"/files/antivirus.db";

    }
    public String checkVirus(String md5){
        String desc = null;
        SQLiteDatabase db = SQLiteDatabase.openDatabase(
                dbname,null,
                SQLiteDatabase.OPEN_READONLY
        );
        Cursor cursor = db.rawQuery("select desc from datable where md5 = ?",
                new String[]{ md5 });
        if (cursor.moveToNext()){
            desc = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return desc;
    }
    public boolean isDBExit(){
        File file = new File(dbname);
        return file.exists()&& file.length() >0;
    }

    public String getDBVersionNum(){
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbname,null,SQLiteDatabase.OPEN_READONLY);
        String versionnumber = "0";
        Cursor cursor = db.rawQuery("select subcnt from version",null);

        if (cursor.moveToNext()){
            versionnumber = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return versionnumber;
    }

    public void updateDBVersion(int newversion){
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbname,null,SQLiteDatabase.OPEN_READWRITE);
        String versionnumber = "0";
        ContentValues values = new ContentValues();
        values.put("subcnt",newversion);
        db.update("version",values,null,null);
        db.close();
    }

    public void add(String desc,String md5){
        SQLiteDatabase db = SQLiteDatabase.openDatabase(dbname,null,SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("md5",md5);
        values.put("desc",desc);
        values.put("type",6);
        values.put("name","Android.Hack.i22hkt.a");
        db.insert("datable",null,values);
        db.close();
    }
}
