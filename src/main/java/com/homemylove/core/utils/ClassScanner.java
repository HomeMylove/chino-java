package com.homemylove.core.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassScanner {

    public static List<String> scanClasses(String scanPackage){
        List<String> classNames = new ArrayList<>();
        doScan(scanPackage,classNames);
        return classNames;
    }

    private static void doScan(String scanPackage,List<String> classNames) {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        path = path + scanPackage.replaceAll("\\.", "/");
        File pack = new File(path);
        File[] files = pack.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                // 递归
                doScan(scanPackage + "." + file.getName(),classNames);
            } else if (file.getName().endsWith(".class")) {
                String className = scanPackage + "." + file.getName().replaceAll(".class", "");
                classNames.add(className);
            }
        }
    }
}
