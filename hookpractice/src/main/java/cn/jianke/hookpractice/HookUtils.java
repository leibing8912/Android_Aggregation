package cn.jianke.hookpractice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @className: HookUtils
 * @classDescription: hook工具类
 * @author: leibing
 * @createTime: 2017/3/25
 */
public class HookUtils {
    // 日志标识
    private final static String TAG = "HookUtils";
    // activity启动提示
    private final static String ACTIVITY_LAUNCH_TIPS = "Activity已经开始启动";
    // 代理页面
    private Class<?> proxyActivity;
    // 上下文
    private Context context;

    /**
     * Constructor
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param proxyActivity 代理页面
     * @param context 上下文
     * @return
     */
    public HookUtils(Class<?> proxyActivity, Context context) {
        this.proxyActivity = proxyActivity;
        this.context = context;
    }

    /**
     * hook ams
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param
     * @return
     */
    public void hookAms() {
        // 一路反射，直到拿到IActivityManager的对象
        try {
            Class<?> ActivityManagerNativeClss = Class.forName("android.app.ActivityManagerNative");
            Field defaultFiled = ActivityManagerNativeClss.getDeclaredField("gDefault");
            defaultFiled.setAccessible(true);
            Object defaultValue = defaultFiled.get(null);
            // 反射SingleTon
            Class<?> SingletonClass = Class.forName("android.util.Singleton");
            Field mInstance = SingletonClass.getDeclaredField("mInstance");
            mInstance.setAccessible(true);
            // 到这里已经拿到ActivityManager对象
            Object iActivityManagerObject = mInstance.get(defaultValue);
            // 开始动态代理，用代理对象替换掉真实的ActivityManager，瞒天过海
            Class<?> mIActivityManagerIntercept = Class.forName("android.app.IActivityManager");
            // 动态代理handler实例
            AmsInvocationHandler mAmsInvocationHandler = new AmsInvocationHandler(iActivityManagerObject);
            // 代理对象
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{mIActivityManagerIntercept}, mAmsInvocationHandler);
            // 现在替换掉这个对象
            mInstance.set(defaultValue, proxy);
        } catch (Exception e) {
            Log.e(TAG,"#Exception>" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @className: AmsInvocationHandler
     * @classDescription: ams invocation mHandler
     * @author: leibing
     * @createTime: 2017/3/25
     */
    class AmsInvocationHandler implements InvocationHandler {
        // ActivityManager对象
        private Object iActivityManagerObject;

        private AmsInvocationHandler(Object iActivityManagerObject) {
            this.iActivityManagerObject = iActivityManagerObject;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 我要在这里搞点事情
            if ("startActivity".contains(method.getName())) {
                Log.e(TAG, ACTIVITY_LAUNCH_TIPS);
                // 换掉
                Intent intent = null;
                int index = 0;
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg instanceof Intent) {
                        // 说明找到了startActivity的Intent参数
                        intent = (Intent) args[i];
                        // 这个意图是不能被启动的，因为Acitivity没有在清单文件中注册
                        index = i;
                    }
                }

                // 伪造一个代理的Intent，代理Intent启动的是proxyActivity
                Intent proxyIntent = new Intent();
                ComponentName componentName = new ComponentName(context, proxyActivity);
                proxyIntent.setComponent(componentName);
                proxyIntent.putExtra("oldIntent", intent);
                args[index] = proxyIntent;
            }
            return method.invoke(iActivityManagerObject, args);
        }
    }

    /**
     * hook system mHandler
     * @author leibing
     * @createTime 2017/3/25
     * @lastModify 2017/3/25
     * @param
     * @return
     */
    public void hookSystemHandler() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            // 获取主线程对象
            Object activityThread = currentActivityThreadMethod.invoke(null);
            // 获取mH字段
            Field mH = activityThreadClass.getDeclaredField("mH");
            mH.setAccessible(true);
            // 获取Handler
            Handler mHandler = (Handler) mH.get(activityThread);
            // 获取原始的mCallBack字段
            Field mCallBack = Handler.class.getDeclaredField("mCallback");
            mCallBack.setAccessible(true);
            // 这里设置了我们自己实现了接口的CallBack对象
            mCallBack.set(mHandler, new ActivityThreadHandlerCallback(mHandler)) ;
        } catch (Exception e) {
            Log.e(TAG, "#hookSystemHandler#Exception>" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @className: ActivityThreadHandlerCallback
     * @classDescription: 主线程handler回调
     * @author: leibing
     * @createTime: 2017/3/25
     */
    class ActivityThreadHandlerCallback implements Handler.Callback {
        private Handler mHandler;

        private ActivityThreadHandlerCallback(Handler handler) {
            this.mHandler = handler;
        }

        @Override
        public boolean handleMessage(Message msg) {
            Log.e(TAG, "#msg.what>" + msg.what);
            // 替换之前的Intent
            if (msg.what == 100) {
                handleLauchActivity(msg);
            }
            mHandler.handleMessage(msg);
            return true;
        }

        /**
         * 启动activity
         * @author leibing
         * @createTime 2017/3/25
         * @lastModify 2017/3/25
         * @param msg
         * @return
         */
        private void handleLauchActivity(Message msg) {
            Object obj = msg.obj;
            try{
                Field intentField = obj.getClass().getDeclaredField("intent");
                intentField.setAccessible(true);
                Intent proxyInent = (Intent) intentField.get(obj);
                Intent realIntent = proxyInent.getParcelableExtra("oldIntent");
                if (realIntent != null) {
                    proxyInent.setComponent(realIntent.getComponent());
                }
            }catch (Exception e){
                Log.e(TAG, "#handleLauchActivity#Exception>" + e.getMessage());
            }
        }
    }
}
