package com.victor.base.event;

/**
 * 描述：
 * -
 * 创建人：wangchunxiao
 * 创建时间：2018/07/16
 */
public class MessageType {

    public static final int EVENT_TYPE_TOKEN_INVALID = 0x2000;
    public static final int EVENT_TYPE_USER_MESSAGE = 0x2001;
    public static final int EVENT_TYPE_INBOUND_LIST_REFRESH = 0x2002;
    public static final int EVENT_TYPE_INBOUND_SCAN_ADD_ITEM = 0x2003;
    public static final int EVENT_TYPE_INBOUND_SCAN_REMOVE_ITEM = 0x2004;
    public static final int EVENT_TYPE_INBOUND_SCAN_UPDATE_ITEM = 0x2005;
    public static final int EVENT_TYPE_OUTBOUND_LIST_REFRESH = 0x2006;
    public static final int EVENT_TYPE_OUTBOUND_SCAN_ADD_ITEM = 0x2007;
    public static final int EVENT_TYPE_OUTBOUND_SCAN_REMOVE_ITEM = 0x2008;
    public static final int EVENT_TYPE_OUTBOUND_SCAN_UPDATE_ITEM = 0x2009;
}
