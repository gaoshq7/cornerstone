package io.github.gsq.hm.master;

import io.github.gsq.hm.common.Event;
import lombok.Getter;
import lombok.Setter;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.master.ChannelContext
 *
 * @author : gsq
 * @date : 2024-10-14 17:13
 * @note : It's not technology, it's art !
 **/
@Getter
public class ChannelContext {

    private final String clientId;

    @Setter
    private boolean auth;

    @Setter
    private Event offlineEvent;

    public ChannelContext(String clientId) {
        this.clientId = clientId;
    }

    public Event getOfflineEvent() {
        return this.offlineEvent == null ? Event.SLAVE_SHUTDOWN : this.offlineEvent;
    }

}
