package com.alens.base.http;

import com.alens.base.http.impl.OkHttp;

/**
 * Description : 采用代理方式方便替换网络底层库
 */
public class HttpProxy implements HttpInterface{

    private HttpInterface httpInterface;
    private static HttpProxy httpProxy;

    public static HttpProxy get(){
        if(httpProxy == null){
            synchronized (HttpProxy.class){
                if(httpProxy == null){
                    httpProxy = new HttpProxy();
                }
            }
        }
        return httpProxy;
    }

    private HttpProxy() {
        httpInterface = new HttpProxyHandler().getProxy(new OkHttp());   //okHttp
//        httpInterface = new HttpProxyHandler().getProxy(new VolleyHttp());//volley
    }

    @Override
    public HttpEntity doGetSync(HttpEntity httpEntity) {
        return httpInterface.doGetSync(httpEntity);
    }

    @Override
    public void doGetAsync(HttpEntity httpEntity, HttpCallback callback) {
        httpInterface.doGetAsync(httpEntity,callback);
    }

    @Override
    public HttpEntity doPostSync(HttpEntity httpEntity) {
        return httpInterface.doPostSync(httpEntity);
    }

    @Override
    public void doPostAsync(HttpEntity httpEntity, HttpCallback callback) {
        httpInterface.doPostAsync(httpEntity,callback);
    }


}
