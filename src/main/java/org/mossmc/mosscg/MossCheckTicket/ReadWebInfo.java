package org.mossmc.mosscg.MossCheckTicket;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.mossmc.mosscg.MossCheckTicket.Logger.sendInfo;

public class ReadWebInfo {
    public static void readData(String data) {
        JSONObject fullData = JSONObject.parseObject(data);
        JSONObject showData = fullData.getJSONObject("data");
        JSONArray ticketData = showData.getJSONArray("screen_list");
        if (!BasicInfo.displayName) {
            sendInfo("当前监听活动："+showData.getString("name"));
            BasicInfo.displayName = true;
        }

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
            sendInfo("---------------------------------------------"+randomSpilt);
            sendInfo("|            发现余票！！！请及时抢票！！！        ");
            sendInfo("---------------------------------------------"+randomSpilt);
            sendInfo("|                  =余票类型=                  ");
            ticketData.forEach((date) -> {
                JSONObject dateData = JSONObject.parseObject(date.toString());
                if (dateData.getBoolean("clickable")) {
                    JSONArray typeArray = dateData.getJSONArray("ticket_list");
                    typeArray.forEach((type) -> {
                        JSONObject typeData = JSONObject.parseObject(type.toString());
                        if (typeData.getBoolean("clickable")) {
                            sendInfo("|           " + dateData.getString("name") + " " + typeData.getString("desc"));
                        }
                    });
                }
            });
            sendInfo("---------------------------------------------"+randomSpilt);
            sendInfo("|                                            ");
            sendInfo("|         一定要快抢票噢~                       ");
            sendInfo("|                    一定要快抢票噢~            ");
            sendInfo("|                               一定要快抢票噢~ ");
            sendInfo("|                                            ");
            sendInfo("---------------------------------------------"+randomSpilt);
            sendInfo("累计发现余票次数："+BasicInfo.hasTicketCount);
        }
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
