package cn.acyco.gui.hub;

import cn.acyco.event.EventManager;
import cn.acyco.event.EventTarget;
import cn.acyco.event.impl.RenderEvent;
import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiContainer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

/**
 * @author Acyco
 * @create 2020-01-15 00:17
 */
public class HUDManager {
    private static HUDManager instance = null;

    private Set<IRenderer> registeredRenderers = Sets.newHashSet();

    private Minecraft MINECRAFT = Minecraft.getMinecraft();
    private HUDManager () {
        
    }

    public static HUDManager getInstance() {
        if (instance != null) {
            return instance;
        }
        instance=new HUDManager();

        EventManager.register(instance); //注册

        return instance;
    }
    
    public void register(IRenderer... renderers) {
        this.registeredRenderers.addAll(Arrays.asList(renderers));
    }

    public void unregister(IRenderer... renderers) {
          this.registeredRenderers.removeAll(Arrays.asList(renderers));
    }

    public Collection<IRenderer> getRegisterRenderers() {
        return Sets.newHashSet(registeredRenderers);
    }
    
    public void openConfigScreen() {
        MINECRAFT.displayGuiScreen(new HUDConfigScreen(this));
    }
    @EventTarget
    public void onRender(RenderEvent event) {
        if (MINECRAFT.currentScreen == null || MINECRAFT.currentScreen instanceof GuiContainer || MINECRAFT.currentScreen instanceof GuiChat) {
            for (IRenderer registeredRenderer : registeredRenderers) {
                callRenderer(registeredRenderer);
            }
        }
    }

    private void callRenderer(IRenderer registeredRenderer) {
        if (!registeredRenderer.isEnabled()) {
           return; 
        }
        ScreenPosition position = registeredRenderer.load();
        if (position == null) {
            position = ScreenPosition.formRelativePosition(0.5, 0.5);
        }
        registeredRenderer.render(position);
    }
}
