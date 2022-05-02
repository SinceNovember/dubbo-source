package com.alibaba.dubbo.examples.custom.service;

import com.alibaba.dubbo.examples.custom.api.NameService;

public class NameServiceImpl implements NameService {
    @Override
    public void testName() {
        System.out.println("testName");
    }
}
