package io.github.gaoshq7.test;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.test.SlaveAppTest
 *
 * @author : gsq
 * @date : 2024-09-11 17:54
 * @note : It's not technology, it's art !
 **/
@SpringBootApplication
public class SlaveAppTest implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SlaveAppTest.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}
