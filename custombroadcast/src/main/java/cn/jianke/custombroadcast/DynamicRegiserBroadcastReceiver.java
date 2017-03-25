package cn.jianke.custombroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @className: DynamicRegiserBroadcastReceiver
 * @classDescription: 动态注册广播
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class DynamicRegiserBroadcastReceiver extends BroadcastReceiver{
    // 日志标识
    private final static String TAG = "Dynamicregiser";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 打印context
        Log.e(TAG, "#context>" + context);
        // 打印广播动作
        Log.e(TAG, "#action>" + intent.getAction());
        // 打印从何页面发送广播
        if (intent != null && intent.getExtras() != null){
            Log.e(TAG, "#whatFrom>" + intent.getExtras().getString(ConstantUtils.WHAT_PAGE_FROM));
        }
        // 打印当前进程名
        Log.e(TAG, "#current process name>" + CommonUtils.getCurProcessName(context));
    }
}
