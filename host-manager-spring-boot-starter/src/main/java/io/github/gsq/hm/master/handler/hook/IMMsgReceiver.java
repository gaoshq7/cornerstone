package io.github.gsq.hm.master.handler.hook;

import io.github.gsq.hm.common.Event;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.hook.IMMsgReceiver
 *
 * @author : gsq
 * @date : 2024-09-29 15:49
 * @note : It's not technology, it's art !
 **/
public interface IMMsgReceiver {

    void loseOnce(String clientId);

    void loseTwice(String clientId);

    void online(String clientId);

    void offline(String clientId, Event event);

}
