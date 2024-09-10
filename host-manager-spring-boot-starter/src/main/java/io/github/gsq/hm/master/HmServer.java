package io.github.gsq.hm.master;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.HmServer
 *
 * @author : gsq
 * @date : 2024-09-03 17:12
 * @note : It's not technology, it's art !
 **/
public class HmServer {

    private final ServerBootstrap bootstrap;

    private final InetSocketAddress address;

    private Channel channel;

    public HmServer(ServerBootstrap bootstrap, InetSocketAddress address) {
        this.bootstrap = bootstrap;
        this.address = address;
    }

    public void start() throws InterruptedException {
        this.channel = this.bootstrap
                .bind(address)
                .sync()
                .channel()
                .closeFuture()
                .sync()
                .channel();
    }

    public void stop() {
        if (this.channel != null) {
            this.channel.close();
            this.channel.parent().close();
        }
    }

}
