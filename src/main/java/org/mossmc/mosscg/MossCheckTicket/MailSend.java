package org.mossmc.mosscg.MossCheckTicket;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class MailSend {
    public static void sendMail(List<String> types) {
        try {
            if (!BasicInfo.enableMail) return;
            Logger.sendInfo("正在发送邮件......");
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.host", BasicInfo.mailSMTPHost);
            props.setProperty("mail.smtp.auth", "true");
            //props.setProperty("mail.smtp.port", mailSMTPPort);
            Session mailSession = Session.getInstance(props);
            mailSession.setDebug(true);
            MimeMessage message = new MimeMessage(mailSession);
            StringBuilder data = new StringBuilder("有票了喵！票种类如下：<br>");
            for (String type : types) {
                data.append(type);
                data.append("<br>");
            }
            message.setFrom(new InternetAddress(BasicInfo.mailAccount, "MossCheckTicket", "UTF-8"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(BasicInfo.mailReceive, BasicInfo.mailReceive, "UTF-8"));
            message.setSubject("MCT票务提醒", "UTF-8");
            message.setContent(data.toString(), "text/html;charset=UTF-8");
            message.setSentDate(new Date());
            message.saveChanges();
            Transport mailTransport = mailSession.getTransport();
            mailTransport.connect(BasicInfo.mailAccount, BasicInfo.mailPassword);
            mailTransport.sendMessage(message, message.getAllRecipients());
            mailTransport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
