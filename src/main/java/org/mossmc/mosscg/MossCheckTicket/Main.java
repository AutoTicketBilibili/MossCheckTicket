package org.mossmc.mosscg.MossCheckTicket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import static org.mossmc.mosscg.MossCheckTicket.Logger.sendException;
import static org.mossmc.mosscg.MossCheckTicket.Logger.sendInfo;

public class Main {
    public static void main(String[] args) {
        //既然你都看到这里了，那就浅说两句吧
        //倒卖我倒是无所谓，别改作者就行，版本号随便改
        sendInfo("欢迎使用MossCheckTicket抢票监听插件~");
        sendInfo("软件版本：" + BasicInfo.version);
        sendInfo("软件作者：" + BasicInfo.author);
        sendInfo("请输入你要监听的漫展ID：");
        BasicInfo.ID = readInput();
        run();
    }

    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public static void run(){
        sendInfo("正在初始化......");
        init();
        sendInfo("开始监听......");
        sendInfo("发现余票后会输出信息提示，请关注~");
        while (true) {
            try {
                ReadWebInfo.readData(GetWebInfo.getData(BasicInfo.infoTarget()));
            } catch (Exception e) {
                sendException(e);
            } finally {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    sendException(e);
                }
            }
        }
    }

    public static void init() {
        System.setProperty("sun.net.client.defaultConnectTimeout", "3000");
        System.setProperty("sun.net.client.defaultReadTimeout", "3000");
        System.setProperty("http.keepAlive", "false");
    }

    public static String readInput() {
        BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(System.in, Charset.defaultCharset()));
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            sendException(e);
            return "null";
        }
    }
}
