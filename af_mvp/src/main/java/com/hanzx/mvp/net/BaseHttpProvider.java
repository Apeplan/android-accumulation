package com.hanzx.mvp.net;

import java.io.IOException;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求配置类的默认实现
 * <p>
 * @author : Hanzx
 * @date : 2017/10/18 17:04
 * @email : iHanzhx@gmail.com
 */

public class BaseHttpProvider implements HttpProvider {
    /**
     * 连接超时时间
     */
    private static final long CONNECT_TIME_OUT = 15;
    /**
     * 读取超时时间
     */
    private static final long READ_TIME_OUT = 180;
    /**
     * 写入超时时间
     */
    private static final long WRITE_TIME_OUT = 30;

    @Override
    public Interceptor[] configInterceptors() {
        return null;
    }

    @Override
    public void configHttps(OkHttpClient.Builder builder) {

    }

    @Override
    public CookieJar configCookie() {
        return null;
    }

    @Override
    public RequestHandler configHandler() {
        return new BaseRequestHandler();
    }

    @Override
    public long configConnectTimeOutSecs() {
        return CONNECT_TIME_OUT;
    }

    @Override
    public long configReadTimeOutSecs() {
        return READ_TIME_OUT;
    }

    @Override
    public long configWriteTimeOutSecs() {
        return WRITE_TIME_OUT;
    }

    @Override
    public boolean configLogEnable() {
        return true;
    }

    private class BaseRequestHandler implements RequestHandler {

        @Override
        public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
            return chain.request().newBuilder()
                    .addHeader("X-Auth-Token", "")
                    .addHeader("Authorization", "")
                    .build();
        }

        @Override
        public Response onAfterRequest(Response response, Interceptor.Chain chain) throws
                IOException {
            int code = response.code();
            if (401 == code) {
                throw new ApiException(code, "登录已过期,请重新登录!");
            } else if (403 == code) {
                throw new ApiException(code, "禁止访问!");
            } else if (404 == code) {
                throw new ApiException(code, "链接错误");
            } else if (503 == code) {
                throw new ApiException(code, "服务器升级中!");
            } else if (500 == code) {
                throw new ApiException(code, "服务器内部错误!");
            }
            return response;
        }
    }
}
