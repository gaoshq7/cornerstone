package io.github.gsq.hm.slave.handler;

import cn.hutool.extra.spring.SpringUtil;
import io.github.gsq.hm.common.EnvUtil;
import io.github.gsq.hm.common.protobuf.Message;
import io.github.gsq.hm.slave.HmClient;
import io.github.gsq.hm.slave.handler.hook.IHeartbeatProvider;
import io.github.gsq.hm.slave.handler.hook.ILoginProvider;
import io.github.gsq.hm.slave.handler.hook.ISMsgReceiver;
import io.netty.channel.ChannelHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogLevel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

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

    private ILoginProvider loginProvider;

    private IHeartbeatProvider heartbeatProvider;

    private ISMsgReceiver msgReceiver;

    private HmClient client;

    protected final InternalLogger logger;

    protected SAbstractHandler() {
        this.logger = InternalLoggerFactory.getInstance(this.getClass());
    }

    protected final void debug(String msg) {
        if (this.logger.isDebugEnabled()) {
            this.logger.log(InternalLogLevel.DEBUG, msg);
        }
    }

    protected final void info(String msg) {
        if (this.logger.isInfoEnabled()) {
            this.logger.log(InternalLogLevel.INFO, msg);
        }
    }

    protected final void warn(String msg) {
        if (this.logger.isWarnEnabled()) {
            this.logger.log(InternalLogLevel.WARN, msg);
        }
    }

    protected final void error(String msg) {
        if (this.logger.isErrorEnabled()) {
            this.logger.log(InternalLogLevel.ERROR, msg);
        }
    }

    protected final ILoginProvider getLoginProvider() {
        if (this.loginProvider == null) {
            ILoginProvider provider = EnvUtil.getBean(ILoginProvider.class);
            this.loginProvider = provider != null ? provider :
                    new ILoginProvider() {

                        @Override
                        public String create() {
                            return "";
                        }

                        @Override
                        public void result(String data) {

                        }

                    };
        }
        return this.loginProvider;
    }

    protected final IHeartbeatProvider getHeartbeatProvider() {
        if (this.heartbeatProvider == null) {
            IHeartbeatProvider provider = EnvUtil.getBean(IHeartbeatProvider.class);
            this.heartbeatProvider = provider != null ? provider :
                    new IHeartbeatProvider() {

                        @Override
                        public String create() {
                            return "";
                        }

                        @Override
                        public void result(String data) {

                        }

                    };
        }
        return this.heartbeatProvider;
    }

    protected final ISMsgReceiver getMsgReceiver() {
        if (this.msgReceiver == null) {
            ISMsgReceiver receiver = EnvUtil.getBean(ISMsgReceiver.class);
            this.msgReceiver = receiver != null ? receiver :
                    new ISMsgReceiver() {

                        @Override
                        public void loseOnce() {

                        }

                        @Override
                        public void loseTwice() {

                        }

                        @Override
                        public void loseLink() {

                        }

                    };
        }
        return this.msgReceiver;
    }

    protected final HmClient getClient() {
        if (this.client == null) {
            this.client = SpringUtil.getBean(HmClient.class);
        }
        return this.client;
    }

}
