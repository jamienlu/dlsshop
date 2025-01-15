package io.github.jamielu.dlszanpakutouclient.config;

import org.springframework.core.env.EnumerablePropertySource;

/**
 * @author jamieLu
 * @create 2024-04-20
 */
public class JMProperSource extends EnumerablePropertySource<JMConfigService> {
    public JMProperSource(String name, JMConfigService source) {
        super(name, source);
    }

    @Override
    public String[] getPropertyNames() {
        return source.getPropertyNames();
    }

    @Override
    public Object getProperty(String name) {
        return source.getProperty(name);
    }
}
