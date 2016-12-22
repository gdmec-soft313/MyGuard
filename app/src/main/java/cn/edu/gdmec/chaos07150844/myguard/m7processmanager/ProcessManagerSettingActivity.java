package cn.edu.gdmec.chaos07150844.myguard.m7processmanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import cn.edu.gdmec.chaos07150844.myguard.R;
import cn.edu.gdmec.chaos07150844.myguard.m7processmanager.service.AutoKillProcessService;
import cn.edu.gdmec.chaos07150844.myguard.m7processmanager.utils.SystemInfoUtils;

public class ProcessManagerSettingActivity extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

    private ToggleButton mShowSysAPPStgb;
    private ToggleButton mKillProcessTgb;
    private SharedPreferences mSP;
    private boolean running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_process_manager_setting);
        mSP=getSharedPreferences("config",MODE_PRIVATE);
        initView();
    }

    private void initView(){
        findViewById(R.id.rl_titlebar).setBackgroundColor(getResources().getColor(R.color.bright_green));
        ImageView mLeftImgv= (ImageView) findViewById(R.id.imgv_leftbtn);
        mLeftImgv.setOnClickListener(this);
        mLeftImgv.setImageResource(R.drawable.back);
        ((TextView)findViewById(R.id.tv_title)).setText("进程管理设置");
        mShowSysAPPStgb= (ToggleButton) findViewById(R.id.tgb_showsys_process);
        mKillProcessTgb= (ToggleButton) findViewById(R.id.tgb_killprocess_lockscreen);
        mShowSysAPPStgb.setChecked(mSP.getBoolean("showSystemProcess",true));
        running= SystemInfoUtils.isServiceRunning(this,"cn.itcast.mobliesafe.chapter07.sevice.AutoKillProcessService");
        mKillProcessTgb.setChecked(running);
        initListener();
    }

    private void initListener(){
        mKillProcessTgb.setOnCheckedChangeListener(this);
        mShowSysAPPStgb.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()){
            case R.id.tgb_showsys_process:
                saveStatus("showSystemProcess",b);
                break;
            case R.id.tgb_killprocess_lockscreen:
                Intent service=new Intent(this, AutoKillProcessService.class);
                if(b){
                    startService(service);

                }else{
                    stopService(service);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgv_leftbtn:
                finish();
                break;
        }
    }

    private void saveStatus(String string,boolean isChecked){
        SharedPreferences.Editor editor=mSP.edit();
        editor.putBoolean(string,isChecked);
        editor.commit();
    }
}
