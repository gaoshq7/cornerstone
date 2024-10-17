package io.github.gaoshq7.slave;

import io.github.gsq.hm.slave.handler.hook.ISMsgReceiver;
import org.springframework.stereotype.Component;

/**
 * Project : cornerstone
 * Class : io.github.gaoshq7.slave.SMsg
 *
 * @author : gsq
 * @date : 2024-10-17 17:05
 * @note : It's not technology, it's art !
 **/
@Component
public class SMsg implements ISMsgReceiver {

    @Override
    public void loseOnce() {
        System.out.println("与主失联一次");
    }

    @Override
    public void loseTwice() {
        System.out.println("与主失联二次");
    }

    @Override
    public void loseLink() {
        System.out.println("与主断开链接");
    }

}
