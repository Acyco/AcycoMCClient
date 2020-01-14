package cn.acyco.gui.hub;

/**
 * @author Acyco
 * @create 2020-01-15 00:15
 */
public interface IRenderer extends IRenderConfig{
    int getWidth();
    int getHeight();

    void render(ScreenPosition position);

    default void renderDummy(ScreenPosition position) {
        render(position);
    }

    public default boolean isEnabled() {
        return true;
    }
}
