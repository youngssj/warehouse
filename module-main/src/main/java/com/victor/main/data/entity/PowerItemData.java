package com.victor.main.data.entity;

import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/22
 * 邮箱：jxfengmtx@gmail.com
 * 该实体类可以自定义,比如该类是数据库实体类. 或者是数据字典实体类, 但需要实现IKeyAndValue接口, 返回key和value两个值就可以在Spinner中绑定使用了
 */

public class PowerItemData implements IKeyAndValue {
    //key是下拉显示的文字
    private String key;
    //value是对应需要上传给后台的值, 这个可以根据具体业务具体定义
    private String power;

    public PowerItemData(String key, String power) {
        this.key = key;
        this.power = power;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return power;
    }
}