package org.mossmc.mosscg.MossCheckTicket.Mail;

import com.alibaba.fastjson.JSONObject;
import org.mossmc.mosscg.MossCheckTicket.BasicInfo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MailSend {
    public static void sendTicketMail(List<String> types) {
        try {
            if (!BasicInfo.config.getBoolean("enableMail")) return;

            StringBuilder builder = new StringBuilder("有票了喵！票种类如下：<br>");
            for (String type : types) builder.append(type).append("<br>");

            sendMail("MCT票务提醒",builder.toString());
        } catch (Exception e) {
            BasicInfo.logger.sendException(e);
        }
    }

    public static void sendStatusMail(String show,String old,String last) {
        try {
            if (!BasicInfo.config.getBoolean("enableMail")) return;

            StringBuilder builder = new StringBuilder("票务状态更新了喵：<br>");
            builder.append("展演：").append(show).append("<br>");
            builder.append("原状态：").append(old).append("<br>");
            builder.append("新状态：").append(last).append("<br>");

            sendMail("MCT票务状态提醒", builder.toString());
        } catch (Exception e) {
            BasicInfo.logger.sendException(e);
        }
    }

    public static void sendMail(String title,String content) throws Exception{
        BasicInfo.logger.sendInfo("正在发送邮件......");
        JSONObject data = new JSONObject();
        data.put("token",BasicInfo.config.getString("mailToken"));
        data.put("mailTitle",title);
        data.put("mailSenderName","MossCheckTicket");
        data.put("mailContent",content);

        String url = "http://"+BasicInfo.config.getString("mailAddress")+"/API";
        URL targetURL = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) targetURL.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Connection", "keep-alive");
        connection.setRequestProperty("Content-Type","application/json;charset=utf8");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36 Edg/124.0.0.0");
        connection.setDoOutput(true);

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8));
        writer.write(data.toString());
        writer.flush();
        writer.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder readInfo = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) readInfo.append(inputLine);
        reader.close();
        BasicInfo.sendDebug(readInfo.toString());

        BasicInfo.logger.sendInfo("邮件发送完成！");
    }
}
