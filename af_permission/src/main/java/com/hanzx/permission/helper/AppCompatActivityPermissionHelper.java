package com.hanzx.permission.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by: Hanzhx
 * Created on: 2017/9/3 12:15
 * Email: iHanzhx@gmail.com
 */

class AppCompatActivityPermissionHelper extends BaseSupportPermissionsHelper<AppCompatActivity> {

    public AppCompatActivityPermissionHelper(@NonNull AppCompatActivity host) {
        super(host);
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return getHost().getSupportFragmentManager();
    }

   /* @Override
    public FragmentManager getSupportFragmentManager() {
        return getHost().getSupportFragmentManager();
    }*/

    @Override
    public void directRequestPermissions(int requestCode, @NonNull String... perms) {
        ActivityCompat.requestPermissions(getHost(), perms, requestCode);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String perm) {
        return ActivityCompat.shouldShowRequestPermissionRationale(getHost(), perm);
    }

    @Override
    public Context getContext() {
        return getHost();
    }
}
