package com.hanzx.permission.helper;

import android.app.FragmentManager;
import android.support.annotation.NonNull;

import com.hanzx.permission.dialog.RationaleDialogFragment;

/**
 * Created by: Hanzx
 * Created on: 2017/9/3 14:40
 * Email: hanzhanxi@gmail.com
 */

public abstract class BaseFrameworkPermissionsHelper<T> extends PermissionHelper<T> {

    public BaseFrameworkPermissionsHelper(@NonNull T host) {
        super(host);
    }

    @Override
    public void showRequestPermissionRationale(@NonNull String rationale,
                                               int positiveButton,
                                               int negativeButton,
                                               int requestCode,
                                               @NonNull String... perms) {
        RationaleDialogFragment
                .newInstance(positiveButton, negativeButton, rationale, requestCode, perms)
                .show(getFragmentManager(), RationaleDialogFragment.TAG);
    }

    public abstract FragmentManager getFragmentManager();
}
