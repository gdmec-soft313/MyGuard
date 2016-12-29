package cn.edu.gdmec.chaos07150844.myguard.m7processmanager.entity;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/12/21 0021.
 */
public class AppEntity {
    private Drawable appIcon;
    private String appName;
    private String packageName;
    private double memorySize;

    public Drawable getAppIcon(){
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon){
        this.appIcon=appIcon;
    }

    public String getAppName(){
        return appName;
    }

    public void setAppName(String appName){
        this.appName=appName;
    }

    public String getPackageName(){
        return packageName;
    }

    public void setPackageName(String packageName){
        this.packageName=packageName;
    }

    public double getMemorySize(){
        return memorySize;
    }

    public void setPackageName(double memorySize){
        this.memorySize=memorySize;
    }

}
