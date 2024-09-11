package io.github.gaoshq7.test;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.test.MasterAppTest
 *
 * @author : gsq
 * @date : 2024-09-11 17:53
 * @note : It's not technology, it's art !
 **/
@SpringBootApplication
public class MasterAppTest implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(MasterAppTest.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}
