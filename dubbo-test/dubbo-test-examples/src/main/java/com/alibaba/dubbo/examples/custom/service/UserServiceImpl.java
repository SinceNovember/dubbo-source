package com.alibaba.dubbo.examples.custom.service;

import com.alibaba.dubbo.examples.custom.api.UserService;

public class UserServiceImpl implements UserService {

    @Override
    public void testUser() {
        System.out.println("调用service");
    }

    @Override
    public void myTest() {
        System.out.println("调用myTest");
    }

}
