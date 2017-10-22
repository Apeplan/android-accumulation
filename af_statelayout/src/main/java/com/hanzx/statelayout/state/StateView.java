package com.hanzx.statelayout.state;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanzx.statelayout.R;

/**
 * 状态页
 *
 * Created by: iHanzhx
 * Created on: 2017/8/27
 * Email: iHanzhx@gmail.com
 */

public class StateView extends FrameLayout {
    /**
     * 加载进度条
     */
    private ColorProgressBar mProgressBar;
    /**
     * 图片
     */
    private ImageView mStateMsgIcon;
    /**
     * 提示语标题
     */
    private TextView mTitleTextView;
    /**
     * 重试按钮
     */
    protected Button mButton;

    public StateView(Context context) {
        super(context);
        init(null, 0);
    }

    public StateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public StateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // 获取属性配置
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.StateView,
                defStyle, 0);
        Boolean isLoading = a.getBoolean(R.styleable.StateView_sv_show_loading, false);
        String message = a.getString(R.styleable.StateView_sv_message_text);
        String btnText = a.getString(R.styleable.StateView_sv_btn_text);
        a.recycle();

        bindViews();
        // 根据配置显示默认状态
        show(isLoading, message, btnText, null);
    }

    /**
     * UI 初始化绑定
     */
    private void bindViews() {
        LayoutInflater.from(getContext()).inflate(R.layout.sample_state_view, this, true);
        mProgressBar = (ColorProgressBar) findViewById(R.id.state_view_loading);
        mStateMsgIcon = (ImageView) findViewById(R.id.state_view_message_icon);
        mTitleTextView = (TextView) findViewById(R.id.state_view_message);
        mButton = (Button) findViewById(R.id.state_view_button);
    }

    /**
     * 显示StateView状态
     *
     * @param loading          是否要显示loading
     * @param title            标题的文字，不需要则传null或者""
     * @param btnText          按钮的文字，不需要按钮则传null或者""
     * @param btnClickListener 按钮的onClick监听，不需要则传null或者""
     */
    public void show(boolean loading, String title, String btnText, OnClickListener
            btnClickListener) {
        setLoadingShowing(loading);
        setMessageText(title);
        setRetryButton(btnText, btnClickListener);
        show();
    }

    /**
     * 显示StateView并且只显示loading的情况，此时title、subtitle、button都被隐藏
     *
     * @param loading 是否显示loading
     */
    public void show(boolean loading, String msg) {
        setLoadingShowing(loading);
        setMessageText(msg);
        setRetryButton(null, null);
        show();
    }

    /**
     * 用于显示纯文本的简单调用方法，此时loading、button均被隐藏
     *
     * @param msg 提示文字，不需要则传null或者""
     */
    public void show(String msg) {
        setLoadingShowing(false);
        setMessageText(msg);
        setRetryButton(null, null);
        show();
    }

    /**
     * 显示StateView
     */
    private void show() {
        setVisibility(VISIBLE);
    }

    /**
     * 隐藏StateView
     */
    public void hide() {
        setVisibility(GONE);
        setLoadingShowing(false);
        setMessageText(null);
        setRetryButton(null, null);
    }

    /**
     * 状态页是否正在显示
     *
     * @return true 正在显示
     */
    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    /**
     * 当前状态是否为加载中
     *
     * @return true 正在加载中
     */
    public boolean isLoading() {
        return getVisibility() == VISIBLE && mProgressBar.getVisibility() == VISIBLE;
    }

    /**
     * 设置加载中状态的显示、隐藏
     *
     * @param show true 显示
     */
    private void setLoadingShowing(boolean show) {
        mProgressBar.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * 设置状态对应的图片资源
     *
     * @param resImageId 图片资源id
     */
    public void setStateIcon(@DrawableRes int resImageId) {
        if (resImageId == 0) {
            mStateMsgIcon.setVisibility(GONE);
        }
        mStateMsgIcon.setVisibility(VISIBLE);
        mStateMsgIcon.setImageResource(resImageId);
    }

    /**
     * 设置提示语标题文案
     *
     * @param msg 提示文案
     */
    private void setMessageText(String msg) {
        mTitleTextView.setText(msg);
        mTitleTextView.setVisibility(TextUtils.isEmpty(msg) ? GONE : VISIBLE);
    }

    /**
     * 设置提示语标题颜色
     *
     * @param color 颜色
     */
    public void setMessageColor(int color) {
        mTitleTextView.setTextColor(color);
    }

    /**
     * 设置提示语文字大小
     *
     * @param dp 大小，dp单位
     */
    public void setMessageSize(int dp) {
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
    }

    /**
     * 设置重试按钮
     *
     * @param text          按钮文案
     * @param clickListener 按钮点击事件
     */
    private void setRetryButton(String text, OnClickListener clickListener) {
        mButton.setText(text);
        mButton.setVisibility(TextUtils.isEmpty(text) ? GONE : VISIBLE);
        mButton.setOnClickListener(clickListener);
    }

    /**
     * dp ת pixels :将dip值转换为像素值
     */
    public static int dip2pixels(Context ctx, float dpValue) {
        final float scale = ctx.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
