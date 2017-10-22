package com.hanzx.mvp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hanzx.mvp.BuildConfig;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by: Hanzhx
 * Created on: 2017/9/27 18:26
 * Email: iHanzhx@gmail.com
 */

public abstract class BasicActivity extends AppCompatActivity implements View.OnClickListener {
    protected final String TAG = this.getClass().getSimpleName();
    /**
     * 是否允许全屏
     **/
    private boolean mAllowFullScreen = true;
    /**
     * 是否禁止旋转屏幕
     **/
    private boolean mAllowScreenRotate = false;
    /**
     * 当前Activity渲染的视图View
     **/
    private View mContextView = null;
    /**
     * 是否输出日志信息
     **/
    private boolean mIsDebug;
    /**
     * 订阅
     */
    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 防止重建时fragment恢复
        if (null != savedInstanceState) {
            String FRAGMENTS_TAG = "android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        super.onCreate(savedInstanceState);
        mIsDebug = BuildConfig.DEBUG;
        $Log(TAG + "\tonCreate()");
        try {
            Bundle bundle = getIntent().getExtras();
            initParams(bundle);
            mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);

            if (mAllowFullScreen) {
                this.getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                requestWindowFeature(Window.FEATURE_NO_TITLE);
            }

            beforeSetContentView();

            setContentView(mContextView);

            afterSetContentView();

            if (!mAllowScreenRotate) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            bindViews(mContextView);
            initObjects();
            initData(this);
            setListener();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Bundle参数
     *
     * @param params 参数
     */
    public abstract void initParams(Bundle params);

    /**
     * 绑定XML布局id
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * 绑定控件
     *
     * @param view
     */
    public abstract void bindViews(final View view);

    /**
     * 初始化一些非View控件的操作，如果Presenter、ToolBar
     */
    protected void initObjects() {
    }

    /**
     * 初始化数据，业务操作
     *
     * @param mContext
     */
    public abstract void initData(Context mContext);

    /**
     * 设置点击事件，在{@link #initData(Context)}之后调用
     */
    protected void setListener() {
    }

    /**
     * 设置一些关于本Window相关的设置,详见{@link #requestWindowFeature(int)}方法
     * 在{@link AppCompatActivity#setContentView(int)}之前调用<br>
     */
    protected void beforeSetContentView() {
    }

    /**
     * 基础页面控件的初始化,例如ToolBar等
     * 在{@link AppCompatActivity#setContentView(int)}之前调用<br>
     */
    private void afterSetContentView() {
    }

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick()) {
            widgetClick(v);
        }
    }

    /**
     * 增加到Rxjava的订阅管理器中,统一管理订阅事件,用于销毁时统一销毁订阅事件
     *
     * @param subscription 订阅对象
     */
    public void addSubscribe(Subscription subscription) {
        if (null == mCompositeSubscription) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    /**
     * 解除订阅
     */
    public void unSubscribe() {
        if (null != mCompositeSubscription) {
            mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * 页面跳转
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T view(int resId) {
        return (T) super.findViewById(resId);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onResume() {
        super.onResume();
        $Log(TAG + "--->onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        $Log(TAG + "--->onDestroy()");
    }

    /**
     * 是否允许全屏
     *
     * @param allowFullScreen
     */
    public void setAllowFullScreen(boolean allowFullScreen) {
        this.mAllowFullScreen = allowFullScreen;
    }

    /**
     * 是否允许屏幕旋转
     *
     * @param allowScreenRotate true 允许旋转
     */
    public void setScreenRotate(boolean allowScreenRotate) {
        this.mAllowScreenRotate = allowScreenRotate;
    }

    /**
     * 日志输出
     *
     * @param msg
     */
    protected void $Log(String msg) {
        if (mIsDebug) {
            Log.d(TAG, msg);
        }
    }

    /**
     * 防止快速点击
     *
     * @return
     */
    private boolean fastClick() {
        long lastClick = 0;
        if (System.currentTimeMillis() - lastClick <= 1000) {
            return false;
        }
        lastClick = System.currentTimeMillis();
        return true;
    }
}
