package com.hanzx.mvp.frame;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

/**
 * describe: 更新界面相关的方法
 *
 * @author Hanzhx
 * @date 2017/8/27
 * @email iHanzhx@gmail.com
 */

public interface UIControl {
    /**
     * 正在加载
     */
    void showLoading();

    /**
     * 出错了
     *
     * @param e 异常信息
     */
    void showError(Exception e);

    /**
     * 网络错误，网络异常
     *
     * @param msg 网络异常提示语
     */
    void showNetException(String msg);

    /**
     * 隐藏加载
     */
    void hideLoading();

    /**
     * 吐司
     *
     * @param msg 要显示的文本
     */
    void toast(@NonNull String msg);

    /**
     * 吐司
     *
     * @param resId 文本资源id
     */
    void toast(@StringRes int resId);

}
