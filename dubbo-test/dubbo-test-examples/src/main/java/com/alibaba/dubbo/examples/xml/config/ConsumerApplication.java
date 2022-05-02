package com.alibaba.dubbo.examples.xml.config;

import com.alibaba.dubbo.examples.custom.api.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/dubbo-demo-injvm.xml");
        UserService userService = context.getBean(UserService.class);
//        userService.testUser();
    }
}
