package cn.acyco;

import cn.acyco.event.EventTarget;
import cn.acyco.event.impl.ClientTickEvent;

/**
 * @author Acyco
 * @create 2020-01-14 22:21
 */
public class TestClass {
    @EventTarget
    public void test(ClientTickEvent event) {
//        System.out.println("Client tick evient is being~~~ ");
    }
}
