package com.victor.base.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserInfoBean {
    private String createBy;
    private String createTime;
    private Object updateBy;
    private Object updateTime;
    private String remark;
    private Params params;
    private int userId;
    private int deptId;
    private String userName;
    private String nickName;
    private String email;
    private String phonenumber;
    private String sex;
    private String avatar;
    private String password;
    private String status;
    private String delFlag;
    private String loginIp;
    private String loginDate;
    private Dept dept;
    private List<Roles> roles;
    private Object roleIds;
    private Object postIds;
    private Object roleId;
    private boolean admin;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Object getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Object updateBy) {
        this.updateBy = updateBy;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(String loginDate) {
        this.loginDate = loginDate;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public Object getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Object roleIds) {
        this.roleIds = roleIds;
    }

    public Object getPostIds() {
        return postIds;
    }

    public void setPostIds(Object postIds) {
        this.postIds = postIds;
    }

    public Object getRoleId() {
        return roleId;
    }

    public void setRoleId(Object roleId) {
        this.roleId = roleId;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public static class Params {
        @SerializedName("@type")
        private String _$Type169;// FIXME check this code

        public String get_$Type169() {
            return _$Type169;
        }

        public void set_$Type169(String _$Type169) {
            this._$Type169 = _$Type169;
        }
    }

    public static class Dept {
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private Params params;
        private int deptId;
        private int parentId;
        private String ancestors;
        private String deptName;
        private int orderNum;
        private String leader;
        private Object phone;
        private Object email;
        private String status;
        private Object delFlag;
        private Object parentName;
        private List<?> children;

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Params getParams() {
            return params;
        }

        public void setParams(Params params) {
            this.params = params;
        }

        public int getDeptId() {
            return deptId;
        }

        public void setDeptId(int deptId) {
            this.deptId = deptId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getAncestors() {
            return ancestors;
        }

        public void setAncestors(String ancestors) {
            this.ancestors = ancestors;
        }

        public String getDeptName() {
            return deptName;
        }

        public void setDeptName(String deptName) {
            this.deptName = deptName;
        }

        public int getOrderNum() {
            return orderNum;
        }

        public void setOrderNum(int orderNum) {
            this.orderNum = orderNum;
        }

        public String getLeader() {
            return leader;
        }

        public void setLeader(String leader) {
            this.leader = leader;
        }

        public Object getPhone() {
            return phone;
        }

        public void setPhone(Object phone) {
            this.phone = phone;
        }

        public Object getEmail() {
            return email;
        }

        public void setEmail(Object email) {
            this.email = email;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Object delFlag) {
            this.delFlag = delFlag;
        }

        public Object getParentName() {
            return parentName;
        }

        public void setParentName(Object parentName) {
            this.parentName = parentName;
        }

        public List<?> getChildren() {
            return children;
        }

        public void setChildren(List<?> children) {
            this.children = children;
        }
    }

    public static class Roles {
        private Object createBy;
        private Object createTime;
        private Object updateBy;
        private Object updateTime;
        private Object remark;
        private Params params;
        private int roleId;
        private String roleName;
        private String roleKey;
        private int roleSort;
        private String dataScope;
        private boolean menuCheckStrictly;
        private boolean deptCheckStrictly;
        private String status;
        private Object delFlag;
        private boolean flag;
        private Object menuIds;
        private Object deptIds;
        private Object permissions;
        private boolean admin;

        public Object getCreateBy() {
            return createBy;
        }

        public void setCreateBy(Object createBy) {
            this.createBy = createBy;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(Object updateBy) {
            this.updateBy = updateBy;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public Params getParams() {
            return params;
        }

        public void setParams(Params params) {
            this.params = params;
        }

        public int getRoleId() {
            return roleId;
        }

        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getRoleKey() {
            return roleKey;
        }

        public void setRoleKey(String roleKey) {
            this.roleKey = roleKey;
        }

        public int getRoleSort() {
            return roleSort;
        }

        public void setRoleSort(int roleSort) {
            this.roleSort = roleSort;
        }

        public String getDataScope() {
            return dataScope;
        }

        public void setDataScope(String dataScope) {
            this.dataScope = dataScope;
        }

        public boolean isMenuCheckStrictly() {
            return menuCheckStrictly;
        }

        public void setMenuCheckStrictly(boolean menuCheckStrictly) {
            this.menuCheckStrictly = menuCheckStrictly;
        }

        public boolean isDeptCheckStrictly() {
            return deptCheckStrictly;
        }

        public void setDeptCheckStrictly(boolean deptCheckStrictly) {
            this.deptCheckStrictly = deptCheckStrictly;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(Object delFlag) {
            this.delFlag = delFlag;
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public Object getMenuIds() {
            return menuIds;
        }

        public void setMenuIds(Object menuIds) {
            this.menuIds = menuIds;
        }

        public Object getDeptIds() {
            return deptIds;
        }

        public void setDeptIds(Object deptIds) {
            this.deptIds = deptIds;
        }

        public Object getPermissions() {
            return permissions;
        }

        public void setPermissions(Object permissions) {
            this.permissions = permissions;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "createBy='" + createBy + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                ", params=" + params +
                ", userId=" + userId +
                ", deptId=" + deptId +
                ", userName='" + userName + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", sex='" + sex + '\'' +
                ", avatar='" + avatar + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginDate='" + loginDate + '\'' +
                ", dept=" + dept +
                ", roles=" + roles +
                ", roleIds=" + roleIds +
                ", postIds=" + postIds +
                ", roleId=" + roleId +
                ", admin=" + admin +
                '}';
    }
}
