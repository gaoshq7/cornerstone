package io.github.gsq.hm.slave.handler;

import cn.hutool.core.lang.UUID;
import io.github.gsq.hm.Constant;
import io.github.gsq.hm.common.MsgUtil;
import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.common.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.slave.handler.SLoginHandler
 *
 * @author : gsq
 * @date : 2024-09-04 16:56
 * @note : It's not technology, it's art !
 **/
@Slf4j
public class SLoginHandler extends SAbstractHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(MsgUtil.createMsg(Constant.HOSTNAME, Command.CommandType.AUTH, UUID.fastUUID().toString(true)));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message.BaseMsg msg) {
        if (msg.getType() == Command.CommandType.AUTH_BACK) {
            System.out.println("连接成功");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("线路断开");
    }

}
