package cn.edu.gdmec.chaos07150844.myguard.m2theftguard;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.edu.gdmec.chaos07150844.myguard.R;

/**
 * Created by asus on 2016/12/20.
 */
public class SetUp1Activity extends BaseSetUpActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up1);
        initView();
    }
    private void initView(){
        ((RadioButton)findViewById(R.id.rb_first)).setChecked(true);
    }

    @Override
    public void showNext() {
        startActivityAndFinishSelf(SetUp2Activity.class);

    }

    @Override
    public void showPre() {
        Toast.makeText(this,"当前已经是第一页",Toast.LENGTH_LONG).show();


    }
}
