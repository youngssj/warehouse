package com.victor.base.event;

/**
 * 描述：
 * -
 * 创建人：wangchunxiao
 * 创建时间：2018/07/16
 */
public class MessageEvent<T> extends Event {
    private final int messageType;
    private T t;

    public MessageEvent(int messageType, T t) {
        this.messageType = messageType;
        this.t = t;
    }

    public int getMessageType() {
        return messageType;
    }

    public T getData() {
        return t;
    }
}
