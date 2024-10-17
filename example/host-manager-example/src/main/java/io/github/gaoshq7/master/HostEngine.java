package io.github.gaoshq7.master;

import cn.hutool.core.collection.CollUtil;
import io.github.gsq.hm.master.handler.Host;
import io.github.gsq.hm.master.handler.hook.IHostController;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.master.HostEngine
 *
 * @author : gsq
 * @date : 2024-10-17 14:40
 * @note : It's not technology, it's art !
 **/
@Component
public class HostEngine implements IHostController {

    @Override
    public List<Host> load() {
        return CollUtil.newArrayList(
                new CustomHost("Dream", "大西瓜"),
                new CustomHost("host09", "小番茄"),
                new CustomHost("host07", "小趴菜")
        );
    }

    @Override
    public Host create(String hostname, String data) {
        return new CustomHost(hostname, data);
    }

    @Override
    public void remove(String hostname) {
        System.out.println("假装删了：" + hostname);
    }

}
