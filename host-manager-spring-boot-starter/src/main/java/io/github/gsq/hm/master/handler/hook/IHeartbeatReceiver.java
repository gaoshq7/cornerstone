package io.github.gsq.hm.master.handler.hook;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.hook.IHeartbeatReceiver
 *
 * @author : gsq
 * @date : 2024-09-29 15:46
 * @note : It's not technology, it's art !
 **/
public interface IHeartbeatReceiver {

    String handle(String clientId, String data);

}
