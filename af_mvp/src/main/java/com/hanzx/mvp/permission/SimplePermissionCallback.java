package com.hanzx.mvp.permission;

import android.support.annotation.Nullable;

/**
 * describe:
 *
 * @author Hanzx
 * @date 2017/8/27
 * @email hanzx1024@gmail.com
 */

public abstract class SimplePermissionCallback implements PermissionCallback {
    @Override
    public void onGranted(@Nullable String... permissions) {

    }

    @Override
    public void onDenied(@Nullable String... permissions) {

    }
}
