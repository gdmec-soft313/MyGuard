package cn.edu.gdmec.chaos07150844.myguard.m3communicationguard.entity;

/**
 * Created by flora on 2016/12/20.
 */
public class BlackContactInfo {
    public String phoneNumber;

    public String contactName;

    public int mode;

    public String getModeString(int mode) {
        switch (mode){
            case 1:
                return "电话拦截";
            case 2:
                return "短信拦截";
            case 3:
                return "电话、短信拦截";
        }
        return "";
    }
}
