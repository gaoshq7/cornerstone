package io.github.gsq.hm.common;

import com.google.protobuf.Timestamp;
import io.github.gsq.hm.common.protobuf.Command;
import io.github.gsq.hm.common.protobuf.Message;

/**
 * Project : cornerstone
 * Class : io.github.gsq.hm.common.MsgUtil
 *
 * @author : gsq
 * @date : 2024-09-10 10:40
 * @note : It's not technology, it's art !
 **/
public final class MsgUtil {

    public static Message.BaseMsg createMsg(String clientId, Command.CommandType type, String data) {
        Message.BaseMsg.Builder builder = Message.BaseMsg.newBuilder();
        return builder
                .setClientId(clientId)
                .setType(type)
                .setData(data)
                .setTime(Timestamp.newBuilder())
                .build();
    }

}
