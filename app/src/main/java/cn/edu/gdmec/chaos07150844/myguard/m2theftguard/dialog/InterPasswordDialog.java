package cn.edu.gdmec.chaos07150844.myguard.m2theftguard.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.edu.gdmec.chaos07150844.myguard.R;

/**
 * Created by asus on 2016/12/20.
 */
/*确认密码对话框*/
public class InterPasswordDialog  extends Dialog implements android.view.View.OnClickListener{
    private TextView mTitleTV;
    private EditText mInterET;
    private Button mOKBtn;
    private Button mCancleBtn;
    private MyCallBack myCallBack;
    private Context context;

    public InterPasswordDialog(Context context) {
        super(context, R.style.dialog_custom);
        this.context = context;
    }

    public void setCallBack(MyCallBack myCallBack) {
        this.myCallBack = myCallBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.inter_password_dialog);
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView(){
        mTitleTV = (TextView) findViewById(R.id.yv_setuppwd_title);
        mInterET = (EditText) findViewById(R.id.et_inter_password);
        mOKBtn = (Button) findViewById(R.id.btn_comfirm);
        mCancleBtn = (Button) findViewById(R.id.btn_dismiss);
        mOKBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);
    }


   public void setTile(String title){
       if(!TextUtils.isEmpty(title)){
           mTitleTV.setText(title);
       }
   }
    public  String getPassword(){
        return mInterET.getText().toString();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_comfirm:
                myCallBack.confirm();
                break;
            case R.id.btn_dismiss:
                myCallBack.cancle();
                break;
        };
    }


    public interface MyCallBack {
        void confirm();
        void cancle();
    }
}
