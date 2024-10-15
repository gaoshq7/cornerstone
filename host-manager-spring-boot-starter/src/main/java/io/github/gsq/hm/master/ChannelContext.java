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

    private int counter;

    public ChannelContext(String clientId) {
        this.clientId = clientId;
        this.auth = true;
        this.counter = 1;
    }

    public void increment() {
        this.counter++;
    }

    public void reset() {
        this.counter = 1;
    }

    public Event getOfflineEvent() {
        return this.offlineEvent == null ? Event.SLAVE_SHUTDOWN : this.offlineEvent;
    }

}
