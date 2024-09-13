package io.github.gsq.hm.master.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.MAbstractHandler
 *
 * @author : gsq
 * @date : 2024-09-03 17:08
 * @note : It's not technology, it's art !
 **/
@ChannelHandler.Sharable
public abstract class MAbstractHandler extends ChannelInboundHandlerAdapter {

    protected final AttributeKey<String> clientId = AttributeKey.valueOf("clientId");

    protected final String getClientId(ChannelHandlerContext ctx) {
        return ctx.channel().attr(clientId).get();
    }

}
