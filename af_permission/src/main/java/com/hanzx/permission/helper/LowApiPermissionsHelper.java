package com.hanzx.permission.helper;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by: Hanzhx
 * Created on: 2017/9/3 12:12
 * Email: iHanzhx@gmail.com
 */

class LowApiPermissionsHelper extends PermissionHelper<Object> {

    public LowApiPermissionsHelper(@NonNull Object host) {
        super(host);
    }

    @Override
    public void directRequestPermissions(int requestCode, @NonNull String... perms) {
        throw new IllegalStateException("Should never be requesting permissions on API < 23!");
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String perm) {
        return false;
    }

    @Override
    public void showRequestPermissionRationale(@NonNull String rationale, int positiveButton, int
            negativeButton, int requestCode, @NonNull String... perms) {
        throw new IllegalStateException("Should never be requesting permissions on API < 23!");
    }

    @Override
    public Context getContext() {
        return null;
    }
}
