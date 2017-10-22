package com.hanzx.statelayout.state;

import android.content.Context;
import android.view.View;

/**
 * Created by: Hanzhx
 * Created on: 2015/8/28 10:42
 * Email: iHanzhx@gmail.com
 */

public interface StateViewHelper {
    View getCurrentLayout();

    void restoreView();

    void showLayout(View view);

    View inflate(int layoutId);

    Context getContext();

    View getView();
}
