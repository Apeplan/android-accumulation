package com.hanzx.statelayout;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanzx.statelayout.state.ColorProgressBar;
import com.hanzx.statelayout.state.StateCoverHelperImpl;
import com.hanzx.statelayout.state.StateViewHelper;

/**
 * 页面状态切换控制类：网络异常、出错了、空、加载中
 * <p>
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2015/8/28 10:46
 */

public class StateViewControl {
    /**
     * 状态页控制帮助类
     */
    private StateViewHelper helper;

    public StateViewControl(View view) {
        this(new StateCoverHelperImpl(view));
    }

    public StateViewControl(StateViewHelper helper) {
        super();
        this.helper = helper;
    }

    /**
     * 显示网络异常的 View
     *
     * @param onClickListener 按钮的点击事件
     */
    public void showStateNetworkError(View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        textView.setText(R.string.state_network_exception);

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.state_network_none);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        } else {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        helper.showLayout(layout);
    }

    /**
     * 显示出错了的 View
     *
     * @param errorMsg        错误提示语,传null或者""将会使用默认提示语
     * @param onClickListener 按钮点击事件
     */
    public void showStateError(String errorMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(errorMsg)) {
            textView.setText(errorMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string
                    .state_error_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.state_crazy_error);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        } else {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        helper.showLayout(layout);
    }

    /**
     * 显示没有数据的 View
     *
     * @param emptyMsg        数据为空提示语,传null或者""将会使用默认提示语
     * @param onClickListener 按钮点击事件
     */
    public void showStateEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        } else {
            textView.setText(helper.getContext().getResources().getString(R.string
                    .state_empty_msg));
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        imageView.setImageResource(R.drawable.state_empty);

        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        } else {
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        helper.showLayout(layout);
    }

    /**
     * 显示加载中的 View
     *
     * @param msg 加载提示语,传null或者""将会使用默认提示语
     */
    public void showStateLoading(String msg) {
        View layout = helper.inflate(R.layout.loading);
        ColorProgressBar loading = (ColorProgressBar) layout.findViewById(R.id.loading_progress);
        if (!TextUtils.isEmpty(msg)) {
            TextView textView = (TextView) layout.findViewById(R.id.loading_msg);
            textView.setText(msg);
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        helper.showLayout(layout);
    }

    /**
     * 重置，显示应用页面本来的 View
     */
    public void restore() {
        helper.restoreView();
    }
}
