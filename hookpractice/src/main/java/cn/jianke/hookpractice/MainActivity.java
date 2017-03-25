package cn.jianke.hookpractice;

import android.os.Bundle;
import android.view.View;

/**
 * @className: MainActivity
 * @classDescription: main page
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // onClick
        findViewById(R.id.btn_start_other_activity).setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startTargetActivity(OtherActivity.class);
                startTargetActivity(UnregisteredActivity.class);
            }
        });
    }
}
