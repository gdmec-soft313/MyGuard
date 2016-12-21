package cn.edu.gdmec.chaos07150844.myguard.m8trafficmonitor.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Chaos丶 on 2016/12/21.
 */
public class TrafficOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "traffic.db",TABLE_NAME="traffic";
    private final static String GPRS="gprs",TIME="date";

    public TrafficOpenHelper(Context context){
        super(context,DB_NAME,null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +"(id integer primary key autoincrement," +
        GPRS + "varchar(255),"+TIME+"datetime)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
