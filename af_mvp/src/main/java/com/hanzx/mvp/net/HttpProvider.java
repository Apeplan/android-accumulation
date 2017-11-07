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
     * @return Interceptor 数组
     */
    Interceptor[] configInterceptors();

    /**
     * 配置请求信息
     *
     * @param builder OkHttpClient 构建信息
     */
    void configHttps(OkHttpClient.Builder builder);

    /**
     * 配置Cookie
     *
     * @return CookieJar
     */
    CookieJar configCookie();

    /**
     * 配置请求处理器
     *
     * @return 请求处理器
     */
    RequestHandler configHandler();

    /**
     * 配置连接超时时间
     *
     * @return 连接超时时间
     */
    long configConnectTimeOutSecs();

    /**
     * 配置读取超时时间
     *
     * @return 读取超时时间
     */
    long configReadTimeOutSecs();

    /**
     * 配置写入超时时间
     *
     * @return 写入超时时间
     */
    long configWriteTimeOutSecs();

    /**
     * 配置日志开关
     *
     * @return 日志是否打开
     */
    boolean configLogEnable();
}
