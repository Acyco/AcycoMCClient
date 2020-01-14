package cn.acyco.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 事件管理器
 * @author Acyco
 * @create 2020-01-14 21:45
 */
public class EventManager {
    //注册表
    private static final Map<Class<? extends Event>, ArrayList<EventData>> REGISTRY_MAP = new HashMap<Class<? extends Event>, ArrayList<EventData>>();

    /**
     *  存在类/则进行优先级排序 
     * @param clazz
     */
    private static void sortListValue(final Class<? extends Event> clazz) {
        final ArrayList<EventData> flexableArray = new ArrayList<>();
        for (final byte b : EventPriority.VALUE_ARRAY) {
            for (EventData eventData : EventManager.REGISTRY_MAP.get(clazz)) {
                if (eventData.priority == b) {
                    flexableArray.add(eventData);
                }


            }
        }
        EventManager.REGISTRY_MAP.put(clazz, flexableArray);

    }

    //不可用的方法
    private static boolean isMethodBad(final Method method) {
        System.out.println(method.getParameterTypes().length+"s");
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
    }

    private static boolean isMethodBad(final Method method, final Class<? extends Event> clazz) {
        return isMethodBad(method) || method.getParameterTypes()[0].equals(clazz);
    }

    public static ArrayList<EventData> get(final Class<? extends Event> clazz) {
        return REGISTRY_MAP.get(clazz);
    }

//清空
    public static void cleamMap(final boolean removeOnlyEmptyValue) {
        final Iterator<Map.Entry<Class<? extends Event>, ArrayList<EventData>>> iterator = EventManager.REGISTRY_MAP.entrySet().iterator();

        while (iterator.hasNext()) {
            if (!removeOnlyEmptyValue || iterator.next().getValue().isEmpty()) {
                iterator.remove();
            }
        }
    }
     //取消注册 
    public static void unregister(final Object o, final Class<? extends Event> clazz) {
        if (REGISTRY_MAP.containsKey(clazz)) {
            for (final EventData eventData : REGISTRY_MAP.get(clazz)) {
                if (eventData.source.equals(o)) {
                    REGISTRY_MAP.get(clazz).remove(eventData);
                }
            }
        }
        cleamMap(true);
    }

    public static void unregister(final Object o) {
        for (ArrayList<EventData> flexableArray : REGISTRY_MAP.values()) {
            for (int i = flexableArray.size() - 1; i >= 0; i--) {
                if (flexableArray.get(i).source.equals(0)) {
                    flexableArray.remove(i);
                }
            }
        }
        cleamMap(true);
    }

    /**
     * 注册方法
     * @param method 方法
     * @param obj
     */
    public static void register(final Method method, final Object obj) {
        final Class<?> clazz = method.getParameterTypes()[0];
        final EventData eventData = new EventData(obj, method, method.getAnnotation(EventTarget.class).value());//注释判断 
        if (!eventData.target.isAccessible()) { //不可访问
            eventData.target.setAccessible(true);//打开访问
        }

        if (REGISTRY_MAP.containsKey(clazz)) { //类已经存在
            if (!REGISTRY_MAP.get(clazz).contains(eventData)) {//方法不存在
                sortListValue((Class<? extends Event>) clazz);//排序 再添加
            }
        } else {
            REGISTRY_MAP.put((Class<? extends Event>) clazz, new ArrayList<EventData>() {
                {
                    this.add(eventData);//this-> ArrayList  加到ArrayList   ArrayList 再压入注册表
                }
            });

        }
    }

    public static void register(final Object obj, final Class<? extends Event> clazz) {

        for (Method method : obj.getClass().getMethods()) {
            if (!isMethodBad(method, clazz)) {
                register(method, obj);

            }
        }
    }

    public static void register(Object o) {
        
        for (final Method method : o.getClass().getMethods()) {//取出实例所有的方法
            if (!isMethodBad(method)) {
                register(method,o);
            }
        }
     
    }
}
