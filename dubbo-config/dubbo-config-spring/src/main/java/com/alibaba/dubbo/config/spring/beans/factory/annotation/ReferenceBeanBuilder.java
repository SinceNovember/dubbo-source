/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.config.spring.beans.factory.annotation;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.ReferenceBean;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import static com.alibaba.dubbo.config.spring.util.BeanFactoryUtils.getOptionalBean;

/**
 * {@link ReferenceBean} Builder
 *
 * @since 2.5.7
 */
class ReferenceBeanBuilder extends AbstractAnnotationConfigBeanBuilder<Reference, ReferenceBean> {


    private ReferenceBeanBuilder(Reference annotation, ClassLoader classLoader, ApplicationContext applicationContext) {
        super(annotation, classLoader, applicationContext);
    }

    private void configureInterface(Reference reference, ReferenceBean referenceBean) {

        // 首先，从 @Reference 获得 interfaceName 属性，从而获得 interfaceClass 类
        Class<?> interfaceClass = reference.interfaceClass();
        if (void.class.equals(interfaceClass)) {

            interfaceClass = null;

            String interfaceClassName = reference.interfaceName();

            if (StringUtils.hasText(interfaceClassName)) {
                if (ClassUtils.isPresent(interfaceClassName, classLoader)) {
                    interfaceClass = ClassUtils.resolveClassName(interfaceClassName, classLoader);
                }
            }

        }

        // 如果获得不到，则使用 interfaceClass 即可
        if (interfaceClass == null) {
            interfaceClass = this.interfaceClass;
        }

        Assert.isTrue(interfaceClass.isInterface(),
                "The class of field or method that was annotated @Reference is not an interface!");

        referenceBean.setInterface(interfaceClass);

    }


    private void configureConsumerConfig(Reference reference, ReferenceBean<?> referenceBean) {

        // 获得 ConsumerConfig 对象
        String consumerBeanName = reference.consumer();

        //从工厂中获取ConsumerConfig的Bean
        ConsumerConfig consumerConfig = getOptionalBean(applicationContext, consumerBeanName, ConsumerConfig.class);
        // 设置到 referenceBean 中
        referenceBean.setConsumer(consumerConfig);

    }

    @Override
    protected ReferenceBean doBuild() {
        // 创建 ReferenceBean 对象
        return new ReferenceBean<Object>(annotation);
    }

    @Override
    protected void preConfigureBean(Reference annotation, ReferenceBean bean) {
        Assert.notNull(interfaceClass, "The interface class must set first!");
    }

    @Override
    protected String resolveModuleConfigBeanName(Reference annotation) {
        return annotation.module();
    }

    @Override
    protected String resolveApplicationConfigBeanName(Reference annotation) {
        return annotation.application();
    }

    @Override
    protected String[] resolveRegistryConfigBeanNames(Reference annotation) {
        return annotation.registry();
    }

    @Override
    protected String resolveMonitorConfigBeanName(Reference annotation) {
        return annotation.monitor();
    }

    @Override
    protected void postConfigureBean(Reference annotation, ReferenceBean bean) throws Exception {

        // 设置 applicationContext
        bean.setApplicationContext(applicationContext);

        // 配置 interfaceClass
        configureInterface(annotation, bean);

        // 配置 ConsumerConfig
        configureConsumerConfig(annotation, bean);

        // 执行 Bean 后置属性初始化
        bean.afterPropertiesSet();

    }

    public static ReferenceBeanBuilder create(Reference annotation, ClassLoader classLoader,
                                              ApplicationContext applicationContext) {
        return new ReferenceBeanBuilder(annotation, classLoader, applicationContext);
    }

}
