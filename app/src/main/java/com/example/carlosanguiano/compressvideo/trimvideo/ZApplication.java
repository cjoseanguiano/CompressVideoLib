package com.example.carlosanguiano.compressvideo.trimvideo;

import android.app.Application;
import android.content.Context;

import com.example.carlosanguiano.compressvideo.compresscomplete.file.FileUtils;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import iknow.android.utils.BaseUtils;

public class ZApplication extends Application {
    private static ZApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        BaseUtils.init(this);
        initImageLoader(this);
        initFFmpegBinary(this);
        FileUtils.createApplicationFolder(this);
        instance = this;
    }

    public static void initImageLoader(Context context) {
        int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 10);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).memoryCache(new LRULimitedMemoryCache(memoryCacheSize))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    private void initFFmpegBinary(Context context) {

        try {
            FFmpeg.getInstance(context).loadBinary(new LoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                }
            });
        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public static ZApplication getInstance() {
        return instance;
    }
}
