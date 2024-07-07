package org.mossmc.mosscg.MossCheckTicket;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Logger {
    public static SimpleDateFormat timeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String getNowTimeString() {
        return timeFormat.format(new Date(System.currentTimeMillis()));
    }
    public static void sendInfo(String info) {
        info = "[Info]["+getNowTimeString()+"] "+info;
        System.out.println(info);
    }
    public static void sendWarn(String info) {
        info = "[Warn]["+getNowTimeString()+"] "+info;
        System.out.println(info);
    }
    public static void sendException(Exception exception) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter= new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        String info = "[Exception]["+getNowTimeString()+"] "+stringWriter;
        System.out.println(info);
        try {
            printWriter.close();
            stringWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
