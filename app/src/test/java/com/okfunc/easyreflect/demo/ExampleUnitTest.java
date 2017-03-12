package com.okfunc.easyreflect.demo;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.*;
import static com.okfunc.easyreflect.ER.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        B b = new B();
        String str = (String) get(A.class, "TAG");
        System.out.println(str);
    }

    public static class B extends A {
        public String mTime = "02:21";
    }

    public static class A {
        static String TAG = "bm-test";
        public String mDate = "2017";
    }
}