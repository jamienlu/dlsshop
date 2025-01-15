package io.github.jamielu.dlszanpakutouclient.reposity;

import cn.kimmking.utils.HttpUtils;
import com.alibaba.fastjson.TypeReference;
import io.github.jamielu.dlszanpakutouclient.config.ConfigMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author jamieLu
 * @create 2024-04-20
 */
@Slf4j
public class JMReposityImpl implements JMReposity {
    ConfigMeta meta;
    Map<String, Long> versionMap = new HashMap<>();
    Map<String, Map<String, String>> configMap = new HashMap<>();
    ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    List<JMReposityChangeListener> listeners = new ArrayList<>();

    public JMReposityImpl(ApplicationContext applicationContext, ConfigMeta meta) {
        this.meta = meta;
        executor.scheduleWithFixedDelay(this::heartbeat, 1000, 5000, TimeUnit.MILLISECONDS);
    }

    public void addListener(JMReposityChangeListener listener) {
        listeners.add(listener);
    }

    @Override
    public Map<String, String> getConfig() {
        String key = meta.genKey();
        if (configMap.containsKey(key)) {
            return configMap.get(key);
        }
        return findAll();
    }

    private Map<String, String> findAll() {
        String listPath = meta.listPath();
        log.info("###list all configs from kk config server.");
        List<ZanpakutouConfigs> configs = HttpUtils.httpGet(listPath, new TypeReference<List<ZanpakutouConfigs>>() {
        });
        Map<String, String> resultMap = new HashMap<>();
        configs.forEach(c -> resultMap.put(c.getPkey(), c.getPval()));
        return resultMap;
    }

    private void heartbeat() {
        String versionPath = meta.versionPath();
        Long version = HttpUtils.httpGet(versionPath, new TypeReference<Long>() {
        });
        String key = meta.genKey();
        ;
        Long oldVersion = versionMap.getOrDefault(key, -1L);
        if (version > oldVersion) { // 发生了变化了
            System.out.println("### current=" + version + ", old=" + oldVersion);
            System.out.println("### need update new configs.");
            versionMap.put(key, version);
            Map<String, String> newConfigs = findAll();
            configMap.put(key, newConfigs);
            listeners.forEach(l -> l.onChange(new JMReposityChangeListener.ChangeEvent(meta, newConfigs)));
        }

    }
}