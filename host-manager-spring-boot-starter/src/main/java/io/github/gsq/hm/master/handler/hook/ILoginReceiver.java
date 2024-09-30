package io.github.gsq.hm.master.handler.hook;

import io.github.gsq.hm.common.models.LoginDTO;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.hook.ILoginReceiver
 *
 * @author : gsq
 * @date : 2024-09-29 15:47
 * @note : It's not technology, it's art !
 **/
public interface ILoginReceiver {

    LoginDTO auth(String clientId, String data);

}
