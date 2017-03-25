package cn.jianke.hookpractice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @className: BaseActivity
 * @classDescription: 页面基类
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class BaseActivity extends Activity{

    /**
     * 启动新页面
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param  targetCls
     * @param bundle
     * @return
     */
    protected void startTargetActivity(Class<?> targetCls, Bundle bundle){
        Intent intent = new Intent(this, targetCls);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 启动新页面
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param  targetCls
     * @return
     */
    protected void startTargetActivity(Class<?> targetCls){
        startTargetActivity(targetCls, null);
    }
}
