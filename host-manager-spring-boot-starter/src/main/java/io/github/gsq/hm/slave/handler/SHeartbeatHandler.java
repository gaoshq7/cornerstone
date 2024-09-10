package io.github.gsq.hm.slave.handler;

import cn.hutool.core.lang.UUID;
import io.github.gsq.hm.Constant;
import io.github.gsq.hm.common.MsgUtil;
import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.common.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.slave.handler.SHeartbeatHandler
 *
 * @author : gsq
 * @date : 2024-09-04 17:00
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class SHeartbeatHandler extends SAbstractHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            ctx.writeAndFlush(MsgUtil.createMsg(Constant.HOSTNAME, Command.CommandType.PING, UUID.fastUUID().toString()));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message.BaseMsg msg) {
        if (msg.getType() == Command.CommandType.PONG) {
            System.out.println("收到回执信息：" + msg.getData());
        } else {
            if(ctx.channel().isOpen()){
                ctx.fireChannelRead(msg);
            }
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.out.println("信道错误" + cause.getMessage());
    }

}
