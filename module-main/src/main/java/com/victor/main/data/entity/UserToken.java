package com.victor.main.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

@Entity
public class UserToken {

    /**
     * userPhone : null
     * userName : 12000000000
     * token : eyJhbGciOiJIUzI1NiJ9.eyJkZXBtQ29kZSI6bnVsbCwibG9naW5OYW1lIjoiMTIwMDAwMDAwMDAiLCJjdXN0b21lckNvZGUiOiJ0MSIsInVzZXJOYW1lIjpudWxsLCJleHAiOjE1OTk2NTQyNjAsInVzZXJJZCI6MTIyLCJpYXQiOjE1OTk2MzYyNjB9.A4RcKo-Ja816BjpcGSIH-yQJB3GW9K9hU0sBc8ACdls
     */
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull
    private String userName;
    private String userPhone;
    private String token;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
