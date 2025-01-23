package com.victor.base.router;

/**
 * 用于组件开发中，ARouter多Fragment跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 * Created by goldze on 2018/6/21
 */

public class RouterFragmentPath {
    /**
     * 首页组件
     */
    public static class Home {
        private static final String HOME = "/home";
        /*首页*/
        public static final String PAGER_HOME = HOME + "/Home";
    }

    /**
     * 工作组件
     */
    public static class WorkBench {
        private static final String WorkBench = "/WorkBench";
        /*工作*/
        public static final String PAGER_WorkBench = WorkBench + "/WorkBench";
    }

    /**
     * 用户组件
     */
    public static class User {
        private static final String USER = "/user";
        /*我的*/
        public static final String PAGER_MINE = USER + "/Mine";
    }

    /**
     * 用户组件
     */
    public static class Sync {
        private static final String SYNC = "/sync";
        /*我的*/
        public static final String PAGER_SYNC = SYNC + "/Sync";
    }

    public static class Materials {
        private static final String Materials = "/Materials";
        /*工作*/
        public static final String PAGER_MATERIALS_LIST = Materials + "/Materials_List";
    }

    public static class Inbound {
        private static final String Inbound = "/Inbound";
        /*工作*/
        public static final String PAGER_INBOUND_SCAN = Inbound + "/Inbound_scan";
    }

    public static class Outbound {
        private static final String Outbound = "/Outbound";
        /*工作*/
        public static final String PAGER_OUTBOUND_SCAN = Outbound + "/Outbound_scan";
    }
}
