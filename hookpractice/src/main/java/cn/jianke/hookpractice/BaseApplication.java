package cn.jianke.hookpractice;

import android.app.Application;

/**
 * @className: BaseApplication
 * @classDescription: 应用实例
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class BaseApplication extends Application{
    // hook utils instance
    private HookUtils mHookUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        // init hook utils instance
        mHookUtils = new HookUtils(ProxActivity.class, this);
        // start hook system handler
        mHookUtils.hookSystemHandler();
        // start hook ams
        mHookUtils.hookAms();
    }
}
