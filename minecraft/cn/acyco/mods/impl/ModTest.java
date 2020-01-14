package cn.acyco.mods.impl;

import cn.acyco.gui.hub.ScreenPosition;
import cn.acyco.mods.ModDraggable;

/**
 * @author Acyco
 * @create 2020-01-15 01:20
 */
public class ModTest extends ModDraggable {
     private ScreenPosition position;
    @Override
    public int getWidth() {
        return font.getStringWidth("Hello Acyco~~~~~");
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition position) {
//        System.out.println(position.getAbsoluteX());
//        System.out.println(position.getAbsoluteY());
        font.drawString("Hello Acyco", position.getAbsoluteX() + 1, position.getAbsoluteY() + 1,-1);
    }

    @Override
    public void renderDummy(ScreenPosition position) {

        font.drawString("Hello Acyco~~~~~", position.getAbsoluteX() + 1, position.getAbsoluteY() + 1, 0xFF00FF00);
    }

    @Override
    public void save(ScreenPosition position) {
        this.position = position;
    }

    @Override
    public ScreenPosition load() {
        return position; //这里不能为null 要根据坐标返回去
    }
}
