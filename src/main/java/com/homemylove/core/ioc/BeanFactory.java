package com.homemylove.core.ioc;

public interface BeanFactory {

    // 根据 id 获取 java bean
    Object getBean(String id);
}
