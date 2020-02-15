package com.codingwithset.ear.utils;

import android.util.Patterns;

public class Utils {
    private static Utils instance;

    public static Utils getInstance() {
        if (instance == null){
            instance = new Utils();
        }
        return instance;
    }

    public  boolean isValidEmail(CharSequence target) {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
