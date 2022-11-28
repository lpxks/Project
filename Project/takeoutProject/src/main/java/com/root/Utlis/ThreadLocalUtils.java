package com.root.Utlis;


//保存在同一个线程的一个工具类
public class ThreadLocalUtils {

    //只要在同一线程内可以往里面存取数据都可以保证不会受到干扰
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setData(long id)
    {
        threadLocal.set(id);
    }

    public static Long getData()
    {
        return threadLocal.get();
    }
}
