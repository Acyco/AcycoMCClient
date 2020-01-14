package cn.acyco.mods;

import cn.acyco.gui.hub.IRenderer;
import cn.acyco.gui.hub.ScreenPosition;

/**
 * @author Acyco
 * @create 2020-01-15 01:17
 */
public abstract class ModDraggable extends Mod implements IRenderer {
    public final int getLineOffset(ScreenPosition position, int lineNum) {
        return position.getAbsoluteY() + getLineOffset(lineNum);
    }

    private int getLineOffset(int lineNum) {
        return font.FONT_HEIGHT + 3 * lineNum;
    }
}
