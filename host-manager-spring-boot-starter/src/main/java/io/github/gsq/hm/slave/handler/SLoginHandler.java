package io.github.gsq.hm.slave.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import io.github.gsq.hm.Constant;
import io.github.gsq.hm.common.MsgUtil;
import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.common.protobuf.Message;
import io.github.gsq.hm.slave.handler.hook.ILoginProvider;
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
            ILoginProvider provider = getLoginProvider();
            boolean flag = provider.result(msg.getData());
            if (flag) {
                debug(StrUtil.format("{}主机登录成功。", Constant.HOSTNAME));
            } else {
                warn(StrUtil.format("{}主机登录失败。", Constant.HOSTNAME));
            }
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        warn("线路断开了");
    }

}
