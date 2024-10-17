package io.github.gaoshq7.master;

import io.github.gsq.hm.common.Event;
import io.github.gsq.hm.master.handler.hook.IMMsgReceiver;
import org.springframework.stereotype.Component;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.master.MMsg
 *
 * @author : gsq
 * @date : 2024-10-17 16:55
 * @note : It's not technology, it's art !
 **/
@Component
public class MMsg implements IMMsgReceiver {

    @Override
    public void loseOnce(String clientId) {
        System.out.println(clientId + " 失联一次");
    }

    @Override
    public void loseTwice(String clientId) {
        System.out.println(clientId + " 失联二次");
    }

    @Override
    public void online(String clientId) {
        System.out.println(clientId + " 上线了");
    }

    @Override
    public void offline(String clientId, Event event) {
        System.out.println(clientId + event.getContent());
    }

}
