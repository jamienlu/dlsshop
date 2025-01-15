package io.github.jamielu.dlszanpakutouserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jamieLu
 * @create 2024-04-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZanpakutouConfigs {
    private String app;
    private String env;
    private String ns;
    private String pkey;
    private String pval;
}
