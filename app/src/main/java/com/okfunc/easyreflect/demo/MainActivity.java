package com.okfunc.easyreflect.demo;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

import static com.okfunc.easyreflect.ER.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "bm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void btn(View view) {
//        Resources res = (Resources) get(this, "mResources");

        try {
            Class cls = getClass();
            Field[] fs = cls.getDeclaredFields();

            for (Field f : fs) {
                Log.i(TAG, "" + f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
