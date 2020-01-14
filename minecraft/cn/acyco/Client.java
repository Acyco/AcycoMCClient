package cn.acyco;

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

    public static void setTitle() {
        Display.setTitle(clientName+"  " + clientVersion);
    }
    public void init() {
        
    }
}
