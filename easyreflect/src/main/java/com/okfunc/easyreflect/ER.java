package com.okfunc.easyreflect;

import java.util.Objects;

/**
 * EasyReflect. 一个简单，快速使用反射的库
 * <p>
 * Created by lWX406498 on 2017/3/9.
 */
public class ER {

    public static Object er(Object... args) {
        try {
            return EasyReflect.er(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void call(String method, Object... args) {

    }

    public static void call(Object target, String method, Object... args) {
        EasyReflect.call(target, method, args);
    }

    public static Object get(Object target,String f) {
        return EasyReflect.get(target, f);
    }

    /**
     * 在 runnable 方法中执行指定的方法
     *
     * @return 封装过后的Runable
     */
    public static Runnable runnable() {
        return new Runnable() {
            @Override
            public void run() {

            }
        };
    }
}
