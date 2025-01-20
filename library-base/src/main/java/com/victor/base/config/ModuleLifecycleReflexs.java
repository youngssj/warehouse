package com.victor.base.config;

/**
 * Created by goldze on 2018/6/21 0021.
 * 组件生命周期反射类名管理，在这里注册需要初始化的组件，通过反射动态调用各个组件的初始化方法
 * 注意：以下模块中初始化的Module类不能被混淆
 */

public class ModuleLifecycleReflexs {
    private static final String BaseInit = "com.victor.base.base.BaseModuleInit";
    //主业务模块
    private static final String MainInit = "com.victor.main.MainModuleInit";
    private static final String WorkBenchInit = "com.victor.workbench.WorkBenchModuleInit";
    private static final String InventoryInit = "com.victor.inventory.InventoryModuleInit";
    private static final String MaterialsInit = "com.victor.materials.MaterialsModuleInit";
    private static final String InboundInit = "com.victor.inbound.InboundModuleInit";
    private static final String MineInit = "com.victor.mine.MineModuleInit";
    private static final String SyncInit = "com.victor.sync.SyncModuleInit";
    public static String[] initModuleNames = {BaseInit, MainInit, WorkBenchInit, InventoryInit, MaterialsInit, InboundInit, MineInit, SyncInit};
}
