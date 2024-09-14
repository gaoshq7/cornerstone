package io.github.gsq.hm.master.handler;

import io.github.gsq.hm.common.MsgUtil;
import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.common.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.MLoginHandler
 *
 * @author : gsq
 * @date : 2024-09-04 16:48
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class MLoginHandler extends MAbstractHandler {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        Message.BaseMsg msg = (Message.BaseMsg) data;
        if (msg.getType() == Command.CommandType.AUTH) {
            info(msg.getClientId() + "前来注册: " + msg.getData());
        }
        ctx.channel().writeAndFlush(MsgUtil.createMsg("", Command.CommandType.AUTH_BACK, "你小子可以"));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        warn(getClientId(ctx) + "失去连接");
    }

}
