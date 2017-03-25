package cn.jianke.custombroadcast;

import android.app.ActivityManager;
import android.content.Context;

/**
 * @className: CommonUtils
 * @classDescription: 通用工具
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class CommonUtils {

    /**
     * 获取当前进程名
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
