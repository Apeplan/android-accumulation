package com.hanzx.mvp.net;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author : Hanzx
 * @date : 2017/10/18 16:42
 * @email : iHanzhx@gmail.com
 */

public class HttpMgr {
    /**
     * 网络连接超时时间
     */
    private final long mConnetTimeOut = 10 * 1000L;
    /**
     * 读取超时时间
     */
    private final long mReadTimeOut = 10 * 1000L;
    /**
     * 网络配置提供者
     */
    private HttpProvider mHttpProvider;
    /**
     * 提供者集合
     */
    private Map<String, HttpProvider> mHttpProviderMap = new HashMap<>();
    /**
     * Retrofit 集合
     */
    private Map<String, Retrofit> mRetrofitMap = new HashMap<>();
    /**
     * HttpClient 集合
     */
    private Map<String, OkHttpClient> mHttpClientMap = new HashMap<>();

    private static class SingletonHolder {
        private static final HttpMgr INSTANCE = new HttpMgr();
    }

    private HttpMgr() {
    }

    /**
     * 获取实例对象
     *
     * @return HttpMgr 对象
     */
    public static HttpMgr getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取baseUrl 和Retrofit service 类
     *
     * @param baseUrl 基础连接
     * @param service Retrofit Service
     * @param <T>     Retrofit Service 类型
     *
     * @return Retrofit Service 对象
     */
    public <T> T get(String baseUrl, Class<T> service) {
        return getInstance().getRetrofit(baseUrl).create(service);
    }

    /**
     * 注册网络配置提供者
     *
     * @param provider 网络配置提供者
     */
    public void registerProvider(HttpProvider provider) {
        this.mHttpProvider = provider;
    }

    public void registerProvider(String baseUrl, HttpProvider provider) {
        getInstance().mHttpProviderMap.put(baseUrl, provider);
    }

    /**
     * 获取通用的网络配置提供者
     *
     * @return
     */
    public HttpProvider getHttpProvider() {
        return mHttpProvider;
    }

    /**
     * 清除缓存
     */
    public void clearCache() {
        getInstance().mRetrofitMap.clear();
        getInstance().mHttpClientMap.clear();
    }

    public Retrofit getRetrofit(String baseUrl) {
        return getRetrofit(baseUrl, null);
    }

    /**
     * 获取 Retrofit
     *
     * @param baseUrl  基础链接
     * @param provider 网络配置提供者
     *
     * @return Retrofit
     */
    public Retrofit getRetrofit(String baseUrl, HttpProvider provider) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (null != mRetrofitMap.get(baseUrl)) {
            return mRetrofitMap.get(baseUrl);
        }

        if (null == provider) {
            provider = mHttpProviderMap.get(baseUrl);
            if (null == provider) {
                provider = mHttpProvider;
            }
        }
        checkProvider(provider);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(baseUrl, provider))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.build();
        mRetrofitMap.put(baseUrl, retrofit);
        mHttpProviderMap.put(baseUrl, provider);

        return retrofit;
    }

    /**
     * 获取 OkHttpClient
     * @param baseUrl 基础
     * @param provider 链接
     * @return OkHttpClient 对象
     */
    private OkHttpClient getClient(String baseUrl, HttpProvider provider) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (null != mHttpClientMap.get(baseUrl)) {
            return mHttpClientMap.get(baseUrl);
        }
        checkProvider(provider);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(provider.configConnectTimeOutSecs() != 0
                ? provider.configConnectTimeOutSecs()
                : mConnetTimeOut, TimeUnit.SECONDS);
        builder.readTimeout(provider.configReadTimeOutSecs() != 0
                ? provider.configReadTimeOutSecs() : mReadTimeOut, TimeUnit.SECONDS);

        builder.writeTimeout(provider.configWriteTimeOutSecs() != 0
                ? provider.configReadTimeOutSecs() : mReadTimeOut, TimeUnit.SECONDS);
        CookieJar cookieJar = provider.configCookie();
        if (null != cookieJar) {
            builder.cookieJar(cookieJar);
        }
        provider.configHttps(builder);

        RequestHandler handler = provider.configHandler();
        if (null != handler) {
            builder.addInterceptor(new HttpInterceptor(handler));
        }

        Interceptor[] interceptors = provider.configInterceptors();
        if (!empty(interceptors)) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        if (provider.configLogEnable()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        OkHttpClient client = builder.build();
        mHttpClientMap.put(baseUrl, client);
        mHttpProviderMap.put(baseUrl, provider);

        return client;
    }

    private boolean empty(Interceptor[] interceptors) {
        return interceptors == null || interceptors.length == 0;
    }

    /**
     * 检查 HttpProvide 是否为空
     *
     * @param provider HttpProvider
     */
    private void checkProvider(HttpProvider provider) {
        if (provider == null) {
            throw new IllegalStateException("must register provider first");
        }
    }

    /**
     * 获取 Retrofit
     *
     * @return Retrofit 集合
     */
    public Map<String, Retrofit> getRetrofitMap() {
        return mRetrofitMap;
    }

    /**
     * 获取 OkHttpClient
     *
     * @return OkHttpClient 集合
     */
    public Map<String, OkHttpClient> getClientMap() {
        return mHttpClientMap;
    }
}
