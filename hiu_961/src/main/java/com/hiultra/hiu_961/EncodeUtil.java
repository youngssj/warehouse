package com.hiultra.hiu_961;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/11/14
 * 邮箱：jxfengmtx@gmail.com
 */
public class EncodeUtil {
    /**
     * rfid 加密
     */
    public static String rfidEncode(String strCarddata) {
        String strResult = new String();
        try {
            //1.	前边补0，补足22位 ：000000 5000  0000  0000  0003
            String strTemp = String.format("%022d", Long.parseLong(strCarddata));
            //2.	中间分叉得到两个11位数据A、B：A=000000 50000，B=000  0000  0003
            System.out.println(strTemp);
            String strA = strTemp.substring(0, 11);
            String strB = strTemp.substring(11, 22);
            System.out.println(strA);
            System.out.println(strB);
            //3.	A、B各自加5，得到: A1=000000 50005    ,B1=000  0000  0008
            long lA = Long.parseLong(strA) + 3;
            long lB = Long.parseLong(strB) + 3;
            System.out.println(lB);
            System.out.println(strA);
            //4.	H1=A1 MOD7=4   , H2=B1 MOD7=1
            long iHa = lA % 7;
            long iHb = lB % 7;


            //5.	A2=A1-H1=50005-4=50001  ;B2=B1-H2=8-1=7
            long iA2 = lA - iHa;
            long iB2 = lB - iHb;

            //6.	A3=A2/7=7143  ,B3=B2/7=1
            long iA3 = iA2 / 7;
            long iB3 = iB2 / 7;

            //7.	将A3补足11位得到A4=0000 000 7143；B3补足11位得到 B4=0000 0000 001
            String strA4 = String.format("%011d", iA3);
            String strB4 = String.format("%011d", iB3);

            //8.	拼接A4 H1 H2 B4,得到写入数据：000000071434100000000001
            strResult = strA4 + iHa + "" + iHb + strB4;
        } catch (Exception e) {
            e.printStackTrace();
            strResult = "";
        }

        return strResult;
    }

    /**
     * rfid 解密
     */
    public static String rfidDecode(String strCarddata) {
        if (null == strCarddata || strCarddata.length() != 24) {
            return strCarddata;
        }
        String strResult = new String();
        try {
            strCarddata = strCarddata.substring(0, 24);
            String strA4 = strCarddata.substring(0, 11);
            long iHa = Long.parseLong(strCarddata.substring(11, 12));
            long iHb = Long.parseLong(strCarddata.substring(12, 13));
            String strB4 = strCarddata.substring(13, 24);

            long iA2 = Long.parseLong(strA4) * 7;
            long iB2 = Long.parseLong(strB4) * 7;

            long lA = iA2 + iHa;
            long lB = iB2 + iHb;

            long A = lA - 3;
            long B = lB - 3;

            strResult = A + "" + String.format("%011d", B);
        } catch (Exception e) {
            e.printStackTrace();
            strResult = "";
        }
        KLog.i(strResult);
        return strResult;
    }

}
