package com.hiultra.c72;

import static com.rscja.barcode.BarcodeUtility.ModuleType.BARCODE_2D;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.util.Log;

import com.rscja.barcode.BarcodeUtility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/12/16
 * 邮箱：jxfengmtx@gmail.com
 */
public class BarCodeHelper {
    private static BarCodeHelper instance;
    private static Context mContext;
    private String TAG = "BarCodeHelper";
    BarcodeUtility barcodeUtility = null;
    private String barCode;
    private CallBack mCallBack;
    private BarcodeDataReceiver barcodeDataReceiver;
    private ExecutorService executorService;

    public static BarCodeHelper getInstance(Context context) {
        if (context == null) throw new IllegalArgumentException("context can not be null");
        mContext = context;
        if (instance == null) instance = new BarCodeHelper();
        DevBeep.init(context);
        return instance;
    }

    public void init() {
        barcodeUtility = BarcodeUtility.getInstance();
        if (barcodeUtility != null) {
            executorService = Executors.newFixedThreadPool(1);
            executorService.submit(this::initTask);
        }
    }

    public void closeScan() {
        if (barcodeUtility != null) {
            barcodeUtility.stopScan(mContext, BARCODE_2D);
            barcodeUtility.close(mContext, BARCODE_2D);
        }
        if (!executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }


    public void ScanBarcode(CallBack callBack) {
        mCallBack = callBack;
        if (barcodeUtility != null) {
            Log.i(TAG, "ScanBarcode");
            barcodeUtility.startScan(mContext, BarcodeUtility.ModuleType.BARCODE_2D);
        }
    }

    public interface CallBack {
        void scanCallBack(String info);
    }

    private void initTask() {
        if (barcodeUtility != null) {
            barcodeUtility.setScanResultBroadcast(mContext, "com.scanner.broadcast", "data");
            barcodeUtility.open(mContext, BARCODE_2D);//打开2D
            barcodeUtility.setReleaseScan(mContext, false);//设置松开扫描按键，不停止扫描
            barcodeUtility.setScanFailureBroadcast(mContext, true);//扫描失败也发送广播
            barcodeUtility.enableContinuousScan(mContext, false);//关闭键盘助手连续扫描
            barcodeUtility.enablePlayFailureSound(mContext, false);//关闭键盘助手 扫描失败的声音
            //barcode2DWithSoft.enablePlaySuccessSound(context, false);//关闭键盘助手 扫描成功的声音
            barcodeUtility.enableEnter(mContext, false);//关闭回车
            barcodeUtility.setBarcodeEncodingFormat(mContext, 1);
            if (barcodeDataReceiver == null) {
                barcodeDataReceiver = new BarcodeDataReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.scanner.broadcast");
                mContext.registerReceiver(barcodeDataReceiver, intentFilter);
            }
        }
    }

    protected class BarcodeDataReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String barCode = intent.getStringExtra("data");
            String status = intent.getStringExtra("SCAN_STATE");
            if (status != null && (status.equals("cancel"))) {
                return;
            } else {
                if (TextUtils.isEmpty(barCode)) {
                    barCode = "Scan fail";
                    DevBeep.PlayErr();
                } else if (mCallBack != null) {
                    DevBeep.PlayOK();
                    mCallBack.scanCallBack(barCode);
                }
            }
        }
    }

}
