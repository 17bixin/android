package com.alens.utils;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Glide 加载图片工具类
 */
public class GlideUtils {
//    private static int placeholderId = R.drawable.ic_image_loading;
//    private static int errorId = R.;
    /**
     * 加载图片
     * fallback imageUrl为null显示
     * priority 设置优先级
     * asGif 会检查是不是gif图片
     * */
    public static void loadingImg(Context context, ImageView iv, String picUrl){
        Glide.with(context)
                .load(picUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(Color.GRAY)
                .error(Color.GRAY)
                .fallback(Color.GRAY)
                .priority(Priority.IMMEDIATE)
                .dontAnimate()
                .into(iv);
    }

    public static void loadingImg(Context context, ImageView iv, int resId){
        Glide.with(context)
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(Color.GRAY)
                .error(Color.GRAY)
                .fallback(Color.GRAY)
                .priority(Priority.IMMEDIATE)
                .dontAnimate()
                .into(iv);
    }

    /**
     * 加载gif图
     */
    public static void loadGif(Context context, ImageView iv, String picUrl){
        Glide.with(context)
                .load(picUrl)
                .asGif()
                .into(iv);
    }

    public static void loadingImg(Context context, ImageView iv, String picUrl, int placeholderAvatar){
        Glide.with(context)
                .load(picUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(Color.GRAY)
                .dontAnimate()
                .into(iv);
    }
    public static void loadingImgWithError(Context context, ImageView iv, String picUrl, int errorAvatar){
        Glide.with(context)
                .load(picUrl)
                .error(errorAvatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(iv);
    }
    public static void loadingImgWithBlur(Context context, ImageView iv, String picUrl){
        Glide.with(context)
                .load(picUrl)
                .error(Color.GRAY)
                .bitmapTransform(new BlurTransformation(context, 25, 4))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(iv);
    }
    public static void loadingImgWithBlur(Context context, ImageView iv, String picUrl, int error){
        Glide.with(context)
                .load(picUrl)
                .error(error)
                .bitmapTransform(new BlurTransformation(context, Glide.get(context).getBitmapPool()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(iv);
    }

    public static void displayRound(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(Color.GRAY)
                .centerCrop()
                .bitmapTransform(new RoundedCornersTransformation(context, 100, 0, RoundedCornersTransformation.CornerType.ALL))
                .dontAnimate()
                .into(imageView);
    }

    public static void displayCircle(Context context, ImageView imageView, String url) {
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(Color.GRAY)
                .centerCrop()
                .bitmapTransform(new CropCircleTransformation(context))
                .dontAnimate()
                .into(imageView);
    }

    public static void clear(Context context){
        clearImageDiskCache(context);
        Glide.get(context).clearDiskCache();
    }

    /**
     * 清除图片磁盘缓存
     */
    private static void clearImageDiskCache(final Context context) {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(context).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(context).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
