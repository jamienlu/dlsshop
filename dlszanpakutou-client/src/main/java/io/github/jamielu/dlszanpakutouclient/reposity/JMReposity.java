package io.github.jamielu.dlszanpakutouclient.reposity;

import io.github.jamielu.dlszanpakutouclient.config.ConfigMeta;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author jamieLu
 * @create 2024-04-20
 */
public interface JMReposity {
    static JMReposity getDefault(ApplicationContext applicationContext, ConfigMeta meta) {
        return new JMReposityImpl(applicationContext, meta);
    }

    Map<String, String> getConfig();
    void addListener(JMReposityChangeListener listener);
}
