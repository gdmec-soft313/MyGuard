package cn.edu.gdmec.chaos07150844.myguard.m3communicationguard;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


import cn.edu.gdmec.chaos07150844.myguard.R;
import cn.edu.gdmec.chaos07150844.myguard.m3communicationguard.adapter.ContactAdapter;
import cn.edu.gdmec.chaos07150844.myguard.m3communicationguard.entity.ContactInfo;

public class ContactSelectActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mListView;
    private ContactAdapter adapter;
    private List<ContactInfo> systemContacts;
    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case 10:
                    if(systemContacts!=null){
                        adapter = new ContactAdapter(systemContacts,ContactSelectActivity.this);
                        mListView.setAdapter(adapter);


                    }
                    break;
            }
        };
    } ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_contact_select);
        initView();
    }

    private  void initView(){
        ((TextView)findViewById(R.id.tv_title)).setText("选择联系人");

    }
    @Override
    public void onClick(View v) {

    }
}
