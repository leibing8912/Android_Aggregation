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