package io.github.gsq.hm.master.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import io.github.gsq.hm.common.EnvUtil;
import io.github.gsq.hm.master.HostManager;
import io.github.gsq.hm.master.handler.hook.IHostController;
import io.netty.channel.ChannelHandlerContext;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.handler.HostManagerImpl
 *
 * @author : gsq
 * @date : 2024-09-09 17:08
 * @note : It's not technology, it's art !
 **/
public class HostManagerImpl<T extends Host> implements HostManager<T> {

    private final List<T> hosts = new CopyOnWriteArrayList<>();

    private IHostController controller;

    public void load() {
        List<T> hosts = (List<T>) getHostController().load();
        List<String> hostnames = CollUtil.map(hosts, Host::getHostname, true);
        int size = CollUtil.distinct(hostnames).size();
        if (size != hostnames.size()) {
            throw new RuntimeException("主机集合中有重复元素，加载失败！");
        }
        this.hosts.addAll(hosts);
    }

    @Override
    public List<T> list() {
        this.sort();
        return ListUtil.unmodifiable(this.hosts);
    }

    @Override
    public T get(String hostname) {
        return CollUtil.findOne(this.hosts, host -> host.getHostname().equals(hostname));
    }

    @Override
    public void close(String hostname) {
        Host host = get(hostname);
        if (host == null) {
            throw new RuntimeException(StrUtil.format("{}主机不存在", hostname));
        } else {
            if (host.isConnected()) {
                host.close();
            }
        }
    }

    @Override
    public void remove(String hostname) {
        Host host = get(hostname);
        if (host != null) {
            if (host.isConnected()) {
                throw new RuntimeException(StrUtil.format("{}主机处于连接状态不可删除", hostname));
            }
            delete(hostname);
        }
    }

    protected void delete(String hostname) {
        getHostController().remove(hostname);
        this.hosts.remove(get(hostname));
    }

    protected boolean join(String hostname, String data, ChannelHandlerContext ctx) {
        boolean flag = true;
        try {
            Host one = CollUtil.findOne(this.hosts, host -> host.getHostname().equals(hostname));
            if (one == null) {
                Host host = this.getHostController().create(hostname, data);
                host.setCtx(ctx);
                this.hosts.add((T) host);
            } else {
                if (one.isConnected()) {
                    throw new RuntimeException(StrUtil.format("{}主机已存在不能重复添加", hostname));
                }
                one.setCtx(ctx);
                one.update(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    private IHostController getHostController() {
        if (this.controller == null) {
            IHostController controller = EnvUtil.getBean(IHostController.class);
            this.controller = controller != null ? controller : new IHostController() {

                @Override
                public List<Host> load() {
                    return List.of();
                }

                @Override
                public Host create(String hostname, String data) {
                    return new Host(hostname);
                }

                @Override
                public void remove(String hostname) {

                }

            };
        }
        return this.controller;
    }

    private void sort() {
        this.hosts.sort(Comparator.comparing(Host::getHostname));
    }

}
