package com.okfunc.easyreflect;

import java.lang.reflect.Method;

/**
 *
 * Created by lWX406498 on 2017/3/9.
 */

public class MethodInfo {

    static MethodInfo parse(Method method) {
        MethodInfo mi = new MethodInfo();

        mi.mMethod = method;
        mi.mArguments = method.getParameterTypes();

        if (mi.mArguments == null) mi.mArguments = new Class[0];

        return mi;
    }

    private Class<?>[] mArguments;
    private Method mMethod;

    boolean match(Object... args) {
        if (args == null || args.length != mArguments.length) return false;

        for (int i = 0; i < args.length; i++) {
            if (!mArguments[i].isAssignableFrom(args[i].getClass())) return false;
        }

        return true;
    }

    Method method() {
        return mMethod;
    }

    @Override
    public String toString() {
        return mMethod.getName();
    }
}
