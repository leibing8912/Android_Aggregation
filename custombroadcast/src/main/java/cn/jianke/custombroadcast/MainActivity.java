package cn.jianke.custombroadcast;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import android.view.View;

/**
 * @className: MainActivity
 * @classDescription: broad cast man page
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class MainActivity extends BaseActivity {
    // 广播动作
    public static final String BROADCAST_ACTION = "cn.jianke.custombroadcast";
    // 动态广播对象
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 动态注册广播
        dynamicRegister();
        // onClick
        findViewById(R.id.btn_turn_to_broadcast_page).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTargetActivity(StartBroadcastActivity.class);
            }
        });
    }

    /**
     * 动态注册广播
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param
     * @return
     */
    private void dynamicRegister() {
        // init dynamic broadcast instance
        mBroadcastReceiver = new DynamicRegiserBroadcastReceiver();
        // init intent filter
        IntentFilter intentFilter = new IntentFilter();
        // add action to intent filter
        intentFilter.addAction(BROADCAST_ACTION);
        // register broadcast
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    /**
     * 解绑动态广播
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param
     * @return
     */
    private void dynamicUnRegister(){
        if (mBroadcastReceiver != null){
            unregisterReceiver(mBroadcastReceiver);
            mBroadcastReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        // 解绑动态广播
        dynamicUnRegister();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            BaseApplication.getInstance().localUnRegister();
            finish();
            android.os.Process.killProcess(Process.myPid());
        }
        return false;
    }
}
