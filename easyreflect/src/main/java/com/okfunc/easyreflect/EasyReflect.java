package com.okfunc.easyreflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by lWX406498 on 2017/3/9.
 */
public class EasyReflect {

    private static HashMap<Class, ClassInfo> mClassCache = new HashMap<>();

    static Object er(Object... args) throws Exception {
        if (args == null || args.length <= 0) return null;

        // 静态方法调用
        if (args[0] instanceof String) {
            return staticMethodCallFromStackTrace((String) args[0]);
        } else {
            return call(args[0], (String) args[1], parameterCopy(args));
        }
    }

    static Object call(Object target, String methodName, Object... args) {
        Method method = method(target, methodName, args);

        if (method != null) {
            try {
                return method.invoke(target, args);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Field field(Object target, String fieldName) {
        return getTargetClassInfo(target).findField(fieldName);
    }

    public static Method method(Object target, String methodName, Object... args) {
        return getTargetClassInfo(target).findMethod(methodName, args);
    }

    private static ClassInfo getTargetClassInfo(Object target) {
        Class cls = (target instanceof Class) ? (Class) target : target.getClass();

        ClassInfo ci = mClassCache.get(cls);

        if (ci == null) {
            ci = new ClassInfo(cls);
            mClassCache.put(cls, ci);
        }

        return ci;
    }

    private static Object staticMethodCallFromStackTrace(String methodName, Object... args) throws Exception {
        StackTraceElement[] stes = new Throwable().getStackTrace();
        if (stes.length < 3) return null;
        String pkgName = stes[3].getClassName();
        pkgName = pkgName.substring(0, pkgName.lastIndexOf('.'));

        for (int i = 3; i < stes.length; i++) {
            String clsName = stes[i].getClassName();
            if (!clsName.startsWith(pkgName)) return null;
            Class cls = Class.forName(clsName);
            Method method = method(cls, methodName, args);
            if (method != null) return method.invoke(cls, args);
        }
        return null;
    }

    private static Object[] parameterCopy(Object... args) {
        if (args.length <= 1) return null;

        Object[] params = new Object[args.length - 2];

        for (int i = 2, j = 0; i < args.length; i++, j++) {
            params[j] = args[i];
        }

        return params;
    }
}
