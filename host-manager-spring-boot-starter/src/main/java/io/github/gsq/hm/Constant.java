package io.github.gsq.hm;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.Constant
 *
 * @author : gsq
 * @date : 2024-09-03 17:16
 * @note : It's not technology, it's art !
 **/
@Slf4j
public final class Constant {

    public static final String HOSTNAME = HostnameUtil.getHostname();

    public static final String HOSTIP = HostnameUtil.getHostIp();

    public static final String MASTER = "master";

    public static final String SLAVE = "slave";

    public static final class HostnameUtil {

        public static String getHostname() {
            String hostname = null;
            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                log.error("Get Hostname Error", e);
            }
            return hostname;
        }

        public static String getHostIp() {
            String ip = null;
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error("Get Host IP Error", e);
            }
            return ip;
        }

    }

}
