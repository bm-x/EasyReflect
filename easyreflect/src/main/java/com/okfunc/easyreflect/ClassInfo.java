package com.okfunc.easyreflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by lWX406498 on 2017/3/9.
 */

public class ClassInfo {

    private Class mCls;
    private Class mMethodTier;
    private List<Method> mMethods = new ArrayList<>();
    private HashMap<String, List<MethodInfo>> mMethodSet = new HashMap<>();
    private HashMap<String, Field> mFieldSet = new HashMap<>();

    ClassInfo(Class cls) {
        mCls = cls;
        mMethodTier = mCls;
    }

    private List<MethodInfo> parseMethod(List<Method> methods, String name) {
        List<MethodInfo> list = new ArrayList<>();
        Iterator<Method> iterator = methods.iterator();
        while (iterator.hasNext()) {
            Method method = iterator.next();
            if (method.getName().equals(name)) {
                list.add(MethodInfo.parse(method));
                iterator.remove();
            }
        }
        return list;
    }

    private MethodInfo findMatch(List<MethodInfo> list, Object... args) {
        for (MethodInfo mi : list) {
            if (mi.match(args)) {
                return mi;
            }
        }
        return null;
    }

    private Method methodDepthSeach(String name, Object... args) {
        if (mMethodTier == null) return null;

        Method[] methods = mMethodTier.getDeclaredMethods();


        ArrayList<Method> list = new ArrayList<>(methods.length);

        for (Method m : methods) {
            list.add(m);
        }

        List<MethodInfo> mis = parseMethod(list, name);

        mMethodTier = mMethodTier.getSuperclass();
        mMethods.addAll(list);
        mMethodSet.get(name).addAll(mis);

        MethodInfo mi = findMatch(mis, args);

        if (mi != null) {
            return mi.method();
        }

        return methodDepthSeach(name, args);
    }


    Method findMethod(String name, Object... args) {
        List<MethodInfo> methods = mMethodSet.get(name);

        if (methods == null) {
            methods = new ArrayList<>();
            mMethodSet.put(name, methods);
        }

        if (!mMethods.isEmpty()) {
            MethodInfo mi = findMatch(methods, args);
            if (mi != null) return mi.method();
        }

        return methodDepthSeach(name, args);
    }

    Field findField(String name) {
        Field field = mFieldSet.get(name);

        if (field == null) {
            Class cls = mCls;
            while (cls != null) {
                try {
                    field = cls.getDeclaredField(name);
                } catch (Exception e) {}
                if (field == null) {
                    cls = cls.getSuperclass();
                    continue;
                }
                mFieldSet.put(name, field);
                break;
            }

        }

        return field;
    }
}
