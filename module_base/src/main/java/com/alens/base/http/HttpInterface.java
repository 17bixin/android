package com.alens.base.http;

/**
 * Description : 网络请求接口
 */
public interface HttpInterface {

    //同步get
    HttpEntity doGetSync(HttpEntity httpEntity);

    //异步get
    void doGetAsync(HttpEntity httpEntity, HttpCallback callback);

    //同步post
    HttpEntity doPostSync(HttpEntity httpEntity);

    //异步post
    void doPostAsync(HttpEntity httpEntity, HttpCallback callback);


}
