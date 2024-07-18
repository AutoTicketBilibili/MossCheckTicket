package org.mossmc.mosscg.MossCheckTicket;

import org.mossmc.mosscg.MossCheckTicket.Web.WebBasic;
import org.mossmc.mosscg.MossCheckTicket.Web.WebData;
import org.mossmc.mosscg.MossCheckTicket.Web.WebReader;
import org.mossmc.mosscg.MossLib.Config.ConfigManager;
import org.mossmc.mosscg.MossLib.File.FileCheck;
import org.mossmc.mosscg.MossLib.Object.ObjectLogger;

public class Main {
    public static void main(String[] args) {
        FileCheck.checkDirExist("./MossCheckTicket");
        ObjectLogger logger = new ObjectLogger(null);
        BasicInfo.logger = logger;

        //基础信息输出
        logger.sendInfo("欢迎使用MossCheckTicket软件");
        logger.sendInfo("软件版本：" + BasicInfo.version);
        logger.sendInfo("软件作者：" + BasicInfo.author);

        //配置读取
        logger.sendInfo("正在读取配置文件......");
        BasicInfo.config = ConfigManager.getConfigObject("./MossCheckTicket", "config.yml", "config.yml");
        if (!BasicInfo.config.getBoolean("enable")) {
            logger.sendInfo("你还没有完成配置文件的设置哦~");
            logger.sendInfo("快去配置一下吧~");
            logger.sendInfo("配置文件位置：./MossCheckTicket/config.yml");
            System.exit(0);
        }
        BasicInfo.debug = BasicInfo.config.getBoolean("debug");

        //初始化
        logger.sendInfo("正在初始化......");
        WebBasic.init();
        logger.sendInfo("开始监听......");
        logger.sendInfo("发现余票后会输出信息提示，请关注~");
        run();
    }

    @SuppressWarnings({"BusyWait", "InfiniteLoopStatement"})
    public static void run(){
        while (true) {
            try {
                String data = WebData.getData(BasicInfo.infoTarget());
                WebReader.readData(data);
            } catch (Exception e) {
                BasicInfo.logger.sendException(e);
            } finally {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    BasicInfo.logger.sendException(e);
                }
            }
        }
    }
}
