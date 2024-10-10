package io.github.gsq.hm.slave.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import io.github.gsq.hm.Constant;
import io.github.gsq.hm.common.MsgUtil;
import io.github.gsq.hm.common.models.LoginDTO;
import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.common.protobuf.Message;
import io.github.gsq.hm.slave.HmClient;
import io.github.gsq.hm.slave.handler.hook.ILoginProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;

import java.util.concurrent.TimeUnit;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.slave.handler.SLoginHandler
 *
 * @author : gsq
 * @date : 2024-09-04 16:56
 * @note : It's not technology, it's art !
 **/
public class SLoginHandler extends SAbstractHandler {

    private final ThreadLocal<Boolean> identification = ThreadLocal.withInitial(() -> true);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        this.identification.set(true);
        ILoginProvider provider = getLoginProvider();
        String data = provider.create();
        debug(StrUtil.format("{}主机创建登录信息：{}", Constant.HOSTNAME, data));
        ctx.writeAndFlush(
                MsgUtil.createMsg(
                        Constant.HOSTNAME,
                        Command.CommandType.AUTH,
                        data
                )
        );
        debug("登录信息提交成功。");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message.BaseMsg msg) {
        if (msg.getType() == Command.CommandType.AUTH_BACK) {
            debug(StrUtil.format("{}主机收到登录结果：{}", Constant.HOSTNAME, msg.getData()));
            LoginDTO loginDTO = JSONUtil.toBean(msg.getData(), LoginDTO.class);
            ILoginProvider provider = getLoginProvider();
            provider.result(loginDTO.getData());
            if (loginDTO.isAuth()) {
                debug(StrUtil.format("{}主机登录成功。", Constant.HOSTNAME));
            } else {
                this.identification.set(false);
                warn(StrUtil.format("{}主机登录失败，将不会启动断线重连。", Constant.HOSTNAME));
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        debug(StrUtil.format("{}主机与master之间的链接断开。", Constant.HOSTNAME));
        super.channelInactive(ctx);
        if (this.identification.get()) {
            debug(StrUtil.format("{}主机5秒后启动断线重连机制。", Constant.HOSTNAME));
            HmClient client = getClient();
            final EventLoop eventLoop = ctx.channel().eventLoop();
            eventLoop.schedule(() -> client.reconnect(eventLoop), 5L, TimeUnit.SECONDS);
        }
    }

}
