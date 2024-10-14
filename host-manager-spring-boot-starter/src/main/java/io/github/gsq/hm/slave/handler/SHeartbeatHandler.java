package io.github.gsq.hm.slave.handler;

import io.github.gsq.hm.Constant;
import io.github.gsq.hm.common.MsgUtil;
import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.common.protobuf.Message;
import io.github.gsq.hm.slave.handler.hook.IHeartbeatProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.slave.handler.SHeartbeatHandler
 *
 * @author : gsq
 * @date : 2024-09-04 17:00
 * @note : It's not technology, it's art !
 **/
public class SHeartbeatHandler extends SAbstractHandler {

    private int counter = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (counter < Constant.MAX_LOSE_TIME) {
                IHeartbeatProvider provider = getHeartbeatProvider();
                String heartbeat = provider.create();
                ctx.writeAndFlush(MsgUtil.createMsg(Constant.HOSTNAME, Command.CommandType.PING, heartbeat));
                note();
                debug("发送心跳包：" + heartbeat);
            } else {
                ctx.channel().close();
                getMsgReceiver().loseLink();
                warn("心跳包丢失三次，已断开链接。");
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message.BaseMsg msg) {
        if (msg.getType() == Command.CommandType.PONG) {
            reset();
            IHeartbeatProvider provider = getHeartbeatProvider();
            provider.result(msg.getData());
            debug("收到心跳响应：" + msg.getData());
        } else {
            if(ctx.channel().isOpen()) {
                ctx.fireChannelRead(msg);
            }
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        super.logger.error("主机信道发生异常：", cause);
        getClient().stop();
        debug("心跳客户端已关闭。");
    }

    private void reset() {
        this.counter = 0;
    }

    private void note() {
        switch (this.counter) {
            case 1 :
                debug("心跳包丢失一次...");
                super.getMsgReceiver().loseOnce();
                break;
            case 2 :
                debug("心跳包丢失二次...");
                super.getMsgReceiver().loseTwice();
                break;
        }
        this.counter++;
    }

}
