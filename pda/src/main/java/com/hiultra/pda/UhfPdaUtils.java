package com.hiultra.assetmanager.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hiultra.pda.EncodeUtil;
import com.supoin.rfidservice.sdk.DataUtils;
import com.supoin.rfidservice.sdk.ModuleController;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;

import static android.content.ContentValues.TAG;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/17
 * 邮箱：jxfengmtx@gmail.com
 */
public class UhfPdaUtils {

    public static ModuleController moduleController;
    private Context mContext;

    public Set<String> mEpcSet = new HashSet<>(); //epc set ,epc list
    private static AsyncTask<Void, Void, Set<String>> mEpcDataAsyncTask;
    private static AsyncTask<Integer, Void, Void> mPowerAsyncTask;
    private MaterialDialog mDialog;
    private boolean isInventory;

    public UhfPdaUtils(Context context) {
        this.mContext = context;
        getReaderInstance(context);
    }

    private void getReaderInstance(Context context) {
        try {
            if (moduleController == null) {
                showDialog();
                moduleController = ModuleController.getInstance(context, new ModuleController.DataListener() {
                    public void onError() {
                        Toast.makeText(context, "模块不存在", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onConnect(boolean isSuccess) {
                        super.onConnect(isSuccess);
                        dismissDialog();
                        if (isSuccess) {
                            Toast.makeText(context, "连接成功", Toast.LENGTH_SHORT).show();
                        }
                        moduleController.moduleSetBeep(true);
                        moduleController.moduleGetPowerRange();
                    }

                    @Override
                    public void onInventoryTag(byte[] epcTid, byte[] epc, byte[] tid, byte[] pc, byte count, float rssi, float freq) {
                        super.onInventoryTag(epcTid, epc, tid, pc, count, rssi, freq);
//                        ToastUtils.showShort(epc + "");
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initUHF() {
    }

    private void showDialog() {
        mDialog = MaterialDialogUtils.showIndeterminateProgressDialog(mContext, "加载中", true).show();
    }

    private void dismissDialog() {
        if (mDialog != null && mDialog.isShowing())
            mDialog.dismiss();
    }

    public void startRead(ReadCallback readCallback) {
        if (moduleController == null)
            getReaderInstance(mContext);

        isInventory = !isInventory;
        if (isInventory) {
            moduleController.moduleInventoryTag();
            moduleController.tagList.clear();
        } else {
            stopInventory();
            isInventory = false;
        }
        for (Map<String, String> map : moduleController.tagList) {
            String epcTid = map.get("epcTid");
            Log.i(TAG, "startRead: " + epcTid);
//            ToastUtils.showShort(epcTid);
            mEpcSet.add(EncodeUtil.rfidDecode(epcTid));  //解码后加入set集合
        }
        readCallback.callback(mEpcSet);
    }

    public void setPower(int power) {
        stopInventory();
        mPowerAsyncTask = new AsyncTask<Integer, Void, Void>() {
            @Override
            protected Void doInBackground(Integer... integers) {
                moduleController.moduleSetParameters(DataUtils.PARA_POWER, power);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                ToastUtils.showShort("设置完成");
            }
        };
        mPowerAsyncTask.execute(power);
    }

    public void closeUhf() {
        if (moduleController != null) {
            moduleController.close();
            moduleController = null;
        }
    }

    /**
     * 停止识别
     */
    private void stopInventory() {
        new Thread(() -> {
            moduleController.moduleStopInventoryTag();
        }).start();
        if (mEpcDataAsyncTask != null && !mEpcDataAsyncTask.isCancelled())
            mEpcDataAsyncTask.cancel(true);
        mEpcDataAsyncTask = null;
        mPowerAsyncTask = null;
    }

    public interface ReadCallback {
        void callback(Set<String> epcData);
    }

}





