package cn.jianke.custombroadcast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import static cn.jianke.custombroadcast.MainActivity.BROADCAST_ACTION;

/**
 * @className: StartBroadcastActivity
 * @classDescription: 启动广播页
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class StartBroadcastActivity extends BaseActivity {
    // 页面名称
    public final static String PAGE_NAME = "StartBroadcastActivity";
    // 应用内广播管理器
    private LocalBroadcastManager mLocalBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_broadcast);
        // init local broadcast manager
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        // onClick
        findViewById(R.id.btn_start_broadcast).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendBroadcast();
            }
        });
        findViewById(R.id.btn_start_local_broadcast).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendLocalBroadcast();
            }
        });
    }

    /**
     * 发送应用内广播
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param
     * @return
     */
    private void sendLocalBroadcast() {
        if (mLocalBroadcastManager != null) {
            Intent intent = new Intent();
            // 设置广播动作
            intent.setAction(BROADCAST_ACTION);
            // 广播传值
            Bundle bundle = new Bundle();
            bundle.putSerializable(ConstantUtils.WHAT_PAGE_FROM, PAGE_NAME);
            intent.putExtras(bundle);
            // 发送广播
            mLocalBroadcastManager.sendBroadcast(intent);
        }
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
        // 发送广播
        sendBroadcast(intent);
    }
}
