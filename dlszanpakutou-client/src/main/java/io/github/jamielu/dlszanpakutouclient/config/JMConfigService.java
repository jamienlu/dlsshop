package io.github.jamielu.dlszanpakutouclient.config;

import io.github.jamielu.dlszanpakutouclient.reposity.JMReposity;
import io.github.jamielu.dlszanpakutouclient.reposity.JMReposityChangeListener;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author jamieLu
 * @create 2024-04-20
 */
public interface JMConfigService extends JMReposityChangeListener {
    static JMConfigService getDefault(ApplicationContext applicationContext, ConfigMeta meta) {
        JMReposity repository = JMReposity.getDefault(applicationContext, meta);
        Map<String, String> config = repository.getConfig();
        JMConfigService configService = new JMConfigServiceImpl(applicationContext, config);
        repository.addListener(configService);
        return configService;
    }

    String[] getPropertyNames();

    String getProperty(String name);
}
