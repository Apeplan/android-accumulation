package com.hanzx.mvp.permission;

import android.support.annotation.Nullable;

/**
 * describe:
 *
 * @author Hanzx
 * @date 2017/8/27
 * @email hanzx1024@gmail.com
 */

public interface PermissionCallback {
    /**
     * 在系统授权框弹出之前
     *
     * @param isShouldShowAlert 是否应该弹出自己的提示框
     * @param permissions 权限列表
     */
    void onBeforeGranted(boolean isShouldShowAlert, String... permissions);

    /**
     * 已授权权限
     *
     * @param permissions 已授权的权限名称
     */
    void onGranted(@Nullable String... permissions);

    /**
     * 拒绝授权的权限
     *
     * @param permissions 拒绝授权的权限名称
     */
    void onDenied(@Nullable String... permissions);
}
