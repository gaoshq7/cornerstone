package io.github.gsq.hm.slave.handler;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.slave.handler.SLoginHandler
 *
 * @author : gsq
 * @date : 2024-09-04 16:56
 * @note : It's not technology, it's art !
 **/
@Slf4j
@Order
public class SLoginHandler extends SAbstractHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

    }

}
