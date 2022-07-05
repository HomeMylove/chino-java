package com.homemylove.core.utils;

import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Field;

public class JSONUtil {

    // 将 json 对象上的属性映射到 clazz 的对象上
    // clazz 应该具有 一个无参构造方法
    public static <T> T jsonObjToJavaObj(JSONObject jsonObject, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        for (Field field : fields) {
            // 一般类型
            if (isCommonField(field)) {
                String typeName = field.getType().getName();  // 需要赋值的字段类型
                Object o = jsonObject.get(changeFileName(field.getName()));
                field.setAccessible(true);
                if (o == null) {
                    try {
                        field.set(t, null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        if (typeName.equals("java.lang.Integer") || typeName.equals("java.lang.Long") || typeName.equals("java.lang.Double"))
                            field.set(t, Integer.parseInt(o.toString()));
                        else if (typeName.equals("")) {
                            
                        } else if (typeName.equals("java.lang.String")) {
                            field.set(t, o.toString());
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return t;
    }


    // 判断是不是一般类型
    // 如果是 可以直接赋值
    // 否则 创建对应的对象实例
    private static boolean isCommonField(Field field) {
        if (field != null) {
            Class<?> fieldType = field.getType();
            return fieldType.equals(Integer.class) ||
                    fieldType.equals(String.class) ||
                    fieldType.equals(Long.class) ||
                    fieldType.equals(Double.class) ||
                    fieldType.equals(boolean.class);
        }
        return false;
    }

    // 修改字段名
    // 添加下划线 大写变小写
    private static String changeFileName(String oldName) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < oldName.length(); i++) {
            char c = oldName.charAt(i);
            if ('A' <= c && c <= 'Z') {
                sb.append("_").append((char) (c + 32));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


}
