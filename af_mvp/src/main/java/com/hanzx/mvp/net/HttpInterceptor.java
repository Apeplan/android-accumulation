package com.hanzx.mvp.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author : Hanzx
 * @date : 2017/11/6 16:40
 * @email : iHanzhx@gmail.com
 */

public class HttpInterceptor implements Interceptor {
    /**
     * 请求处理者
     */
    private RequestHandler mRequestHandler;

    public HttpInterceptor(RequestHandler requestHandler) {
        mRequestHandler = requestHandler;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (null != mRequestHandler) {
            request = mRequestHandler.onBeforeRequest(request, chain);
        }
        Response response = chain.proceed(request);
        if (null != mRequestHandler) {
            Response tmp = mRequestHandler.onAfterRequest(response, chain);
            if (null != tmp) {
                return tmp;
            }
        }
        return response;
    }
}
