package com.hanzx.statelayout.state;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

/**
 * Created by: iHanzhx
 * Created on: 2015/10/25 13:44
 * Email: iHanzhx@gmail.com
 */

public class StateCoverHelperImpl implements StateViewHelper {
    private StateReplaceHelperImpl helper;
    private View view;

    public StateCoverHelperImpl(View view) {
        super();
        if (null == view) return;
        this.view = view;
        ViewGroup group = (ViewGroup) view.getParent();
        LayoutParams layoutParams = view.getLayoutParams();
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        group.removeView(view);
        group.addView(frameLayout, layoutParams);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams
                .MATCH_PARENT);
        View floatView = new View(view.getContext());
        frameLayout.addView(view, params);
        frameLayout.addView(floatView, params);

        helper = new StateReplaceHelperImpl(floatView);
    }

    @Override
    public View getCurrentLayout() {
        return helper.getCurrentLayout();
    }

    @Override
    public void restoreView() {
        helper.restoreView();
    }

    @Override
    public void showLayout(View view) {
        helper.showLayout(view);
    }

    @Override
    public View inflate(int layoutId) {
        return helper.inflate(layoutId);
    }

    @Override
    public Context getContext() {
        return helper.getContext();
    }

    @Override
    public View getView() {
        return view;
    }
}
