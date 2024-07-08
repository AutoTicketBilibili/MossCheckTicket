package org.mossmc.mosscg.MossCheckTicket;

public class BasicInfo {
    public static String version = "V1.2.0.0.1103";
    public static String author = "MossCG";

    public static String infoTarget() {
        return "https://show.bilibili.com/api/ticket/project/get?version=134&id="+ID+"&project_id="+ID;
    }
    public static String ID = "85938";
    public static boolean displayName = false;
    public static int hasTicketCount = 0;

    //邮箱功能默认不开启，构建的时候自己填好自己开
    //我懒得写config功能了
    public static boolean enableMail = false;
    public static String mailAccount = "";
    public static String mailPassword = "";
    public static String mailSMTPHost = "smtp.exmail.qq.com";
    public static String mailReceive = "";
}
