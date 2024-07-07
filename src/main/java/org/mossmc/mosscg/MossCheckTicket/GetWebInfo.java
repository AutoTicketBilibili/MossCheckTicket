package org.mossmc.mosscg.MossCheckTicket;

import sun.net.www.protocol.http.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.mossmc.mosscg.MossCheckTicket.Logger.sendInfo;

public class GetWebInfo {
    public static String getData(String target) {
        int n = 10;
        for (int i=0;i<n;i++) {
            //sendInfo("正在尝试获取数据......第"+(i+1)+"次尝试......");
            try {
                String data = "";
                if (target.startsWith("https://")) data = readHttpsData(target);
                if (target.startsWith("http://")) data = readHttpData(target);
                //sendInfo("数据获取成功！");
                return data;
            } catch (Exception e) {
                sendInfo("第"+(i+1)+"次数据获取失败了......原因是："+e.getMessage());
            }
        }
        return null;
    }
    public static String readHttpsData(String target) throws Exception {
        URL targetURL = new URL(target);
        HttpsURLConnection connection = (HttpsURLConnection) targetURL.openConnection();
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("User-Agent", "MossCheckTicket/"+BasicInfo.version);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder readInfo = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            readInfo.append(inputLine);
        }
        reader.close();
        return readInfo.toString();
    }
    public static String readHttpData(String target) throws Exception {
        URL targetURL = new URL(target);
        HttpURLConnection connection = (HttpURLConnection) targetURL.openConnection();
        connection.setRequestProperty("Connection", "close");
        connection.setRequestProperty("User-Agent", "MossCheckTicket/"+BasicInfo.version);
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String inputLine;
        StringBuilder readInfo = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            readInfo.append(inputLine);
        }
        reader.close();
        return readInfo.toString();
    }
}
