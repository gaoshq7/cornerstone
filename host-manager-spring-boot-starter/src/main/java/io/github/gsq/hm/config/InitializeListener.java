package io.github.gsq.hm.config;

import io.github.gsq.hm.Constant;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.config.InitializeListener
 *
 * @author : gsq
 * @date : 2024-09-14 11:28
 * @note : It's not technology, it's art !
 **/
public class InitializeListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        boolean onMaster = Boolean.parseBoolean(context.getEnvironment().getProperty("cornerstone.hm.master.enabled"));
        boolean onSlave = Boolean.parseBoolean(context.getEnvironment().getProperty("cornerstone.hm.slave.enabled"));
        if (onMaster) {
            boolean masterIsDebug = Boolean.parseBoolean(context.getEnvironment().getProperty("cornerstone.hm.master.debug"));
            initLogSystem(Constant.MASTER, masterIsDebug ? "debug" : "info");
            mInitialize(context);
        }
        if (onSlave) {
            boolean slaveIsDebug = Boolean.parseBoolean(context.getEnvironment().getProperty("cornerstone.hm.slave.debug"));
            initLogSystem(Constant.SLAVE, slaveIsDebug ? "debug" : "info");
            sInitialize(context);
        }
    }

    private void initLogSystem(String role, String level) {
        LoggingSystem system = LoggingSystem.get(LoggingSystem.class.getClassLoader());
        system.setLogLevel("io.netty", LogLevel.ERROR);
        system.setLogLevel("io.github.gsq.hm." + role, LogLevel.valueOf(level.trim().toUpperCase()));
    }

    private void mInitialize(ApplicationContext context) {
        System.out.println("将要启动主进程 ...");
    }

    private void sInitialize(ApplicationContext context) {
        System.out.println("将要启动从进程 ...");
    }

}
