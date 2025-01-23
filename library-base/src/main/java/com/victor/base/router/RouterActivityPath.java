package com.victor.base.router;

/**
 * 用于组件开发中，ARouter单Activity跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 * Created by goldze on 2018/6/21
 */

public class RouterActivityPath {
    /**
     * 主业务组件
     */
    public static class Main {
        private static final String MAIN = "/main";
        /*主业务界面*/
        public static final String PAGER_ONLINE_MAIN = MAIN +"/OnlineMain";
        public static final String PAGER_OFFLINE_MAIN = MAIN +"/OfflineMain";
    }

    /**
     * 身份验证组件
     */
    public static class Sign {
        private static final String SIGN = "/sign";
        /*登录界面*/
        public static final String PAGER_LOGIN = SIGN + "/Login";
    }

    /**
     * 用户组件
     */
    public static class User {
        private static final String USER = "/user";
        /*用户详情*/
        public static final String PAGER_USERDETAIL = USER + "/UserDetail";
    }

    public static class WorkBench {
        private static final String WorkBench = "/WorkBench";
        /*工作*/
        public static final String PAGER_WorkBench_CHECK_LIST = WorkBench + "/WorkBenchCheckList";
        public static final String PAGER_WorkBench_CHECK = WorkBench + "/WorkBenchCheck";
    }

    public static class Inventory {
        private static final String Inventory = "/Inventory";
        /*工作*/
        public static final String PAGER_INVENTORY_CHECK_LIST = Inventory + "/InventoryCheckList";
        public static final String PAGER_INVENTORY_CHECK = Inventory + "/InventoryCheck";
    }

    public static class Materials {
        private static final String Materials = "/Materials";
        /*工作*/
        public static final String PAGER_MATERIALS_QUERY = Materials + "/Materials_Query";
    }

    public static class Inbound {
        private static final String Inbound = "/Inbound";
        /*工作*/
        public static final String PAGER_INBOUND_LIST = Inbound + "/Inbound_List";
        public static final String PAGER_INBOUND_SCAN = Inbound + "/Inbound_Scan";
    }

    public static class Outbound {
        private static final String Outbound = "/Outbound";
        /*工作*/
        public static final String PAGER_OUTBOUND_LIST = Outbound + "/Outbound_List";
        public static final String PAGER_OUTBOUND_SCAN = Outbound + "/Outbound_Scan";
    }

    public static class Movement {
        private static final String Movement = "/Movement";
        /*工作*/
        public static final String PAGER_MOVEMENT_LIST = Movement + "/Movement_List";
        public static final String PAGER_MOVEMENT_SCAN = Movement + "/Movement_Scan";
    }
}
