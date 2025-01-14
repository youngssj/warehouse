package me.goldze.mvvmhabit.base;

/**
 * 描述：
 * -
 * 创建人：wangchunxiao
 * 创建时间：2019/03/18
 */
public class AppStatusManager {

    private static AppStatusManager mInstance = null;

    private int appStatus = AppStatusConstant.APP_FORCE_KILLED;

    private AppStatusManager() {

    }

    public static AppStatusManager getInstance() {
        if (mInstance == null) {
            synchronized (AppStatusManager.class) {
                if (mInstance == null)
                    mInstance = new AppStatusManager();
            }
        }
        return mInstance;
    }

    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }

    public int getAppStatus() {
        return appStatus;
    }


    public static class AppStatusConstant {

        public static final int APP_FORCE_KILLED = -1;//应用放在后台被强杀了
        public static final int APP_NORMAL = 2; //APP正常态//intent到MainActivity区分跳转目的
        public static final String KEY_HOME_ACTION = "key_home_action";//返回到主页面
        public static final int ACTION_BACK_TO_HOME = 0;//默认值
        public static final int ACTION_RESTART_APP = 1;//被强杀
    }
}