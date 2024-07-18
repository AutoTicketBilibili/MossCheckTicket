package org.mossmc.mosscg.MossCheckTicket;

import org.mossmc.mosscg.MossLib.Object.ObjectConfig;
import org.mossmc.mosscg.MossLib.Object.ObjectLogger;

public class BasicInfo {
    public static String version = "V2.1.0.0.0016";
    public static String author = "MossCG";

    public static ObjectLogger logger;
    public static ObjectConfig config;

    public static String infoTarget() {
        String ID = config.getString("showID");
        return "https://show.bilibili.com/api/ticket/project/get?version=134&id="+ID+"&project_id="+ID;
    }
    public static String lastStatus = "未知";
    public static int hasTicketCount = 0;
    public static boolean displayName = false;
    public static boolean lastCheck = false;

    public static boolean debug = false;
    public static void sendDebug(String message) {
        if (debug) logger.sendAPI(message);
    }

}
