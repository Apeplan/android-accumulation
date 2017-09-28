package com.hanzx.permission.helper;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * Created by: Hanzx
 * Created on: 2017/9/3 12:18
 * Email: hanzhanxi@gmail.com
 */

class FrameworkFragmentPermissionHelper extends BaseFrameworkPermissionsHelper<Fragment> {
    public FrameworkFragmentPermissionHelper(@NonNull Fragment host) {
        super(host);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public FragmentManager getFragmentManager() {
        return getHost().getChildFragmentManager();
    }

    @SuppressLint("NewApi")
    @Override
    public void directRequestPermissions(int requestCode, @NonNull String... perms) {
        getHost().requestPermissions(perms, requestCode);
    }

    @SuppressLint("NewApi")
    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String perm) {
        return getHost().shouldShowRequestPermissionRationale(perm);
    }

    @Override
    public Context getContext() {
        return getHost().getActivity();
    }
}
