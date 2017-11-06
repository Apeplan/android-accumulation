package com.hanzx.mvp.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求处理器
 * <p>
 * @author : Hanzx
 * @date : 2017/10/18 16:56
 * @email : iHanzhx@gmail.com
 */

public interface RequestHandler {
    /**
     * 请求前调用
     */
    Request onBeforeRequest(Request request, Interceptor.Chain chain);

    /**
     * 请求之后调用
     */
    Response onAfterRequest(Response response, Interceptor.Chain chain) throws IOException;
}
