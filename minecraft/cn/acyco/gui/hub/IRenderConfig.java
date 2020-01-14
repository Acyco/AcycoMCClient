package cn.acyco.gui.hub;

/**
 * @author Acyco
 * @create 2020-01-15 00:13
 */
public interface IRenderConfig {
    public void save(ScreenPosition position);

    public ScreenPosition load();
}
