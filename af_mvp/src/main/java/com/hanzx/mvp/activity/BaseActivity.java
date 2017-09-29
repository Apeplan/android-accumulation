package com.hanzx.mvp.activity;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * describe:
 *
 * @author Hanzx
 * @date 2017/8/27
 * @email hanzx1024@gmail.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // 防止重建时fragment恢复
        if (null != savedInstanceState) {
            String FRAGMENTS_TAG = "android:support:fragments";
            savedInstanceState.remove(FRAGMENTS_TAG);
        }
        super.onCreate(savedInstanceState);
        beforeSetContentView();
        setContentView(getContentViewLayoutId());
        afterSetContentView();
        onInstanceStateRestore(savedInstanceState);

        if (isDefCallSequence()) {
            parseBundle(getIntent().getExtras());
            bindViews();
            initObjs();
            initData();
            setListener();
        }
    }

    /**
     * 返回页面布局资源id
     *
     * @return 布局ResId
     */
    protected abstract int getContentViewLayoutId();

    /**
     * 绑定View控件 {@link AppCompatActivity#findViewById(int)}
     */
    protected abstract void bindViews();

    /**
     * 初始化一些非View控件的操作，如果Presenter、ToolBar
     */
    protected abstract void initObjs();

    /**
     * 数据初始化
     */
    protected abstract void initData();

    /**
     * 设置点击事件
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
     * 页面状态恢复工作 {@link AppCompatActivity#onRestoreInstanceState(Bundle)}
     *
     * @param savedInstanceState Bundle
     */
    protected void onInstanceStateRestore(Bundle savedInstanceState) {
    }

    /**
     * 是否使用基类方法默认调用顺序
     *
     * @return true 使用默认调用顺序
     */
    protected boolean isDefCallSequence() {
        return true;
    }

    /**
     * 解析传过来的参数
     *
     * @param extras Bundle
     */
    protected void parseBundle(Bundle extras) {
    }

    /**
     * 获取Activity对象,返回当前的Activity实例
     *
     * @return 返回当前的Activity实例
     */
    public Activity getActivity() {
        return this;
    }

    /**
     * 检查权限是否已经授权
     *
     * @param permissionGroup 要申请的权限
     * @return true已授权, false有未授权的权限
     */
    public final boolean checkPermissionsIsGranted(String... permissionGroup) {
        if (permissionGroup != null) {
            for (String permission : permissionGroup) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager
                        .PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
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

}
