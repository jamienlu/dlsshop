package io.github.jamielu.dlszanpakutouclient.annotation;

import io.github.jamielu.dlszanpakutouclient.config.JMConfigRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author jamieLu
 * @create 2024-04-20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Import({JMConfigRegister.class})
public @interface JMConfigEnable {
}
