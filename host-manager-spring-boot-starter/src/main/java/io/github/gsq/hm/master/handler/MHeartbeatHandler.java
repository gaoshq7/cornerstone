package io.github.gsq.hm.master.handler;

import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.common.protobuf.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogLevel;
import lombok.extern.slf4j.Slf4j;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.MHeartbeatHandler
 *
 * @author : gsq
 * @date : 2024-09-04 16:53
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class MHeartbeatHandler extends MAbstractHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            System.out.println(getClientId(ctx) + "链接emo了");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        Message.BaseMsg msg = (Message.BaseMsg) data;
        Channel channel = ctx.channel();
        if (msg.getType() == Command.CommandType.PING) {
            if (logger.isEnabled(InternalLogLevel.DEBUG)) {
                logger.log(InternalLogLevel.DEBUG, "收到" + msg.getClientId() + "的消息：" + msg.getData());
            }
        } else {
            if (channel.isOpen()) {
                //触发下一个handler
                ctx.fireChannelRead(msg);
            }
        }
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println(getClientId(ctx) + "链接发生错误：" + cause.getMessage());
    }

}
