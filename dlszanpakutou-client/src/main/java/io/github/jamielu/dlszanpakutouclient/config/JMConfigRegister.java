package io.github.jamielu.dlszanpakutouclient.config;

import io.github.jamielu.dlszanpakutouclient.value.SpringValueHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author jamieLu
 * @create 2024-04-20
 */
@Slf4j
public class JMConfigRegister implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        registerClass(registry, PropertySourcesProcessor.class);
        registerClass(registry, SpringValueHandler.class);
    }

    private static void registerClass(BeanDefinitionRegistry registry, Class<?> aClass) {
       log.info("###registerClass: " + aClass.getName());
        Optional<String> first = Arrays.stream(registry.getBeanDefinitionNames())
                .filter(x -> aClass.getName().equals(x)).findFirst();

        if (first.isPresent()) {
            log.info("###PropertySourcesProcessor already registered");
            return;
        }
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .genericBeanDefinition(aClass).getBeanDefinition();
        registry.registerBeanDefinition(aClass.getName(), beanDefinition);
    }
}
