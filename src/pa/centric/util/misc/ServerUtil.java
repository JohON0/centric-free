package pa.centric.util.misc;

import pa.centric.util.IMinecraft;

public class ServerUtil implements IMinecraft {
    static String holyWorldName = "holyworld";
    static String reallyWorldName = "reallyworld";

    public static boolean isRW() {
        if (mc.getCurrentServerData() == null) {
            return false;
        }
        return mc.getCurrentServerData().serverIP.toLowerCase().contains(reallyWorldName) && (mc.ingameGUI.overlayPlayerList.footer.getString().contains(reallyWorldName) || mc.ingameGUI.overlayPlayerList.header.getString().toLowerCase().contains(reallyWorldName.toLowerCase()));
    }

    public static boolean isHW() {
        if (mc.getCurrentServerData() == null) {
            return false;
        }
        return mc.getCurrentServerData().serverIP.toLowerCase().contains(holyWorldName) && (mc.ingameGUI.overlayPlayerList.footer.getString().toLowerCase().contains(holyWorldName));
    }
}
