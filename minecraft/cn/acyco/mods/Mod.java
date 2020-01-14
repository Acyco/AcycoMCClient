package cn.acyco.mods;

import cn.acyco.Client;
import cn.acyco.event.EventManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

/**
 * @author Acyco
 * @create 2020-01-15 01:12
 */
public class Mod {
    private boolean isEnable = true;
    
    protected final Minecraft mc;
    protected final FontRenderer font;
    protected final Client client;

    public Mod() {
        this.mc = Minecraft.getMinecraft();
        this.font = this.mc.fontRendererObj;
        this.client = Client.getInstance();

        setEnabled(isEnable);
    }

    private void setEnabled(boolean isEnable) {
        this.isEnable = isEnable;

        if (isEnable) {
            EventManager.register(this);
        } else {
            EventManager.unregister(this);
        } 
    }

    public boolean isEnable() {
        return isEnable;
    }
}
