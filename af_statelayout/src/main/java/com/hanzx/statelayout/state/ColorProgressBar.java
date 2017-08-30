package com.hanzx.statelayout.state;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.hanzx.statelayout.R;

/**
 * Created by: Simon
 * Created on: 2017/8/15 15:34
 * Email: hanzx1024@gmail.com
 */

public class ColorProgressBar extends ProgressBar {
    public ColorProgressBar(Context context) {
        super(context);
        init(context, null);
    }

    public ColorProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ColorProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ColorProgressBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        int color = context.getResources().getColor(R.color.progress_bar);
        setColor(color);
    }

    public void setColor(int color) {
        getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        this.postInvalidate();
    }

}
