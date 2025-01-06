package com.victor.base.data.entity;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/17
 * 邮箱：jxfengmtx@gmail.com
 */
public class AssetBorrowData {

    /**
     * dataList : [{"checkDetailId":156,"batchNumber":"pd20091605460001","materialId":289,"customerId":24,"checkResult":null,"handleResult":null,"checkDate":null,"remark":null,"createUserId":117,"createUserName":"测试人员","createDate":"2020-09-16 05:46:23","updateUserId":null,"updateUserName":null,"updateDate":null,"delflag":0,"materialCode":"zc20091506090001","materialName":"罗技G502","sortName":"鼠标","materiaStatus":"0","specification":"","useDepmName":"产品部"},{"checkDetailId":157,"batchNumber":"pd20091605460001","materialId":290,"customerId":24,"checkResult":null,"handleResult":null,"checkDate":null,"remark":null,"createUserId":117,"createUserName":"测试人员","createDate":"2020-09-16 05:46:23","updateUserId":null,"updateUserName":null,"updateDate":null,"delflag":0,"materialCode":"zc20091506100001","materialName":"罗技G302","sortName":"鼠标","materiaStatus":"0","specification":"","useDepmName":"销售部"}]
     * mainInfo : {"checkId":55,"batchNumber":"pd20091605460001","checkTheme":"盘点单","checkDepmCode":"002","checkDepmName":"销售部","checkUserId":129,"checkUserName":"补茹苟","checkDate":"2020-09-17 00:00:00","checkEndDate":"2020-09-20 00:00:00","checkStartDate":null,"customerId":24,"remark":"","createDate":"2020-09-16 05:46:23","createUserId":117,"createUserName":"测试人员","updateDate":null,"updateUserId":null,"updateUserName":null,"status":1,"delflag":0,"checkResult":null,"locationCode":null,"locationName":null,"materialCode":null,"materialName":null,"sortName":null,"materiaStatus":null,"assetvalue":0,"detailDataIndex":"289,290","detailLi":null}
     */

    private AssetApproveOdd mainInfo;
    private List<DataListBean> dataList;

    public AssetApproveOdd getMainInfo() {
        return mainInfo;
    }

    public void setMainInfo(AssetApproveOdd mainInfo) {
        this.mainInfo = mainInfo;
    }

    public List<DataListBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListBean> dataList) {
        this.dataList = dataList;
    }

    public static class DataListBean {
        /**
         * checkDetailId : 156
         * batchNumber : pd20091605460001
         * materialId : 289
         * customerId : 24
         * checkResult : null
         * handleResult : null
         * checkDate : null
         * remark : null
         * createUserId : 117
         * createUserName : 测试人员
         * createDate : 2020-09-16 05:46:23
         * updateUserId : null
         * updateUserName : null
         * updateDate : null
         * delflag : 0
         * materialCode : zc20091506090001
         * materialName : 罗技G502
         * sortName : 鼠标
         * materiaStatus : 0
         * specification :
         * useDepmName : 产品部
         */


        public Drawable bgColor;
        public Drawable textColor;



        public Drawable getBgColor() {
            return bgColor;
        }

        public void setBgColor(Drawable bgColor) {
            this.bgColor = bgColor;
        }

        public Drawable getTextColor() {
            return textColor;
        }

        public void setTextColor(Drawable textColor) {
            this.textColor = textColor;
        }
    }
}
