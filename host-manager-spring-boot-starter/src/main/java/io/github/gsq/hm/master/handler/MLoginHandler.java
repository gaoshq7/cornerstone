package io.github.gsq.hm.master.handler;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import io.github.gsq.hm.common.Event;
import io.github.gsq.hm.common.MsgUtil;
import io.github.gsq.hm.common.models.LoginDTO;
import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.common.protobuf.Message;
import io.github.gsq.hm.master.handler.hook.ILoginReceiver;
import io.netty.channel.ChannelHandlerContext;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.MLoginHandler
 *
 * @author : gsq
 * @date : 2024-09-04 16:48
 * @note : It's not technology, it's art !
 **/
public class MLoginHandler extends MAbstractHandler {

    private final ThreadLocal<Boolean> auth = ThreadLocal.withInitial(() -> true);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object data) {
        Message.BaseMsg msg = (Message.BaseMsg) data;
        if (msg.getType() == Command.CommandType.AUTH) {
            auth.set(true);
            debug(StrUtil.format("{}主机提交认证信息：{}", msg.getClientId(), msg.getData()));
            setClientId(ctx, msg.getClientId());
            ILoginReceiver receiver = getLoginReceiver();
            LoginDTO loginDTO = receiver.auth(msg.getClientId(), msg.getData());
            if (loginDTO.isAuth()) {
                getMsgReceiver().online(msg.getClientId());
            }
            debug(StrUtil.format("{}主机认证结果：{}", msg.getClientId(), loginDTO));
            ctx.channel().writeAndFlush(
                    MsgUtil.createMsg(msg.getClientId(), Command.CommandType.AUTH_BACK, loginDTO.toString())
            );
            if (!loginDTO.isAuth()) {
                auth.set(false);
                debug(StrUtil.format("{}主机认证失败，5秒后将断开链接...", msg.getClientId()));
                ThreadUtil.safeSleep(5000);
                ctx.channel().close();
                debug(StrUtil.format("与{}主机的链接已断开。", msg.getClientId()));
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if (auth.get()) {
            Event event = getOfflineEvent(ctx);
            getMsgReceiver().offline(getClientId(ctx), event);
            warn(StrUtil.format("与{}主机的链接断开（{}）。", getClientId(ctx), event.getContent()));
        } else {
            warn(getClientId(ctx) + "主机登录认证失败。");
        }
    }

}
