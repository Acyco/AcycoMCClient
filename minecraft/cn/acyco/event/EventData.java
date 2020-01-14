package cn.acyco.event;

import java.lang.reflect.Method;

/**
 * @author Acyco
 * @create 2020-01-14 21:39
 */
public class EventData {
    public final Object source;
    public final Method target;
    public final byte priority;

    public EventData(Object source, Method target, byte priority) {
        this.source = source;
        this.target = target;
        this.priority = priority;
    }
}
