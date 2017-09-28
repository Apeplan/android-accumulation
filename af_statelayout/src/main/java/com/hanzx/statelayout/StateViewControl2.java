package com.hanzx.statelayout;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.hanzx.statelayout.state.StateCoverHelperImpl;
import com.hanzx.statelayout.state.StateView;
import com.hanzx.statelayout.state.StateViewHelper;

/**
 * 页面状态切换控制类：网络异常、出错了、空、加载中
 * <p>
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2015/8/28 10:46
 */

public class StateViewControl2 {
    /**
     * 状态页控制帮助类
     */
    private StateViewHelper helper;
    /**
     * 按钮文字
     */
    private String mBtnText;
    /**
     * 网络异常提示语
     */
    private String mNetExceptionTrip;
    /**
     * 除网络问题之外的错误提示语
     */
    private String mErrorTrip;
    /**
     * 空数据时提示语
     */
    private String mEmptyTrip;
    /**
     * 正在加载提示语
     */
    private String mLoadingTrip;

    public StateViewControl2(View view) {
        this(new StateCoverHelperImpl(view));
    }

    public StateViewControl2(StateViewHelper helper) {
        super();
        this.helper = helper;

        initText();
    }

    /**
     * 初始化默认提示语和按钮文字
     */
    private void initText() {
        mBtnText = helper.getContext().getString(R.string.state_retry);
        mNetExceptionTrip = helper.getContext().getString(R.string.state_network_exception);
        mErrorTrip = helper.getContext().getString(R.string.state_error_msg);
        mEmptyTrip = helper.getContext().getString(R.string.state_empty_msg);
        mLoadingTrip = helper.getContext().getString(R.string.state_loading_message);
    }

    /**
     * 显示网络异常的 View
     *
     * @param onClickListener 按钮的点击事件
     */
    public void showStateNetException(View.OnClickListener onClickListener) {
        StateView stateView = (StateView) helper.inflate(R.layout.state_layout);
        stateView.setBackgroundColor(Color.WHITE);
        stateView.setStateIcon(R.drawable.state_network_none);
        stateView.show(false, mNetExceptionTrip, mBtnText, onClickListener);
        helper.showLayout(stateView);
    }

    /**
     * 显示出错了的 View
     *
     * @param errorMsg        错误提示语,传null或者""将会使用默认提示语
     * @param onClickListener 按钮点击事件
     */
    public void showStateError(String errorMsg, View.OnClickListener onClickListener) {
        StateView stateView = (StateView) helper.inflate(R.layout.state_layout);
        stateView.setBackgroundColor(Color.WHITE);
        stateView.setStateIcon(R.drawable.state_crazy_error);
        if (TextUtils.isEmpty(errorMsg)) {
            errorMsg = mErrorTrip;
        }
        stateView.show(false, errorMsg, mBtnText, onClickListener);
        helper.showLayout(stateView);
    }

    /**
     * 显示没有数据的 View
     *
     * @param emptyMsg        数据为空提示语,传null或者""将会使用默认提示语
     * @param onClickListener 按钮点击事件
     */
    public void showStateEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        StateView stateView = (StateView) helper.inflate(R.layout.state_layout);
        stateView.setBackgroundColor(Color.WHITE);
        stateView.setStateIcon(R.drawable.state_empty);
        if (TextUtils.isEmpty(emptyMsg)) {
            emptyMsg = mEmptyTrip;
        }
        stateView.show(false, emptyMsg, mBtnText, onClickListener);
        helper.showLayout(stateView);
    }

    /**
     * 显示加载中的 View
     *
     * @param msg 加载提示语,传null或者""将会使用默认提示语
     */
    public void showStateLoading(String msg) {
        StateView stateView = (StateView) helper.inflate(R.layout.state_layout);
        stateView.setBackgroundColor(Color.TRANSPARENT);
        if (TextUtils.isEmpty(msg)) {
            msg = mLoadingTrip;
        }
        stateView.setStateIcon(0);
        stateView.show(true, msg);
        helper.showLayout(stateView);
    }

    /**
     * 重置，显示应用页面本来的 View
     */
    public void restore() {
        helper.restoreView();
    }

    /************************* 以下是更改默认提示语和按钮文字的方法 ************************/

    /**
     * 设置按钮的显示文字
     *
     * @param btnText 文字
     */
    public void setBtnText(String btnText) {
        mBtnText = btnText;
    }

    /**
     * 设置网络异常提示语
     *
     * @param netExceptionTrip 提示语
     */
    public void setNetExceptionTrip(String netExceptionTrip) {
        mNetExceptionTrip = netExceptionTrip;
    }

    /**
     * 设置错误提示语
     *
     * @param errorTrip 提示语
     */
    public void setErrorTrip(String errorTrip) {
        mErrorTrip = errorTrip;
    }

    /**
     * 设置空数据时提示语
     *
     * @param emptyTrip 提示语
     */
    public void setEmptyTrip(String emptyTrip) {
        mEmptyTrip = emptyTrip;
    }

    /**
     * 设置加载中的提示语
     *
     * @param loadingTrip 提示语
     */
    public void setLoadingTrip(String loadingTrip) {
        mLoadingTrip = loadingTrip;
    }
}
