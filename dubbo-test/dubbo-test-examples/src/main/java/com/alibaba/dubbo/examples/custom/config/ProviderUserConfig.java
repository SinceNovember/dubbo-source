package com.alibaba.dubbo.examples.custom.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.alibaba.dubbo.examples.custom.api.NameService;
import com.alibaba.dubbo.examples.custom.api.UserService;
import com.alibaba.dubbo.examples.custom.service.NameServiceImpl;
import com.alibaba.dubbo.examples.custom.service.UserServiceImpl;

import java.util.concurrent.CountDownLatch;

public class ProviderUserConfig {

    public static void main(String[] args) throws InterruptedException {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("user");


        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setAddress("redis://127.0.0.1:6379");

        // 服务提供者协议配置
        ProtocolConfig protocol = new ProtocolConfig();

        UserService userService = new UserServiceImpl();

        // 服务提供者暴露服务配置
        ServiceConfig<UserService> service = new ServiceConfig<UserService>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
        service.setApplication(applicationConfig);
        service.setRegistry(registryConfig); // 多个注册中心可以用setRegistries()
        service.setProtocol(protocol); // 多个协议可以用setProtocols()
        service.setInterface(UserService.class);
        service.setRef(userService);
        service.setVersion("1.0.0");
        // 暴露及注册服务
        service.export();
        new CountDownLatch(1).await();

//        NameService nameService = new NameServiceImpl();
//        // 服务提供者暴露服务配置
//        ServiceConfig<NameService> nameExportService = new ServiceConfig<NameService>(); // 此实例很重，封装了与注册中心的连接，请自行缓存，否则可能造成内存和连接泄漏
//        nameExportService.setApplication(applicationConfig);
//        nameExportService.setRegistry(registryConfig); // 多个注册中心可以用setRegistries()
//        nameExportService.setProtocol(protocol); // 多个协议可以用setProtocols()
//        nameExportService.setInterface(NameService.class);
//        nameExportService.setRef(nameService);
//        nameExportService.setVersion("1.0.0");
//        nameExportService.export();



        System.out.println("");

    }
}
