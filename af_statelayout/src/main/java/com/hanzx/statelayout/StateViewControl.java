package com.hanzx.statelayout;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hanzx.statelayout.state.ColorProgressBar;
import com.hanzx.statelayout.state.StateCoverHelperImpl;
import com.hanzx.statelayout.state.StateViewHelper;

/**
 * Created by: Simon
 * Email: simon.han0220@gmail.com
 * Created on: 2015/8/28 10:46
 */

public class StateViewControl {
    private StateViewHelper helper;

    public StateViewControl(View view) {
        this(new StateCoverHelperImpl(view));
    }

    public StateViewControl(StateViewHelper helper) {
        super();
        this.helper = helper;
    }

    public void showStateNetworkError(View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.message);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        textView.setText(R.string.state_no_network_msg);

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

    public void restore() {
        helper.restoreView();
    }
}
