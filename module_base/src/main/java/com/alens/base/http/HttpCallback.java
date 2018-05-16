package com.alens.base.http;

import java.io.IOException;

/**
 * 请求回调接口
 */
public interface HttpCallback {

    /**
     * 请求成功
     */
    void onSuccess(HttpEntity info) throws IOException;

    /**
     * 请求失败
     */
    void onFailure(HttpEntity info) throws IOException;

}
