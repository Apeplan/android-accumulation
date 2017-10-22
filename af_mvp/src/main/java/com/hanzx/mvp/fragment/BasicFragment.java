package com.hanzx.mvp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.BuildConfig;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by: Hanzhx
 * Created on: 2017/9/27 18:42
 * Email: iHanzhx@gmail.com
 */

public abstract class BasicFragment extends Fragment implements View.OnClickListener {
    protected final String TAG = this.getClass().getSimpleName();
    /**
     * 是否输出日志信息
     **/
    private boolean mIsDebug;
    /**
     * 当前Fragment渲染的视图View
     **/
    private View mContextView = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsDebug = BuildConfig.DEBUG;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        mContextView = inflater.inflate(bindLayout(), container, false);
        bindViews(mContextView);
        initObjects();
        initData(getActivity());
        setListener();
        return mContextView;
    }

    /**
     * 绑定布局
     *
     * @return
     */
    public abstract int bindLayout();

    /**
     * 初始化控件
     *
     * @param view
     */
    public abstract void bindViews(final View view);

    /**
     * 初始化一些非View控件的操作，如果Presenter、ToolBar
     */
    protected void initObjects(){}

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
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        if (fastClick()) {
            widgetClick(v);
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T view(View view, int resId) {
        return (T) view.findViewById(resId);
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
