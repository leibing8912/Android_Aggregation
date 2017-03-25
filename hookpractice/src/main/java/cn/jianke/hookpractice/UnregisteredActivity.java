package cn.jianke.hookpractice;

import android.os.Bundle;
/**
 * @className: UnregisteredActivity
 * @classDescription: 未在AndroidManifest.xml中注册页面
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class UnregisteredActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unregistered);
    }
}
