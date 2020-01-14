package cn.acyco.gui.hub;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author Acyco
 * @create 2020-01-15 00:31
 */
public class HUDConfigScreen extends GuiScreen {

    private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<>();

    protected Optional<IRenderer> selectRenderer = Optional.empty();

    private int prevX, prevY;

    public HUDConfigScreen(HUDManager hudManager) {
        Collection<IRenderer> registeredRenders = hudManager.getRegisterRenderers();
        for (IRenderer registeredRender : registeredRenders) {
            if (!registeredRender.isEnabled()) {
                continue;
            }
            ScreenPosition position = registeredRender.load();

            if (position == null) {
                position = ScreenPosition.formRelativePosition(0.5, 0.5);
            }

//            System.out.println(position.getAbsoluteX());
//            System.out.println(position.getAbsoluteY());
            adjustBounds(registeredRender, position);
            this.renderers.put(registeredRender, position);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        final float zBackup = this.zLevel;
        this.zLevel = 300;

        this.drawTestRect(0, 0, this.width - 1, this.height - 1, 0xFFFF0000);

        for (IRenderer renderer : renderers.keySet()) {
            ScreenPosition position = renderers.get(renderer);
            renderer.renderDummy(position);
            
            this.drawTestRect(position.getAbsoluteX(), position.getAbsoluteY(), renderer.getWidth(),renderer.getHeight(), 0xFF00FFFF);
        }
        this.zLevel = zBackup;
    }

    private void drawTestRect(int x, int y, int w, int h, int color) {
        this.drawHorizontalLine(x, x + w, y, color);
        this.drawHorizontalLine(x, x + w, y + h, color);
        this.drawVerticalLine(x, y + h, y, color);
        this.drawVerticalLine(x + w, y + h, y, color);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
       
        if (keyCode == Keyboard.KEY_ESCAPE) {
//            renderers.forEach(IRenderConfig::save);
            renderers.entrySet().forEach((entry)->{
                entry.getKey().save(entry.getValue());
            });
            this.mc.displayGuiScreen(null);
            renderers.forEach((render,pos)->{
                System.out.println(pos.getAbsoluteX());
                System.out.println(pos.getAbsoluteY());
            });

        }
    }


    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        if (selectRenderer.isPresent()) {
            moveSelectedRenderBy(mouseX - prevX, mouseY - prevY);
        }
        this.prevX = mouseX;
        this.prevY = mouseY;
    }

    private void moveSelectedRenderBy(int offsetX, int offsetY) {
        IRenderer renderer = selectRenderer.get();

        ScreenPosition position = renderers.get(renderer);

        position.setAbsolute(position.getAbsoluteX() + offsetX, position.getAbsoluteY() + offsetY);

        adjustBounds(renderer, position);
    }


    @Override
    public void onGuiClosed() {
        for (IRenderer renderer : renderers.keySet()) {
            renderer.save(renderers.get(renderer));
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

 /*   @Override
    protected void drawTestRect(int absoluteX, int absoleteY, int width, IRenderer renderer) {
        
    }*/

    private void adjustBounds(IRenderer renderer, ScreenPosition position) {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();

        int absoluteX = Math.max(0, Math.min(position.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
        int absoluteY = Math.max(0, Math.min(position.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));

        position.setAbsolute(absoluteX, absoluteY);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.prevX = mouseX;
        this.prevY = mouseY;
        loadMouseOver(mouseX, mouseY);
    }

    private void loadMouseOver(int mouseX, int mouseY) {
        this.selectRenderer = renderers.keySet().stream().filter(new MouseOverFinder(mouseX, mouseY)).findFirst();
    }

    private class MouseOverFinder implements Predicate<IRenderer> {
        private int mouseX, mouseY;

        public MouseOverFinder(int mouseX, int mouseY) {
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        @Override
        public boolean test(IRenderer renderer) {

            ScreenPosition position = renderers.get(renderer);

            int absoluteX = position.getAbsoluteX();
            int absoluteY = position.getAbsoluteY();

            if (mouseX >= absoluteX && mouseX <= absoluteX + renderer.getWidth()) {

                if (mouseY >= absoluteY && mouseY <= absoluteY + renderer.getHeight()) {
                    return true;
                }

            }

            return false;
        }
    }


}
