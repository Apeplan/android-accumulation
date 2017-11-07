package com.hanzx.mvp.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 请求处理器
 * <p>
 *
 * @author : Hanzhx
 * @date : 2017/10/18 16:45
 * @email : iHanzhx@gmail.com
 */

public interface RequestHandler {
    /**
     * 请求前调用
     *
     * @param request Http请求
     * @param chain   Interceptor.Chain
     * @return 网络请求 Request
     */
    Request onBeforeRequest(Request request, Interceptor.Chain chain);

    /**
     * 请求之后调用
     *
     * @param response Http 响应
     * @param chain    Interceptor.Chain
     * @return 网络响应 Response
     * @throws IOException
     */
    Response onAfterRequest(Response response, Interceptor.Chain chain) throws IOException;
}
