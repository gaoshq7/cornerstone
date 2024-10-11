package io.github.gsq.hm.master.handler;

import cn.hutool.core.util.StrUtil;
import io.github.gsq.hm.Constant;
import io.github.gsq.hm.common.MsgUtil;
import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.common.protobuf.Message;
import io.github.gsq.hm.master.handler.hook.IHeartbeatReceiver;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.MHeartbeatHandler
 *
 * @author : gsq
 * @date : 2024-09-04 16:53
 * @note : It's not technology, it's art !
 **/
public class MHeartbeatHandler extends MAbstractHandler {

    private final ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 1);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            String clientId = getClientId(ctx);
            if (counter.get() >= Constant.MAX_LOSE_TIME) {
                ctx.channel().close();
                getMsgReceiver().loseLink(clientId);
                warn(clientId + "主机心跳包丢失三次，已断开链接。");
            } else {
                note(clientId);
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        Message.BaseMsg msg = (Message.BaseMsg) data;
        Channel channel = ctx.channel();
        if (msg.getType() == Command.CommandType.PING) {
            reset();
            IHeartbeatReceiver receiver = getHeartbeatReceiver();
            String result = receiver.handle(msg.getClientId(), msg.getData());
            debug(StrUtil.format("收到来自{}主机的心跳信息：{}", getClientId(ctx), msg.getData()));
            ctx.channel().writeAndFlush(
                    MsgUtil.createMsg(getClientId(ctx), Command.CommandType.PONG, result)
            );
            debug(StrUtil.format("向{}主机发送心跳回执信息：{}", getClientId(ctx), result));
        } else {
            if (channel.isOpen()) {
                ctx.fireChannelRead(msg);
            }
        }
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        String clientId = getClientId(ctx);
        super.logger.error("与" + clientId + "主机信道发生异常：", cause);
        ctx.channel().close();
        debug(StrUtil.format("与{}主机之间的信道已关闭。", clientId));
    }

    private void reset() {
        this.counter.set(1);
    }

    private void note(String clientId) {
        switch (this.counter.get()) {
            case 1 :
                debug(clientId + "主机心跳包丢失一次...");
                super.getMsgReceiver().loseOnce(clientId);
                break;
            case 2 :
                warn(clientId + "主机心跳包丢失二次...");
                super.getMsgReceiver().loseTwice(clientId);
                break;
        }
        this.counter.set(this.counter.get() + 1);
    }

}
