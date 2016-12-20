package cn.edu.gdmec.chaos07150844.myguard.m1home.utils;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;

/**
 * Created by Administrator on 2016/12/20 0020.
 */
public class DownloadUtils {
    public void downapk(String url,String targerFile,final MyCallBack myCallBack){
        HttpUtils httpUtils=new HttpUtils();
        httpUtils.download(url, targerFile, new RequestCallBack<File>() {
            @Override
            public void onSuccess(ResponseInfo<File> responseInfo) {
                myCallBack.onSuccess(responseInfo);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                myCallBack.onFailure(e,s);
            }

            @Override
            public void onLoading(long total, long current, boolean isUploading) {
                super.onLoading(total, current, isUploading);
                myCallBack.onLoadding(total,current,isUploading);
            }
        });

    }
}

interface MyCallBack{
    void onSuccess(ResponseInfo<File> responseInfo);
    void onFailure(HttpException e, String s);
    void onLoadding(long total, long current, boolean isUploading);
}
