package com.alibaba.dubbo.examples.custom.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.examples.custom.api.UserService;

public class ConsumerUserConfig {

    public static void main(String[] args) {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("user");
        applicationConfig.setQosPort(22223);

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("redis://127.0.0.1:6379");

        ReferenceConfig<UserService> reference = new ReferenceConfig<UserService>();
        reference.setApplication(applicationConfig);
        reference.setRegistry(registryConfig);
        reference.setInterface(UserService.class);
        UserService userService = reference.get();
        userService.testUser();
    }
}
