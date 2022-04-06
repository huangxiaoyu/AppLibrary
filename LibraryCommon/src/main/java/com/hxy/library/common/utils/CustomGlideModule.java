package com.hxy.library.common.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

/**
 * huangxiaoyu
 * 2020/4/7 0007 17:29
 */
@GlideModule
public class CustomGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        super.applyOptions(context, builder);
        //        设置缓存大小为30mb
        int memoryCacheSizeBytes = 1024 * 1024 * 30; // 30mb
        //        设置内存缓存大小
        builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));
        //        根据SD卡是否可用选择是在内部缓存还是SD卡缓存
        if (context.getExternalCacheDir() != null) {
            builder.setDiskCache(new ExternalPreferredCacheDiskCacheFactory(context, "glide",
                    memoryCacheSizeBytes * 2));
        } else {
            builder.setDiskCache(new InternalCacheDiskCacheFactory(context, "glide", memoryCacheSizeBytes));
        }

    }


    // 针对V4用户可以提升速度
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
