package io.github.gsq.hm.master.handler;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.MHeartbeatHandler
 *
 * @author : gsq
 * @date : 2024-09-04 16:53
 * @note : It's not technology, it's art !
 **/
@Slf4j
@Order(1)
public class MHeartbeatHandler extends MAbstractHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

    }

}
