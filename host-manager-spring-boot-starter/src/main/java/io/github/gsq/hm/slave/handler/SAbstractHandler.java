package io.github.gsq.hm.slave.handler;

import io.github.gsq.hm.common.protobuf.Message;
import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.slave.handler.SAbstractHandler
 *
 * @author : gsq
 * @date : 2024-09-03 17:09
 * @note : It's not technology, it's art !
 **/
@ChannelHandler.Sharable
public abstract class SAbstractHandler extends SimpleChannelInboundHandler<Message.BaseMsg> {



}
