package org.mossmc.mosscg.MossCheckTicket.Web;

public class WebBasic {
    public static void init() {
        System.setProperty("sun.net.client.defaultConnectTimeout", "3000");
        System.setProperty("sun.net.client.defaultReadTimeout", "3000");
        System.setProperty("http.keepAlive", "false");
    }
}
