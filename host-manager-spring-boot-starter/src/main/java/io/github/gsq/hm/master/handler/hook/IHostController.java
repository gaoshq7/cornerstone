package io.github.gsq.hm.master.handler.hook;

import io.github.gsq.hm.master.handler.Host;

import java.util.List;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.hook.IHostController
 *
 * @author : gsq
 * @date : 2024-10-15 16:00
 * @note : It's not technology, it's art !
 **/
public interface IHostController {

    List<Host> load();

    Host create(String hostname, String data);

    void remove(String hostname);

}
