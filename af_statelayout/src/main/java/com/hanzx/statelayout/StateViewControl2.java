package com.hanzx.statelayout;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.hanzx.statelayout.state.StateCoverHelperImpl;
import com.hanzx.statelayout.state.StateView;
import com.hanzx.statelayout.state.StateViewHelper;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2015/8/28 10:46
 */

public class StateViewControl2 {
    private StateViewHelper helper;
    private String mBtnText;

    public StateViewControl2(View view) {
        this(new StateCoverHelperImpl(view));
    }

    public StateViewControl2(StateViewHelper helper) {
        super();
        this.helper = helper;
        mBtnText = helper.getContext().getString(R.string.state_retry);
    }

    public void showStateNetworkError(View.OnClickListener onClickListener) {
        StateView stateView = (StateView) helper.inflate(R.layout.state_layout);
        stateView.setBackgroundColor(Color.WHITE);
        stateView.setStateIcon(R.drawable.state_network_none);
        String msg = helper.getContext().getString(R.string.state_network_exception);
        stateView.show(false, msg, mBtnText, onClickListener);
        helper.showLayout(stateView);
    }

    public void showStateError(String errorMsg, View.OnClickListener onClickListener) {
        StateView stateView = (StateView) helper.inflate(R.layout.state_layout);
        stateView.setBackgroundColor(Color.WHITE);
        stateView.setStateIcon(R.drawable.state_crazy_error);
        if (TextUtils.isEmpty(errorMsg)) {
            errorMsg = helper.getContext().getString(R.string.state_error_msg);
        }
        stateView.show(false, errorMsg, mBtnText, onClickListener);
        helper.showLayout(stateView);
    }

    public void showStateEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        StateView stateView = (StateView) helper.inflate(R.layout.state_layout);
        stateView.setBackgroundColor(Color.WHITE);
        stateView.setStateIcon(R.drawable.state_empty);
        if (TextUtils.isEmpty(emptyMsg)) {
            emptyMsg = helper.getContext().getString(R.string.state_empty_msg);
        }
        stateView.show(false, emptyMsg, mBtnText, onClickListener);
        helper.showLayout(stateView);
    }

    public void showStateLoading(String msg) {
        StateView stateView = (StateView) helper.inflate(R.layout.state_layout);
        stateView.setBackgroundColor(Color.TRANSPARENT);
        if (TextUtils.isEmpty(msg)) {
            msg = helper.getContext().getString(R.string.state_loading_message);
        }
        stateView.setStateIcon(0);
        stateView.show(true, msg);
        helper.showLayout(stateView);
    }

    public void restore() {
        helper.restoreView();
    }
}
