package com.homemylove.core.utils;

public class StringUtil {

    public static boolean isNotEmpty(String str){
        return str != null && !str.equals("");
    }

    public static boolean isEmpty(String str){
        return !isNotEmpty(str);
    }

}
