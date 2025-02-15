package com.example.hiu_931;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.handheld.uhfr.UHFRManager;
import com.uhf.api.cls.Reader;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.pda.serialport.Tools;
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
public class Uhf931Utils {

    public static UHFRManager mUhfrManager;
    private Activity activity;
    private ProgressDialogManager mProgressDialogManager;
    private ExecutorService executorService;
    Handler uiThread = new Handler(Looper.getMainLooper());
    private int power;
    private boolean isReader = false;

    private ReadCallback readCallback;

    public Uhf931Utils(Activity activity) {
        this.activity = activity;
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(2);
        }
    }

    public void initUHF(Context context) {
        showProgress();

        executorService.submit(() -> {

            if (mUhfrManager == null) {
                mUhfrManager = UHFRManager.getInstance();
            }

            if (mUhfrManager != null && power > 0) {
                mUhfrManager.setPower(power, power);
            }

            uiThread.post(() -> {
                dismissProgress();
                ToastUtils.showShort(mUhfrManager != null ? "初始化成功" : "初始化失败");
            });
        });
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
        this.readCallback = readCallback;
        if (mUhfrManager == null) {
            initUHF(context);
        }
        if (!isReader) {
            inventoryEPC(false);
        } else {
            stopInventory();
        }
    }

    public void startSingleRead(Context context, ReadCallback readCallback) {
        this.readCallback = readCallback;
        if (mUhfrManager == null) {
            initUHF(context);
        }
        if (!isReader) {
            inventoryEPC(true);
        } else {
            stopInventory();
        }
    }

    private void inventoryEPC(boolean isSingle) {
        isReader = true;
        mUhfrManager.setGen2session(false);
        //启动盘存线程
        if (isSingle) {
            power = mUhfrManager.getPower()[0];
            mUhfrManager.setPower(5, 5);
            handler.postDelayed(singleThread, 0);
        } else {
            handler.postDelayed(invenrotyThread, 0);
        }
    }

    private Runnable invenrotyThread = new Runnable() {
        @Override
        public void run() {
            Log.i("flutter", "invenrotyThread is running");
            List<Reader.TAGINFO> listTag = null;

            listTag = mUhfrManager.tagEpcTidInventoryByTimer((short) 50);

            Log.i("flutter", "listTag" + listTag);
            if (listTag != null && !listTag.isEmpty()) {
                SoundManager.playSound(0);

                Set<String> epcSet = new HashSet<>();
                for (Reader.TAGINFO taginfo : listTag) {
                    Tag tag = pooled6cData(taginfo);
                    if (tag != null) {
                        epcSet.add(tag.getEpc());
                    }
                }
                if (readCallback != null) {
                    readCallback.callback(epcSet);
                }
            }

            handler.postDelayed(invenrotyThread, 0);
        }
    };

    private Runnable singleThread = new Runnable() {
        @Override
        public void run() {
            List<Reader.TAGINFO> listTag = null;
            listTag = mUhfrManager.tagEpcTidInventoryByTimer((short) 50);

            Log.i("flutter", "listTag" + listTag);
            boolean hasRead = false;
            if (listTag != null && !listTag.isEmpty()) {
                SoundManager.playSound(0);

                for (Reader.TAGINFO taginfo : listTag) {
                    Tag tag = pooled6cData(taginfo);
                    if (tag != null) {
                        Set<String> epcSet = new HashSet<>();
                        epcSet.add(tag.getEpc());
                        if (readCallback != null) {
                            readCallback.callback(epcSet);
                        }
                        hasRead = true;
                        mUhfrManager.setPower(power, power);
                        stopInventory();
                        break;
                    }
                }
            }

            if (!hasRead) {
                handler.postDelayed(singleThread, 0);
            }
        }
    };

    public Tag pooled6cData(Reader.TAGINFO info) {
        Map<String, Tag> tagInfoMap = new LinkedHashMap<String, Tag>();
        String epcAndTid = Tools.Bytes2HexString(info.EpcId, info.EpcId.length);
        android.util.Log.i("Inv", "[pooled6cData] tag epc: " + epcAndTid);
        if (info.EmbededData != null) {
            epcAndTid = Tools.Bytes2HexString(info.EmbededData, info.EmbededData.length);
            android.util.Log.i("Inv", "[pooled6cData] tag tid: " + epcAndTid);
            if (TextUtils.isEmpty(epcAndTid)) {
                return null;
            }
        } else {
            android.util.Log.i("Inv", "[pooled6cData] drop null tid tag");
            return null;
        }
        Tag tag = new Tag();
        tag.setEpc(Tools.Bytes2HexString(info.EpcId, info.EpcId.length));
        tag.setCount(1);
        if (info.EmbededData != null && info.EmbededDatalen > 0) {
            tag.setTid(Tools.Bytes2HexString(info.EmbededData, info.EmbededDatalen));
        }
        return tag;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            }
        }
    };

    @SuppressLint("StaticFieldLeak")
    public void setPower(int power) {
        this.power = power;
        showProgress();
        executorService.submit(() -> {
            stopInventory();
            Reader.READER_ERR readerErr = mUhfrManager.setPower(power, power);
            uiThread.post(() -> {
                dismissProgress();
                ToastUtils.showShort(readerErr == Reader.READER_ERR.MT_OK_ERR ? "设置成功" : "设置失败");
            });
        });
    }

    public int getPower() {
        stopInventory();
        if (mUhfrManager != null)
            return mUhfrManager.getPower()[0];
        return 5;
    }

    public void closeUhf() {
        stopInventory();
        try {
            if (mUhfrManager != null) {
                mUhfrManager = null;
            }
        } catch (Exception e) {

        }
        executorService.shutdownNow();
    }

    /**
     * 停止识别
     */
    private void stopInventory() {
        handler.removeCallbacksAndMessages(null);
        isReader = false;
    }

    public interface ReadCallback {
        void callback(Set<String> epcData);
    }

}





