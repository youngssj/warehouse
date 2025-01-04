package com.hiultra.c72;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;
import java.util.Map;

/**
 * 版权：heihei
 *
 * @author JiangFB
 * 版本：1.0
 * 创建日期：2020/9/18
 * 邮箱：jxfengmtx@gmail.com
 */
public class SoundUtil {
    public static SoundPool sp;
    public static Map<Integer, Integer> suondMap;
    private static Context context;

    public static void initSoundPool(Context context) {
        SoundUtil.context = context;
        SoundPool.Builder spb = new SoundPool.Builder();
        spb.setMaxStreams(10);
        sp = spb.build();      //创建SoundPool对象
        suondMap = new HashMap<Integer, Integer>();
        suondMap.put(1, sp.load(context, R.raw.scan, 1));
    }

    public static void play(int sound, int loopNum) {
        AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
        float audioMaxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        float audioCurrentVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolume / audioMaxVolume;

        try {
            sp.play(suondMap.get(sound), volumnRatio, // 左声道音量
                    volumnRatio, // 右声道音量
                    1, // 优先级，0为最低
                    loopNum, // 循环次数，0无不循环，-1无永远循环
                    1 // 回放速度 ，该值在0.5-2.0之间，1为正常速度
            );
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void pasue() {
        sp.pause(0);
    }
}
