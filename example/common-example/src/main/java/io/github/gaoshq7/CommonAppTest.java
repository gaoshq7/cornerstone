package io.github.gaoshq7;

import io.github.gsq.common.EnhanceApplicationBuilder;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
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
        // 创建一个 Polyglot 上下文，支持多语言
        try (Context context = Context.create()) {
            // 在 Python 中执行一个简单的脚本
            Value result = context.eval("python", "a = 5; b = 3; a + b");
            System.out.println("计算结果: " + result.asInt());
        }

        try (Context context = Context.create()) {
            context.eval("js", "console.log('去你妹的!')");
        }
    }

}
