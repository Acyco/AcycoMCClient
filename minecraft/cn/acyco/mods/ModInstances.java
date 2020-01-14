package cn.acyco.mods;

import cn.acyco.gui.hub.HUDManager;
import cn.acyco.mods.impl.ModTest;

/**
 * @author Acyco
 * @create 2020-01-15 01:26
 */
public class ModInstances {
    private static ModTest modTest;

    public static void register(HUDManager hudManager) {
        modTest = new ModTest();
        hudManager.register(modTest);
    }
    

    public static ModTest getModTest() {
        return modTest;
    }
}
