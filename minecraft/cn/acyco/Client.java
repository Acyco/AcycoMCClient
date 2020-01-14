package cn.acyco;

import cn.acyco.event.EventManager;
import cn.acyco.event.EventTarget;
import cn.acyco.event.impl.ClientTickEvent;
import cn.acyco.gui.hub.HUDManager;
import cn.acyco.mods.ModInstances;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.Display;

/**
 * Acyco Client
 *
 * @author Acyco
 * @create 2020-01-14 21:19
 */
public class Client {
    public static String clientName = "Acyco MC";
    public static String clientVersion = "1.0";
    public static Client INSTANCE = new Client();
    
    private HUDManager hudManager;

    public static Client getInstance() {
        return INSTANCE;
    }

    public static void setTitle() {
        Display.setTitle(clientName+"  " + clientVersion);
    }
    public void init() {
//        EventManager.register(new TestClass());
        EventManager.register(this);
    }
    
    
    public void start() {
        hudManager = HUDManager.getInstance();
        ModInstances.register(hudManager);
    }

    @EventTarget
    public void onTick(ClientTickEvent event) {
       
        if (Minecraft.getMinecraft().gameSettings.CLIENT_GUI_MOD_POS.isPressed()) {
            hudManager.openConfigScreen();
        }
    }
}
