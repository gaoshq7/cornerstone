package io.github.gaoshq7.test;

import cn.hutool.extra.spring.SpringUtil;
import io.github.gsq.hm.master.HmServer;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.test.MasterAppTest
 *
 * @author : gsq
 * @date : 2024-09-11 17:53
 * @note : It's not technology, it's art !
 **/
@SpringBootApplication
@ComponentScan(basePackages = "io.github.gaoshq7.test.master")
public class MasterAppTest implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MasterAppTest.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws InterruptedException {
        HmServer server = SpringUtil.getBean(HmServer.class);
        server.start();
        System.out.println("心跳服务已启动 ...");
    }

}
