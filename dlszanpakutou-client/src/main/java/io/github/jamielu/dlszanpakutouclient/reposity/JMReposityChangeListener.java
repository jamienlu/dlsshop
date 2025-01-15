package io.github.jamielu.dlszanpakutouclient.reposity;

import io.github.jamielu.dlszanpakutouclient.config.ConfigMeta;

import java.util.Map;

/**
 * @author jamieLu
 * @create 2024-04-20
 */
public interface JMReposityChangeListener {
    void onChange(ChangeEvent event);
    record ChangeEvent(ConfigMeta meta, Map<String, String> config) {}
}
