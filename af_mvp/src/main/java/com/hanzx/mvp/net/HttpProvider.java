package com.hanzx.mvp.net;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @author : Hanzhx
 * @date : 2017/10/18 16:45
 * @email : iHanzhx@gmail.com
 */

public interface HttpProvider {
    /**
     * 配置拦截器
     *
     * @return
     */
    Interceptor[] configInterceptors();

    /**
     * 配置请求信息
     *
     * @return
     */
    void configHttps(OkHttpClient.Builder builder);

    /**
     * 配置Cookie
     *
     * @return
     */
    CookieJar configCookie();

    /**
     * 配置请求处理器
     *
     * @return
     */
    RequestHandler configHandler();

    /**
     * 配置连接超时时间
     *
     * @return
     */
    long configConnectTimeOutSecs();

    /**
     * 配置读取超时时间
     *
     * @return
     */
    long configReadTimeOutSecs();

    /**
     * 配置写入超时时间
     *
     * @return
     */
    long configWriteTimeOutSecs();

    /**
     * 配置日志开关
     *
     * @return
     */
    boolean configLogEnable();
}
