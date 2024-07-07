package org.mossmc.mosscg.MossCheckTicket;

public class BasicInfo {
    public static String version = "V1.1.0.0.1103";
    public static String author = "MossCG";

    public static String infoTarget() {
        return "https://show.bilibili.com/api/ticket/project/get?version=134&id="+ID+"&project_id="+ID;
    }
    public static String prepareTarget() {
        return "https://show.bilibili.com/api/ticket/order/prepare?project_id="+ID;
    }
    public static String ID = "85938";
    public static boolean displayName = false;
    public static int hasTicketCount = 0;
}
