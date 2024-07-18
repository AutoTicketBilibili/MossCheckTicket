package org.mossmc.mosscg.MossCheckTicket.Web;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.mossmc.mosscg.MossCheckTicket.BasicInfo;
import org.mossmc.mosscg.MossCheckTicket.Mail.MailSend;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class WebReader {
    public static void readData(String data) {
        JSONObject fullData = JSONObject.parseObject(data);
        JSONObject showData = fullData.getJSONObject("data");

        if (!BasicInfo.displayName) {
            BasicInfo.logger.sendInfo("当前监听活动："+showData.getString("name"));
            BasicInfo.displayName = true;
        }

        String ticketStatus = showData.getString("sale_flag");
        checkTicketStatus(ticketStatus);

        JSONArray ticketData = showData.getJSONArray("screen_list");
        AtomicBoolean hasTicket = new AtomicBoolean(false);
        ticketData.forEach((date) -> {
            JSONObject dateData = JSONObject.parseObject(date.toString());
            if (dateData.getBoolean("clickable")) {
                hasTicket.set(true);
            }
        });

        if (hasTicket.get()) {
            BasicInfo.hasTicketCount++;
            String randomSpilt = getRandomSpilt();

            BasicInfo.logger.sendInfo("---------------------------------------------"+randomSpilt);
            BasicInfo.logger.sendInfo("|            发现余票！！！请及时抢票！！！        ");
            BasicInfo.logger.sendInfo("---------------------------------------------"+randomSpilt);
            BasicInfo.logger.sendInfo("|                  =余票类型=                  ");

            List<String> tickets = new ArrayList<>();
            ticketData.forEach((date) -> {
                JSONObject dateData = JSONObject.parseObject(date.toString());
                if (dateData.getBoolean("clickable")) {
                    JSONArray typeArray = dateData.getJSONArray("ticket_list");
                    typeArray.forEach((type) -> {
                        JSONObject typeData = JSONObject.parseObject(type.toString());
                        if (typeData.getBoolean("clickable")) {
                            String name = "|           " + dateData.getString("name") + " " + typeData.getString("desc");
                            BasicInfo.logger.sendInfo(name);
                            tickets.add(name);
                        }
                    });
                }
            });

            BasicInfo.logger.sendInfo("---------------------------------------------"+randomSpilt);
            BasicInfo.logger.sendInfo("|                                            ");
            BasicInfo.logger.sendInfo("|         一定要快抢票噢~                       ");
            BasicInfo.logger.sendInfo("|                    一定要快抢票噢~            ");
            BasicInfo.logger.sendInfo("|                               一定要快抢票噢~ ");
            BasicInfo.logger.sendInfo("|                                            ");
            BasicInfo.logger.sendInfo("---------------------------------------------"+randomSpilt);
            BasicInfo.logger.sendInfo("累计发现余票次数："+BasicInfo.hasTicketCount);

            if (!BasicInfo.lastCheck) {
                BasicInfo.lastCheck = true;
                MailSend.sendMail(tickets);
            }
        } else {
            BasicInfo.lastCheck = false;
        }
    }

    public static void checkTicketStatus(String status) {
        if (BasicInfo.lastStatus.equals(status)) return;
        String randomSpilt = getRandomSpilt();
        BasicInfo.logger.sendInfo("---------------------------------------------"+randomSpilt);
        BasicInfo.logger.sendInfo("       余票状态更新："+BasicInfo.lastStatus+" -> "+status);
        switch (status) {
            case "预售中":
                BasicInfo.logger.sendInfo("     这种情况代表正在售卖哦！快去抢票吧！");
                break;
            case "暂时售罄":
                BasicInfo.logger.sendInfo("     这种情况代表票仓开启，随时可能放票！");
                break;
            case "已售罄":
                BasicInfo.logger.sendInfo("     这种情况代表关闭了票仓，B站正在屯票ing......");
                break;
            case "已结束":
                BasicInfo.logger.sendInfo("     这种情况代表漫展已经结束啦~");
                break;
            default:
                break;
        }
        BasicInfo.logger.sendInfo("---------------------------------------------"+randomSpilt);
        BasicInfo.lastStatus = status;
    }

    public static String getRandomSpilt() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int count = random.nextInt(10);
        for (int i = 0; i < count; i++) {
            stringBuilder.append("-");
        }
        return stringBuilder.toString();
    }
}
