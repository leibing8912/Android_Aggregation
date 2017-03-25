# Android_Aggregation

### 2017.03.25
#### HookPractice
##### 目标	
 * hook activity启动过程
##### 实现思路
 * 反射ActivityManagerNative获取gDefault字段并拿取该对象
 * 反射SingleTon获取单例字段
 * 通过gDefault对象和单例字段从而拿到IActivityManager对象
 * 获取IActivityManager动态代理对象
 * 替换ActivityManagerNative中gDefault对象
 * 在自定义InvocationHandler中找到启动意图找到其索引并在伪造一个代理Intent(启动代理页面，此页面必须在AndroidManifest.xml有注册)
 * 反射ActivityThread获取主线程对象和mH字段
 * 通过主线程对象和mH字段拿取主线程Handler对象
 * 获取Handler原始mCallBack字段
 * 给mCallBack字段实现自定义CallBack对象
 * 在自定义CallBack对象中反射获取Intent字段，在此将代理Intent换成原始的Intent

#### CustomBroadcast、SendBroadcast
* 静态广播、动态广播、应用内广播理解
  * 对于静态注册的ContextReceiver，回调onReceive(context, intent)中的context具体指的是ReceiverRestrictedContext；

  * 对于全局广播的动态注册的ContextReceiver，回调onReceive(context, intent)中的context具体指的是Activity or Service Context；

  * 对于通过LocalBroadcastManager动态注册的ContextReceiver，回调onReceive(context, intent)中的context具体指的是Application Context。
  * 对于LocalBroadcastManager方式发送的应用内广播，只能通过LocalBroadcastManager动态注册的ContextReceiver才有可能接收到（静态注册或其他方式动态注册的ContextReceiver是接收不到的）
  * Android 3.1开始系统在Intent与广播相关的flag增加了参数，分别是FLAG_INCLUDE_STOPPED_PACKAGES和FLAG_EXCLUDE_STOPPED_PACKAGES。FLAG_INCLUDE_STOPPED_PACKAGES：包含已经停止的包（停止：即包所在的进程已经退出）
FLAG_EXCLUDE_STOPPED_PACKAGES：不包含已经停止的包
  * Android3.1开始，系统本身则增加了对所有app当前是否处于运行状态的跟踪。在发送广播时，不管是什么广播类型，系统默认直接增加了值为FLAG_EXCLUDE_STOPPED_PACKAGES的flag，导致即使是静态注册的广播接收器，对于其所在进程已经退出的app，同样无法接收到广播。
  * 对于自定义的广播，可以通过复写此flag为FLAG_INCLUDE_STOPPED_PACKAGES，使得静态注册的BroadcastReceiver，即使所在App进程已经退出，也能能接收到广播，并会启动应用进程，但此时的BroadcastReceiver是重新新建的。
  * 经测试以上flag调起停止包广播在国产rom上显得鸡肋，无法使用，只需知道有这么回事即可。