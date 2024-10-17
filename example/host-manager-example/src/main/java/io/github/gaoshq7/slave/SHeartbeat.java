package io.github.gaoshq7.slave;

import io.github.gsq.hm.slave.handler.hook.IHeartbeatProvider;
import org.springframework.stereotype.Component;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.slave.SHeartbeat
 *
 * @author : gsq
 * @date : 2024-10-17 17:02
 * @note : It's not technology, it's art !
 **/
@Component
public class SHeartbeat implements IHeartbeatProvider {

    @Override
    public String create() {
        return "你看这是啥" + System.currentTimeMillis();
    }

    @Override
    public void result(String data) {
        System.out.println("主的告诫" + data);
    }

}
