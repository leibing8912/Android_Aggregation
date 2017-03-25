package cn.jianke.sendbroadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * @className: SendBroadcastActivity
 * @classDescription: send broadcast page
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class SendBroadcastActivity extends AppCompatActivity {
    // 广播动作
    public static final String BROADCAST_ACTION = "cn.jianke.custombroadcast";
    // 页面名称
    public final static String PAGE_NAME = "SendBroadcastActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // onClick
        findViewById(R.id.btn_send_broadcast).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcast();
            }
        });
    }

    /**
     * 发送广播
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param
     * @return
     */
    public void sendBroadcast(){
        Intent intent = new Intent();
        // 设置广播动作
        intent.setAction(BROADCAST_ACTION);
        // 广播传值
        Bundle bundle = new Bundle();
        bundle.putSerializable(ConstantUtils.WHAT_PAGE_FROM, PAGE_NAME);
        intent.putExtras(bundle);
        // set flags(运行)
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        // 发送广播
        sendBroadcast(intent);
    }
}
