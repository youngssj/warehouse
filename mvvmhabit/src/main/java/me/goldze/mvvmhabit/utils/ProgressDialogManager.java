package me.goldze.mvvmhabit.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;
import java.lang.ref.WeakReference;

import me.goldze.mvvmhabit.R;
import me.goldze.mvvmhabit.widget.MProgressDialog;


/**
 * 描述：进度条管理类
 * -
 * 创建人：wangchunxiao
 * 创建时间：16/7/28
 */
public class ProgressDialogManager {
    private Dialog dialog;
    private WeakReference<Activity> activity;

    public ProgressDialogManager(Activity activity) {
        this.activity = new WeakReference<>(activity);
        dialog = new MProgressDialog(this.activity.get());
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
    }

    public void setProgressDialog(Dialog progressDialog) {
        if (progressDialog != null) {
            dialog = progressDialog;
        }
    }

    public void setCancelable(boolean isCancelable) {
        if (dialog != null) {
            dialog.setCancelable(isCancelable);
            dialog.setCanceledOnTouchOutside(isCancelable);
        }
    }

    private void setMessage(CharSequence message) {
        if (dialog != null) {
            if (message == null || message.length() == 0) {
                dialog.findViewById(R.id.message).setVisibility(View.GONE);
            } else {
                TextView txt = (TextView) dialog.findViewById(R.id.message);
                txt.setText(message);
            }
        }
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener cancelListener) {
        if (dialog != null) {
            // 监听返回键处理
            dialog.setOnCancelListener(cancelListener);
        }
    }

    public void cancelWaiteDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void showWaiteDialog(CharSequence message, boolean isCancelable, DialogInterface.OnCancelListener cancelListener) {
        setMessage(message);
        setOnCancelListener(cancelListener);
        if (dialog != null && !dialog.isShowing() && !this.activity.get().isFinishing()) {
            dialog.show();
        }
    }

    public void showWaiteDialog(CharSequence message, boolean isCancelable) {
        setMessage(message);
        setCancelable(isCancelable);
        if (dialog != null && !dialog.isShowing() && !this.activity.get().isFinishing()) {
            dialog.show();
        }
    }

    public void showWaiteDialog(CharSequence message) {
        setMessage(message);
        if (dialog != null && !dialog.isShowing() && !this.activity.get().isFinishing()) {
            dialog.show();
        }
    }

    public void showWaiteDialog() {
        showWaiteDialog(activity.get().getResources().getString(R.string.common_loading));
    }

    public void showClearDialog(String msg) {
        showWaiteDialog(msg);
    }

    public boolean isShow() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }

    public void release() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            if (dialog instanceof MProgressDialog) {
                ((MProgressDialog) dialog).release();
            }
            dialog = null;
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        if (dialog != null) {
            dialog.setOnDismissListener(onDismissListener);
        }
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        }
    }
}
