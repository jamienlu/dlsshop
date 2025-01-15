package io.github.jamielu.dlszanpakutouclient.config;

import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
/**
 * @author jamieLu
 * @create 2024-04-20
 */
@Data
public class PropertySourcesProcessor implements BeanFactoryPostProcessor, ApplicationContextAware, EnvironmentAware, PriorityOrdered {

    private final static String KK_PROPERTY_SOURCES = "KKPropertySources";
    private final static String KK_PROPERTY_SOURCE  = "KKPropertySource";
    Environment environment;
    ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        ConfigurableEnvironment ENV = (ConfigurableEnvironment) environment;
        if(ENV.getPropertySources().contains(KK_PROPERTY_SOURCES)) {
            return;
        }

        String app = ENV.getProperty("config.app", "app1");
        String env = ENV.getProperty("config.env", "dev");
        String ns = ENV.getProperty("config.ns", "public");
        String configServer = ENV.getProperty("config.configServer", "http://localhost:9200");

        ConfigMeta configMeta = new ConfigMeta(app, env, ns, configServer);

        JMConfigService configService = JMConfigService.getDefault(applicationContext, configMeta);
        JMProperSource propertySource = new JMProperSource(KK_PROPERTY_SOURCE, configService);
        CompositePropertySource composite = new CompositePropertySource(KK_PROPERTY_SOURCES);
        composite.addPropertySource(propertySource);
        ENV.getPropertySources().addFirst(composite);

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

}
