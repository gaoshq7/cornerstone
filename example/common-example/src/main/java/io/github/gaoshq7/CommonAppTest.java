package io.github.gaoshq7;

import io.github.gsq.common.EnhanceApplicationBuilder;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.CommonAppTest
 *
 * @author : gsq
 * @date : 2024-10-29 17:35
 * @note : It's not technology, it's art !
 **/
@SpringBootApplication
public class CommonAppTest implements ApplicationRunner {

    public static void main(String[] args) {
        EnhanceApplicationBuilder builder = new EnhanceApplicationBuilder(CommonAppTest.class);
        builder.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

}
