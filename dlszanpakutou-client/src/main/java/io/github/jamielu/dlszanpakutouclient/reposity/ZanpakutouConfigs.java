package io.github.jamielu.dlszanpakutouclient.reposity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jamieLu
 * @create 2024-04-20
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
