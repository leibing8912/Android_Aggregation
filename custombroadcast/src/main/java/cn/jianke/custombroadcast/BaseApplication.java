package cn.jianke.custombroadcast;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import static cn.jianke.custombroadcast.MainActivity.BROADCAST_ACTION;

/**
 * @className:
 * @classDescription:
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class BaseApplication extends Application{
    // 日志标识
    private final static String TAG = "BaseApplication";
    // 应用内广播对象
    private BroadcastReceiver mLocalBroadcastReceiver;
    // 应用内广播管理器
    private LocalBroadcastManager mLocalBroadcastManager;
    // sington
    private static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        // init instance
        instance = this;
        // 应用内广播注册
        localRegister();
    }

    /**
     * get sington
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param
     * @return
     */
    public static BaseApplication getInstance(){
        if (instance == null){
            synchronized (BaseApplication.class){
                if (instance == null)
                    instance= new BaseApplication();
            }
        }
        return instance;
    }

    /**
     * 应用内广播注册
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param
     * @return
     */
    private void localRegister() {
        Log.e(TAG, "#localRegister");
        // init local broadcast instance
        mLocalBroadcastReceiver = new LocalBroadcastReceiver();
        // init local broadcast manager
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        // init intent filter
        IntentFilter intentFilter = new IntentFilter();
        // add action to intent filter
        intentFilter.addAction(BROADCAST_ACTION);
        // register broadcast
        mLocalBroadcastManager.registerReceiver(mLocalBroadcastReceiver, intentFilter);
    }

    /**
     * 解绑应用内广播
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param
     * @return
     */
    public void localUnRegister(){
        Log.e(TAG, "#localUnRegister");
        if (mLocalBroadcastManager != null
                && mLocalBroadcastReceiver != null){
            mLocalBroadcastManager.unregisterReceiver(mLocalBroadcastReceiver);
        }
    }
}
