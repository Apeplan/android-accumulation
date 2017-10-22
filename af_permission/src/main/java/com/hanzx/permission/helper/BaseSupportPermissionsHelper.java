package com.hanzx.permission.helper;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;

import com.hanzx.permission.dialog.RationaleDialogFragmentCompat;

/**
 * Created by: Hanzhx
 * Created on: 2017/9/3 12:13
 * Email: iHanzhx@gmail.com
 */

public abstract class BaseSupportPermissionsHelper<T> extends PermissionHelper<T> {

    public BaseSupportPermissionsHelper(@NonNull T host) {
        super(host);
    }

    @Override
    public void showRequestPermissionRationale(@NonNull String rationale,
                                               int positiveButton,
                                               int negativeButton,
                                               int requestCode,
                                               @NonNull String... perms) {
        RationaleDialogFragmentCompat
                .newInstance(positiveButton, negativeButton, rationale, requestCode, perms)
                .show(getSupportFragmentManager(), RationaleDialogFragmentCompat.TAG);
    }

    public abstract FragmentManager getSupportFragmentManager();
}
