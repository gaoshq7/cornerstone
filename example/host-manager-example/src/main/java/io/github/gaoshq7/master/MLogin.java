package io.github.gaoshq7.master;

import cn.hutool.core.util.IdUtil;
import io.github.gsq.hm.common.models.LoginDTO;
import io.github.gsq.hm.master.handler.hook.ILoginReceiver;
import org.springframework.stereotype.Component;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.master.MLogin
 *
 * @author : gsq
 * @date : 2024-10-17 16:49
 * @note : It's not technology, it's art !
 **/
@Component
public class MLogin implements ILoginReceiver {

    @Override
    public LoginDTO auth(String clientId, String data) {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setAuth(false);
        loginDTO.setData("来就来呗" + IdUtil.fastSimpleUUID());
        return loginDTO;
    }

}
