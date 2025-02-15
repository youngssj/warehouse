package com.example.hiu_931;

import static android.content.Context.AUDIO_SERVICE;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.hiultra.hiu_931.R;

import java.util.HashMap;


/**********************************
 *
 * 声音控制类
 *
 * Created by tutu on 2022/8/26
 **********************************/
public class SoundManager {

    static SoundPool soundPool = null;
    static HashMap<Integer, Integer> soundMap;
    static AudioManager audioManager;
    static float volumeRatio = 0f;

    // 初始化
    public static void initSound(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(3);
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        } else {
            soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 5);
        }
        loadSound(context);
        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
    }

    // 加载声音
    private static void loadSound(Context context) {
        soundMap = new HashMap(2);
        soundMap.put(0, soundPool.load(context, R.raw.barcodebeep, 1));
        soundMap.put(1, soundPool.load(context, R.raw.serror, 1));
    }

    // 释放
    public static void releaseSoundPool() {
        soundPool.release();
        soundPool = null;
    }

    // 播放声音
    public static void playSound(Integer id){
        Float audioMaxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 返回当前AudioManager对象的最大音量值
        Float audioCurrentVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 返回当前AudioManager对象的音量值

        volumeRatio = audioCurrentVolume / audioMaxVolume;

        try {
            soundPool.play(
                soundMap.get(id),
                volumeRatio,  // 左声道音量
                volumeRatio,  // 右声道音量
                1,  // 优先级，0为最低
                0,
                1f
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}