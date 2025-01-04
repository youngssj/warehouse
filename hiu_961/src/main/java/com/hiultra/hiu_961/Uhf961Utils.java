package com.hiultra.hiu_961;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.handheld.uhfr.UHFRManager;
import com.uhf.api.cls.Reader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.pda.serialport.Tools;
import me.goldze.mvvmhabit.utils.ToastUtils;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2021/1/25
 * 邮箱：jxfengmtx@gmail.com
 */
public class Uhf961Utils {

    private final Context mContext;
    private UHFRManager mReader;
    private boolean isStart;
    private AsyncTask<Void, Void, Set<String>> mEpcDataAsyncTask;
    public Set<String> mEpcSet = new HashSet<>(); //epc set ,epc list

    private boolean loopFlag;

    public Uhf961Utils(Context context) {
        this.mContext = context;
        getReaderInstance();
    }

    private void getReaderInstance() {
        try {
            if (mReader == null)
                mReader = UHFRManager.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startRead(ReadCallback readCallback) {
        if (mReader == null) {
            getReaderInstance();
        }
        if (loopFlag) {
            stopInventory();
            return;
        } else {
            loopFlag = true;
        }
        if (Reader.READER_ERR.MT_OK_ERR == mReader.asyncStartReading()) {
            loopFlag = true;
            readTag(readCallback);
        } else {
            stopInventory();
        }
    }


    public void readTag(final ReadCallback readCallback) {
        if (mEpcDataAsyncTask == null)
            mEpcDataAsyncTask = new AsyncTask<Void, Void, Set<String>>() {
                @Override
                protected Set<String> doInBackground(Void... voids) {
                    List<Reader.TAGINFO> list1;
                    String data;
                    mEpcSet.clear();
                    while (loopFlag) {
                        list1 = mReader.tagInventoryRealTime();
                        if (list1 != null && list1.size() > 0) {//
                            SoundUtil.play(1, 0);
                            for (Reader.TAGINFO tfs : list1) {
                                byte[] epcdata = tfs.EpcId;
                                data = Tools.Bytes2HexString(epcdata, epcdata.length);
                                mEpcSet.add(EncodeUtil.rfidDecode(data));  //解码后加入set集合
                                Log.i("data", "EPC:" + data);
                            }
                            if (isCancelled())
                                return mEpcSet;
                        }
                    }
                    return mEpcSet;
                }

                @Override
                protected void onCancelled(Set<String> epcData) {
                    super.onCancelled(epcData);
                    if (epcData.size() != 0)
                        readCallback.callback(mEpcSet);
                }

                @Override
                protected void onPostExecute(Set<String> epcData) {
                    if (epcData.size() != 0)
                        readCallback.callback(mEpcSet);
//                    updatePDItemModel(epcSet);
                }
            };
        mEpcDataAsyncTask.execute();
    }

    public void setPower(int power) {
        ToastUtils.showShort(Reader.READER_ERR.MT_OK_ERR == mReader.setPower(power, power)
                ? "设置成功" : "设置失败");
    }


    public interface ReadCallback {
        void callback(Set<String> epcData);
    }

    public void closeUhf() {
        if (mReader != null) {
            stopInventory();
            mReader.close();
            mReader = null;
        }
    }

    /**
     * 停止识别
     */
    private void stopInventory() {
        loopFlag = false;
        if (mReader != null)
            mReader.stopTagInventory();
        if (mEpcDataAsyncTask != null && !mEpcDataAsyncTask.isCancelled())
            mEpcDataAsyncTask.cancel(true);
        mEpcDataAsyncTask = null;
    }

}