package io.github.gaoshq7.master;

import cn.hutool.core.util.IdUtil;
import io.github.gsq.hm.master.handler.hook.IHeartbeatReceiver;
import org.springframework.stereotype.Component;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.master.MHeartbeat
 *
 * @author : gsq
 * @date : 2024-10-17 16:52
 * @note : It's not technology, it's art !
 **/
@Component
public class MHeartbeat implements IHeartbeatReceiver {

    @Override
    public String handle(String clientId, String data) {
        return "心跳回执" + IdUtil.fastSimpleUUID();
    }

}
