package me.goldze.mvvmhabit.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import me.goldze.mvvmhabit.R;


/**
 * 描述：
 * -
 * 创建人：wangchunxiao
 * 创建时间：2017/3/6
 */
public class MProgressDialog extends Dialog {

    private Animation operatingAnim;

    public MProgressDialog(Context context) {
        super(context, R.style.ProgressDialogStyle);
        init();
    }

    private void init() {
        setTitle("");
        setContentView(R.layout.common_base_layout_progress_dialog);
        // 按返回键是否取消
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        // 设置居中
        getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0f;
        getWindow().setAttributes(lp);
    }

    /**
     * 当窗口焦点改变时调用
     */
    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = (ImageView) findViewById(R.id.spinnerImageView);
//        final RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
//                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        animation.setDuration(3000);//设置动画持续时间
//        animation.setRepeatCount(Animation.INFINITE);//设置重复次数
//        animation.setFillAfter(false);//动画执行完后是否停留在执行完的状态
//        animation.setStartOffset(0);//执行前的等待时间
//        LinearInterpolator lir = new LinearInterpolator();
//        animation.setInterpolator(lir);
//        imageView.setAnimation(animation);
//        animation.startNow();
        operatingAnim = AnimationUtils.loadAnimation(getContext(), R.anim.rotation);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        imageView.startAnimation(operatingAnim);
    }

    public void release() {
        if (operatingAnim != null) {
            operatingAnim.cancel();
            operatingAnim = null;
        }
    }
}