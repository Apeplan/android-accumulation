package com.hanzx.mvp.frame;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * describe:
 *
 * @author Hanzx
 * @date 2017/8/27
 * @email hanzx1024@gmail.com
 */

public abstract class AbsPresenter<T extends BaseView> implements UIControl {
    protected T mView;

    private CompositeSubscription mCompositeSubscription;

    public AbsPresenter(@NonNull T view) {
        mView = view;
    }

    public T getView() {
        return mView;
    }

    public final boolean isViewAttached() {
        return mView != null;
    }

    public Context getContext() {
        if (isViewAttached()) {
            if (mView instanceof Activity) {
                return (Context) mView;
            } else if (mView instanceof Fragment) {
                return ((Fragment) mView).getActivity();
            }
            return mView.getContext();
        }
        return null;
    }

    public final String getString(@StringRes int resId) {
        return getContext().getString(resId);
    }

    public final String getString(@StringRes int resId, Object... args) {
        return getContext().getString(resId, args);
    }

    public final void addSubscribe(Subscription subscription) {
        if (null == mCompositeSubscription) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public final void unSubscribe() {
        if (null != mCompositeSubscription) {
            mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void showLoading() {
        if (mView instanceof UIControl) {
            ((UIControl) mView).showLoading();
        }
    }

    @Override
    public void showError(Exception e) {
        if (mView instanceof UIControl) {
            ((UIControl) mView).showError(e);
        }
    }

    @Override
    public void showNetException(String msg) {
        if (mView instanceof UIControl) {
            ((UIControl) mView).showNetException(msg);
        }
    }

    @Override
    public void hideLoading() {
        if (mView instanceof UIControl) {
            ((UIControl) mView).hideLoading();
        }
    }

    @Override
    public void toast(@NonNull String msg) {
        if (mView instanceof UIControl) {
            ((UIControl) mView).toast(msg);
        }
    }

    @Override
    public void toast(int resId) {
        if (mView instanceof UIControl) {
            ((UIControl) mView).toast(resId);
        }
    }
}
