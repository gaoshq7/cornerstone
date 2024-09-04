package io.github.gsq.hm.master.handler;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.MLoginHandler
 *
 * @author : gsq
 * @date : 2024-09-04 16:48
 * @note : It's not technology, it's art !
 **/
@Slf4j
@Order
public class MLoginHandler extends MAbstractHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

    }

}
