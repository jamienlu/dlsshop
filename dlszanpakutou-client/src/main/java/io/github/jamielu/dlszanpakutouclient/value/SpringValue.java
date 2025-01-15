package io.github.jamielu.dlszanpakutouclient.value;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author jamieLu
 * @create 2024-04-20
 */
@Data
@AllArgsConstructor
public class SpringValue {
    private Object bean;
    private String beanName;
    private String key;
    private String placeholder;
    private Field field;
}
