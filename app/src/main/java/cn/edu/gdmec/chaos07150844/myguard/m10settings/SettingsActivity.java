package cn.edu.gdmec.chaos07150844.myguard.m10settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.gdmec.chaos07150844.myguard.R;
import cn.edu.gdmec.chaos07150844.myguard.m10settings.utils.SystemInfoUtils;
import cn.edu.gdmec.chaos07150844.myguard.m10settings.widget.SettingView;
import cn.edu.gdmec.chaos07150844.myguard.m9advancedtools.service.AppLockService;


public class SettingsActivity extends AppCompatActivity implements View.OnClickListener,SettingView.OnCheckedStatusIsChanged{

    private SettingView mBlackNumSV;
    private SettingView mAppLockSV;
    private SharedPreferences mSP;
    private boolean running;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        mSP = getSharedPreferences("config",MODE_PRIVATE);
        initView();
    }

    private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(
                getResources().getColor(R.color.light_blue)
        );
        ImageView mLeftImgv = (ImageView) findViewById(R.id.imgv_leftbtn);
        ((TextView)findViewById(R.id.tv_title)).setText("设置中心");
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        mBlackNumSV = (SettingView) findViewById(R.id.sv_blacknumber_set);

    }

    protected void onStart(){
        running = SystemInfoUtils.isServiceRunning(this,"cn.itcast.mobliesafe.chapter09.service.AppLockService");
        mAppLockSV.setChecked(running);
        mBlackNumSV.setChecked(mSP.getBoolean("BlackNumStatus",true));
        super.onStart();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
        }
    }

    public void onCheckedChanged(View view,boolean isChecked){
        switch (view.getId()){
            case R.id.sv_blacknumber_set:
                saveStatus("BlackNumStatus",isChecked);
                break;
            case R.id.sv_applock_set:
                saveStatus("AppLockStatus",isChecked);
                if (isChecked){
                    intent = new Intent(this, AppLockService.class);
                    startService(intent);
                }else {
                    stopService(intent);
                }
                break;
        }

    }
    private void saveStatus(String keyname,boolean isChecked){
        if (!TextUtils.isEmpty(keyname)){
            SharedPreferences.Editor edit = mSP.edit();
            edit.putBoolean(keyname, isChecked);
            edit.commit();
        }
    }
}
