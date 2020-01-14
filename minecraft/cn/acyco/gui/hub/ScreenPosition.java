package cn.acyco.gui.hub;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

/**
 * @author Acyco
 * @create 2020-01-15 00:01
 */
public class ScreenPosition {
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    protected double x, y;

    public ScreenPosition(double x, double y) {
        setRelative(x, y);
    }

    public ScreenPosition(int x, int y) {
        setAbsolute(x, y);
    }


    public static ScreenPosition formRelativePosition(double x, double y) {
        return new ScreenPosition(x, y);
    }

    public static ScreenPosition fromAbsolutePosition(int x, int y) {
        return new ScreenPosition(x, y);
    }

    public int getAbsoluteX() {
        ScaledResolution res = new ScaledResolution(MINECRAFT);
        return (int) (x * res.getScaledWidth());
    }

    public int getAbsoluteY() {
        ScaledResolution res = new ScaledResolution(MINECRAFT);
        return (int) (y * res.getScaledHeight());
    }


    public double getRelativeX() {
        return x;
    }

    public double getRelativeY() {
        return y;
    }

    public void setAbsolute(int x, int y) {
        ScaledResolution res = new ScaledResolution(MINECRAFT);
        this.x = (double) x / res.getScaledWidth();
        this.y = (double) y / res.getScaledHeight();
    }

    private void setRelative(double x, double y) {

        this.x = x;
        this.y = y;
    }

}
