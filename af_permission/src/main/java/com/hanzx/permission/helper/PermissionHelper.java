package com.hanzx.permission.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by: Hanzhx
 * Created on: 2017/9/3 11:41
 * Email: iHanzhx@gmail.com
 */
@RestrictTo(Scope.LIBRARY)
public abstract class PermissionHelper<T> {
    private T mHost;

    @NonNull
    public static PermissionHelper newInstance(Activity host) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new LowApiPermissionsHelper(host);
        }

        if (host instanceof AppCompatActivity) {
            return new AppCompatActivityPermissionHelper((AppCompatActivity) host);
        }
        return new ActivityPermissionHelper(host);
    }

    @NonNull
    public static PermissionHelper newInstance(Fragment host) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new LowApiPermissionsHelper(host);
        }

        return new SupportFragmentPermissionHelper(host);
    }

    @NonNull
    public static PermissionHelper newInstance(android.app.Fragment host) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return new LowApiPermissionsHelper(host);
        }

        return new FrameworkFragmentPermissionHelper(host);
    }

    public PermissionHelper(@NonNull T host) {
        mHost = host;
    }

    /**
     * 是否显示权限申请理由
     *
     * @param perms 申请的权限
     *
     * @return
     */
    public boolean shouldShowRationale(@NonNull String... perms) {
        for (String perm : perms) {
            if (shouldShowRequestPermissionRationale(perm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限
     *
     * @param rationale      申请权限的理由
     * @param positiveButton 确定按钮文本String资源id
     * @param negativeButton 取消按钮文本String资源id
     * @param requestCode    权限请求码
     * @param perms          申请的权限
     */
    public void requestPermissions(@NonNull String rationale,
                                   @StringRes int positiveButton,
                                   @StringRes int negativeButton,
                                   int requestCode,
                                   @NonNull String... perms) {
        if (shouldShowRationale(perms)) {
            showRequestPermissionRationale(rationale, positiveButton, negativeButton,
                    requestCode, perms);
        } else {
            directRequestPermissions(requestCode, perms);
        }
    }

    /**
     * 某些权限是否被拒绝
     *
     * @param perms 申请的权限
     *
     * @return true 被拒绝
     */
    public boolean somePermissionPermanentlyDenied(@NonNull List<String> perms) {
        for (String perm : perms) {
            if (permissionPermanentlyDenied(perm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 权限是否永久拒绝
     *
     * @param perm 申请的权限
     *
     * @return true 永久拒绝
     */
    public boolean permissionPermanentlyDenied(@NonNull String perm) {
        return !shouldShowRequestPermissionRationale(perm);
    }

    /**
     * 某些权限被拒绝
     *
     * @param perms 申请的权限
     *
     * @return true 被拒绝
     */
    public boolean somePermissionDenied(@NonNull String... perms) {
        return shouldShowRationale(perms);
    }

    @NonNull
    public T getHost() {
        return mHost;
    }

    /**
     * 直接请求权限
     *
     * @param requestCode 权限请求码
     * @param perms       请求的权限
     */
    public abstract void directRequestPermissions(int requestCode, @NonNull String... perms);

    /**
     * 是否显示申请权限的理由
     *
     * @param perm 要申请的权限
     */
    public abstract boolean shouldShowRequestPermissionRationale(@NonNull String perm);
    /**
     * 显示要申请权限的理由
     *
     * @param rationale      申请权限的理由
     * @param positiveButton 确定按钮文本String资源id
     * @param negativeButton 取消按钮文本String资源id
     * @param requestCode    权限请求码
     * @param perms          申请的权限
     */
    public abstract void showRequestPermissionRationale(@NonNull String rationale,
                                                        @StringRes int positiveButton,
                                                        @StringRes int negativeButton,
                                                        int requestCode,
                                                        @NonNull String... perms);

    /**
     * 获取上下文
     *
     * @return Context
     */
    public abstract Context getContext();
}
