package cn.acyco.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Acyco
 * @create 2020-01-14 21:35
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTarget {

    byte value() default EventPriority.THIRD;
}
