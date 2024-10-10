package io.github.gsq.hm.master.handler;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
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
            debug(StrUtil.format("{}主机开始身份认证...", msg.getClientId()));
            ILoginReceiver receiver = getLoginReceiver();
            LoginDTO loginDTO = receiver.auth(msg.getClientId(), msg.getData());
            if (loginDTO.isAuth()) {
                setClientId(ctx, msg.getClientId());
            }
            debug(StrUtil.format("{}主机认证结果：{}", msg.getClientId(), loginDTO));
            ctx.channel().writeAndFlush(
                    MsgUtil.createMsg(getClientId(ctx), Command.CommandType.AUTH_BACK, loginDTO.toString())
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
        String reason;
        if (auth.get()) {
            reason = "主机失联";
        } else {
            reason = "主机身份认证失败";
        }
        warn(StrUtil.format("与{}主机的链接断开（{}）。", getClientId(ctx), reason));
    }

}
