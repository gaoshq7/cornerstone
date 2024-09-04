package io.github.gsq.hm.slave.handler;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.slave.handler.SHeartbeatHandler
 *
 * @author : gsq
 * @date : 2024-09-04 17:00
 * @note : It's not technology, it's art !
 **/
@Slf4j
@Order(1)
public class SHeartbeatHandler extends SAbstractHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

    }

}
