package com.victor.base.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.victor.base.data.entity.UserToken;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/9
 * 邮箱：jxfengmtx@gmail.com
 *
 * 配合room保存数据
 */

@Dao
public interface  UserTokenDao {

    @Insert
    void saveUserToken(UserToken userToken);

    @Query("SELECT * FROM UserToken")
    UserToken getUserToken();
}
