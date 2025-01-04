package com.victor.main.utils;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/8
 * 邮箱：jxfengmtx@gmail.com
 */
public class Constants {


    public static class CONFIG {
        public static boolean IS_INTRANET = false;
        public static boolean IS_OFFLINE = true;
//        public static String DEFAULT_IP = "120.224.45.119";  //默认已填写ip，登录设置可修改
//        public static String DEFAULT_PORT = "7103";

        public static String DEFAULT_IP = "192.168.1.127";  //默认已填写ip，登录设置可修改
        public static String DEFAULT_PORT = "9091";
    }

    public static class SP {
        public static String USERNAME = "user_name";
        public static String PASSWORD = "password";
        public static String TOKEN = "token";
        public static String IP = "ip";
        public static String PORT = "port";
        public static String IS_INTRANET = "is_intranet";
        public static String IS_OFFLINE = "is_offline";
    }

    public static class BUNDLE {
        public static String KEY = "0X000X";
        public static String KEY_0 = "0X000";
        public static String KEY_1 = "0X001";
        public static String KEY_2 = "0X002";
        public static String KEY_3 = "0X003";
        public static String ID = "0X00ID";
        public static String RESULLT = "0X00RS";
    }

    public static class DEVICE {
        public static final String C72 = "handheld";
        public static final String C725X = "HY-725X";
        public static final String C72_AND11 = "C72";
        public static final String C72_AND11_1 = "Handheld";
        public static final String SU_PDA = "PDA";
        public static final String K71V1_64_BSP = "k71v1_64_bsp";
    }

    // 巡检、维修等任务单状态   0 录入 1 进行中 2 超期 3 已完成
    public static class TaskStatus{
        public static final int CREATE = 0;
        public static final int DOING = 1;
        public static final int OVER = 2;
        public static final int FINISH = 3;
    }
    // 任务下单个资产的进行状态  1 已完成  2 未完成
    public static class TaskPartStatus{
        public static final int FINISH = 1;
        public static final int NO_FINISH = 2;
    }
}
