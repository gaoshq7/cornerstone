package io.github.gsq.hm.master;

import io.github.gsq.hm.master.handler.Host;

import java.util.List;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.HostManager
 *
 * @author : gsq
 * @date : 2024-09-09 17:07
 * @note : It's not technology, it's art !
 **/
public interface HostManager<T extends Host> {

    List<T> list();

    T get(String hostname);

    void close(String hostname);

    void remove(String hostname);

}
