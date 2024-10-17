package io.github.gaoshq7.slave;

import cn.hutool.core.util.IdUtil;
import io.github.gsq.hm.slave.handler.hook.ILoginProvider;
import org.springframework.stereotype.Component;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.slave.SLogin
 *
 * @author : gsq
 * @date : 2024-10-17 16:58
 * @note : It's not technology, it's art !
 **/
@Component
public class SLogin implements ILoginProvider {

    @Override
    public String create() {
        return "你看我行呗" + IdUtil.fastSimpleUUID();
    }

    @Override
    public void result(String data) {
        System.out.println("主说：" + data);
    }

}
