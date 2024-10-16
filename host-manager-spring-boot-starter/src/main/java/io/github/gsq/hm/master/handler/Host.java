package io.github.gsq.hm.master.handler;

import cn.hutool.core.util.StrUtil;
import io.github.gsq.hm.common.MsgUtil;
import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.master.ChannelContext;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.AbstractHost
 *
 * @author : gsq
 * @date : 2024-10-15 14:33
 * @note : It's not technology, it's art !
 **/
public class Host {

    @Getter
    private final String hostname;

    private ChannelHandlerContext ctx;

    protected Host(String hostname) {
        this.hostname = hostname;
    }

    protected ChannelHandlerContext getCtx() {
        return this.ctx;
    }

    protected final void setCtx(ChannelHandlerContext ctx) {
        if (ctx == null) throw new RuntimeException("信道处理器不能为null");
        final AttributeKey<ChannelContext> context = AttributeKey.valueOf("context");
        String clientId = ctx.channel().attr(context).get().getClientId();
        if (!clientId.equals(this.hostname)) {
            throw new RuntimeException("主机名与客户端id不匹配");
        }
        this.ctx = ctx;
    }

    protected void close() {
        if (this.ctx == null || !isConnected()) {
            throw new RuntimeException(StrUtil.format("{}主机失联无法传达退役命令", this.hostname));
        }
        ChannelFuture future = this.getCtx().channel().writeAndFlush(
                MsgUtil.createMsg(hostname, Command.CommandType.RESIGN, "")
        );
        try {
            future.get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isConnected() {
        return this.ctx != null && ctx.channel().isActive();
    }

}