package io.github.gaoshq7.master;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Pair;
import io.github.gsq.hm.master.HostManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.master.TestController
 *
 * @author : gsq
 * @date : 2024-10-17 14:19
 * @note : It's not technology, it's art !
 **/
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    private HostManager<CustomHost> manager;

    @GetMapping("/list")
    public List<Pair<String, String>> getHostnames() {
        return CollUtil.map(
                manager.list(),
                host -> new Pair<>(host.getHostname(), host.isConnected() ? "链接中" : "已断开"),
                true
        );
    }

    @GetMapping("/g/{hostname}")
    public CustomHost getHost(@PathVariable("hostname") String hostname) {
        return manager.get(hostname);
    }

    @GetMapping("/c/{hostname}")
    public String close(@PathVariable("hostname") String hostname) {
        manager.close(hostname);
        return "success";
    }

    @GetMapping("/r/{hostname}")
    public String remove(@PathVariable("hostname") String hostname) {
        manager.remove(hostname);
        return "success";
    }

}
