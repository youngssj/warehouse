package com.hiultra.c72;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.rscja.deviceapi.RFIDWithUHFUART;
import com.rscja.deviceapi.entity.UHFTAGInfo;
import com.rscja.deviceapi.exception.ConfigurationException;
import com.rscja.deviceapi.interfaces.ConnectionStatus;
import com.rscja.deviceapi.interfaces.ConnectionStatusCallback;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import me.goldze.mvvmhabit.utils.KLog;
import me.goldze.mvvmhabit.utils.ProgressDialogManager;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/17
 * 邮箱：jxfengmtx@gmail.com
 */
public class UhfC72Utils {

    public static RFIDWithUHFUART mReader;
    private Activity activity;
    public Set<String> mEpcSet = new HashSet<>(); //epc set ,epc list
    public static boolean loopFlag;
    private ProgressDialogManager mProgressDialogManager;
    private ExecutorService executorService;
    Handler uiThread = new Handler(Looper.getMainLooper());
    private int power;

    public UhfC72Utils(Activity activity) {
        this.activity = activity;
        getReaderInstance();
    }

    private void getReaderInstance() {
        try {
            if (mReader == null) {
                mReader = RFIDWithUHFUART.getInstance();
            }

            if (executorService == null)
                executorService = Executors.newFixedThreadPool(2);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }


    public void initUHF(Context context, int power) {
        this.power = power;
        initUHF(context);
    }

    public void initUHF(Context context) {
        getReaderInstance();
        showProgress();
        if (mReader != null) {
            executorService.submit(() -> {

                //监听uhf连接状态
                mReader.setConnectionStatusCallback(new ConnectionStatusCallback() {
                    @Override
                    public void getStatus(ConnectionStatus status, Object device) {
                        if (status == ConnectionStatus.CONNECTED) {
                            //UHF已经连接
                            Log.e("123", "UHF已经连接");
                            mReader.setEPCAndTIDMode();
                        } else if (status == ConnectionStatus.DISCONNECTED) {
                            //UHF连接断开
                            Log.e("123", "UHF连接断开");
                        }
                    }
                });
                boolean b = mReader.init(context);

                if(power > 0){
                    mReader.setPower(power);
                }

                uiThread.post(() -> {
                    dismissProgress();
                    ToastUtils.showShort(b ? "初始化成功" : "初始化失败");
                });
            });

        }
    }

    public void showProgress() {
        if (mProgressDialogManager == null) {
            mProgressDialogManager = new ProgressDialogManager(activity);
            if (!mProgressDialogManager.isShow()) {
                mProgressDialogManager.setCanceledOnTouchOutside(false);
                mProgressDialogManager.setCancelable(true);
                mProgressDialogManager.showWaiteDialog();
            }
        }
    }

    public void dismissProgress() {
        if (mProgressDialogManager != null) {
            mProgressDialogManager.cancelWaiteDialog();
        }
    }

    public void startRead(Context context, ReadCallback readCallback) {
        if (mReader == null) {
            initUHF(context);
        }
        if (loopFlag) {
            stopInventory();
            return;
        } else {
            loopFlag = true;
        }
        if (mReader.startInventoryTag()) {
            loopFlag = true;
            readTag(readCallback);
        } else {
            stopInventory();
        }
    }

    private long time;

    public void readTag(final ReadCallback readCallback) {
        executorService.submit(() -> {
            time = System.currentTimeMillis();
            String strTid;
            String strResult;
            UHFTAGInfo res = null;
//                    mEpcSet.clear();
            while (loopFlag) {
                if (mReader == null)
                    return;
                res = mReader.readTagFromBuffer();

                if (res != null) {
                    strTid = res.getTid();
                    if (strTid.length() != 0 && !strTid.equals("0000000" +
                            "000000000") && !strTid.equals("000000000000000000000000")) {
                        strResult = "TID:" + strTid + "\n";
                    } else {
                        strResult = "";
                    }

                    Log.e("data", "EPC:" + res.getEPC() + "|" + strResult);
//                    String epc = res.getEPC();
//                    String epc = strTid;   //要求读tid

//                    String epc = EncodeUtil.rfidDecode(res.getEPC());
//                    String epc = EncodeUtil.rfidCut(res.getEPC());
                    String epc = res.getEPC();
                    if (!"".equals(epc))
                        mEpcSet.add(epc);  //解码后加入set集合
                    SoundUtil.play(1, 0);

                    if ((System.currentTimeMillis() - time) > 1000) {
                        KLog.i("一秒钟---");
                        time = System.currentTimeMillis();

                        synchronized (mEpcSet) {
                            if (mEpcSet != null && mEpcSet.size() != 0) {
                                Set<String> epcSet = new HashSet<>(mEpcSet);
                                mEpcSet.clear();
                                uiThread.post(() -> {
                                    if (epcSet != null && epcSet.size() != 0) {
                                        readCallback.callback(epcSet);
                                    }
                                });
                            }
                        }
                    }
                }
            }
            uiThread.postDelayed(() -> {
                if (mEpcSet != null && mEpcSet.size() != 0)
                    readCallback.callback(mEpcSet);
            }, 500);
        });
    }

    @SuppressLint("StaticFieldLeak")
    public void setPower(int power) {
        showProgress();
        executorService.submit(() -> {
            stopInventory();
            boolean b = mReader.setPower(power);
            uiThread.post(() -> {
                dismissProgress();
                ToastUtils.showShort(b ? "设置成功" : "设置失败");
            });
        });
    }

    public int getPower() {
        stopInventory();
        if (mReader != null)
            return mReader.getPower();
        return 5;
    }

    public void closeUhf() {
        stopInventory();
        try {
            if (mReader != null) {
                mReader.free();
                mReader = null;
            }
        } catch (Exception e) {

        }
        executorService.shutdownNow();
    }

    /**
     * 停止识别
     */
    private void stopInventory() {
        loopFlag = false;
        if (mReader != null)
            mReader.stopInventory();
    }

    public interface ReadCallback {
        void callback(Set<String> epcData);
    }

}





