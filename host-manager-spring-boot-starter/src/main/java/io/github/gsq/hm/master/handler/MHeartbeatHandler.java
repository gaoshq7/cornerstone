package io.github.gsq.hm.master.handler;

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

    private ThreadLocal<Integer> counter = ThreadLocal.withInitial(() -> 0);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            String channelId = getClientId(ctx);
            if (counter.get() >= Constant.MAX_LOSE_TIME) {
                ctx.channel().close();
                counter.set(0);
            } else {
                counter.set(counter.get() + 1);
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
            IHeartbeatReceiver receiver = getHeartbeatReceiver();
            String result = receiver.handle(msg.getClientId(), msg.getData());
            ctx.channel().writeAndFlush(
                    MsgUtil.createMsg(getClientId(ctx), Command.CommandType.PONG, result)
            );
        } else {
            if (channel.isOpen()) {
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
