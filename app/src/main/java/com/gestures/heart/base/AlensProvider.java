package com.gestures.heart.base;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.okhttplib.OkHttpUtil;
import com.okhttplib.annotation.CacheType;
import com.okhttplib.annotation.Encoding;
import com.okhttplib.cookie.PersistentCookieJar;
import com.okhttplib.cookie.cache.SetCookieCache;
import com.okhttplib.cookie.persistence.SharedPrefsCookiePersistor;

import java.io.File;

/**
 * Description : 采用占位方式初始化第三方组件
 * （onCreate比Application的onCreate先执行）
 */
public class AlensProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        initOkHttp(this.getContext());
        return false;
    }

    /**
     * 初始化网络请求框架
     */
    void initOkHttp(Context context){
        OkHttpUtil.init(context)
                .setConnectTimeout(30)
                .setWriteTimeout(30)
                .setReadTimeout(30)
                .setMaxCacheSize(10 * 1024 * 1024)
                .setCacheType(CacheType.FORCE_NETWORK)
                .setHttpLogTAG("OkHttpLog")
                .setIsGzip(false)
                .setShowHttpLog(false)
                .setShowLifecycleLog(true)
                .setRetryOnConnectionFailure(false)
                .setCachedDir(new File(Config.getCacheDir()))
                .setDownloadFileDir(Config.getDownloadDir())
                .setResponseEncoding(Encoding.UTF_8)
                .setRequestEncoding(Encoding.UTF_8)
//                .setHttpsCertificate("12306.cer")
                .addResultInterceptor(HttpInterceptor.ResultInterceptor)
                .addExceptionInterceptor(HttpInterceptor.ExceptionInterceptor)//请求链路异常拦截器
                .setCookieJar(new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context)))//持久化cookie
                .build();
    }



    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
