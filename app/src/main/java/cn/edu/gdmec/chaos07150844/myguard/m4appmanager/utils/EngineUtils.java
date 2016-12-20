package cn.edu.gdmec.chaos07150844.myguard.m4appmanager.utils;

import android.content.Context;
import android.content.Intent;

import cn.edu.gdmec.chaos07150844.myguard.m4appmanager.entity.AppInfo;

/**
 * Created by Chaos丶 on 2016/12/20.
 */
public class EngineUtils {

    public static void  shareApplication(Context context, AppInfo appInfo){
        Intent intent = new Intent("android.intent.action.SEND");
    }
}
