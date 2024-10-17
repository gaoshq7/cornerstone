package io.github.gaoshq7.master;

import cn.hutool.core.util.IdUtil;
import io.github.gsq.hm.master.handler.Host;
import lombok.Getter;
import lombok.Setter;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.master.CustomHost
 *
 * @author : gsq
 * @date : 2024-10-17 15:07
 * @note : It's not technology, it's art !
 **/
@Getter
@Setter
public class CustomHost extends Host {

    private String customInfo;

    public CustomHost(String hostname, String customInfo) {
        super(hostname);
        this.customInfo = customInfo;
    }

//    @Override
//    protected void update(String data) {
//        this.customInfo = data;
//    }

}
