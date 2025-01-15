package io.github.jamielu.dlszanpakutouserver;

import io.github.jamielu.dlszanpakutouserver.core.LockManager;
import io.github.jamielu.dlszanpakutouserver.dao.ConfigsMapper;
import io.github.jamielu.dlszanpakutouserver.model.ZanpakutouConfigs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jamieLu
 * @create 2024-04-14
 */
@RestController
public class ConfigsController {
    @Autowired
    private LockManager locks;

    @Autowired
    private ConfigsMapper configsMapper;
    private Map<String, Long> VERSIONS = new HashMap<>();

    @GetMapping("/list")
    public List<ZanpakutouConfigs> list(String app, String env, String ns) {
        return configsMapper.list(app, env, ns);
    }

    @RequestMapping("/update")
    public List<ZanpakutouConfigs> update(@RequestParam("app") String app,
                                          @RequestParam("env") String env,
                                          @RequestParam("ns") String ns,
                                          @RequestBody Map<String, String> params) {
        params.forEach((k, v) -> {
            insertOrUpdate(new ZanpakutouConfigs(app, env, ns, k, v));
        });
        VERSIONS.put(app + "-" + env + "-" + ns, System.currentTimeMillis());
        return configsMapper.list(app, env, ns);
    }

    private void insertOrUpdate(ZanpakutouConfigs configs) {
        ZanpakutouConfigs conf = configsMapper.select(configs.getApp(), configs.getEnv(),
                configs.getNs(), configs.getPkey());
        if(conf == null) {
            configsMapper.insert(configs);
        } else {
            configsMapper.update(configs);
        }
    }

    @GetMapping("/version")
    public long version(String app, String env, String ns) {
        return VERSIONS.getOrDefault(app + "-" + env + "-" + ns, -1L);
    }

    @GetMapping("/status")
    public boolean status() {
        return locks.getLocked().get();
    }
}
