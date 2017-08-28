package com.hanzx.framework;

import android.Manifest;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hanzx.mvp.activity.BaseActivity;
import com.hanzx.mvp.permission.PermissionCallback;
import com.hanzx.mvp.utils.L;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindViews() {

    }

    @Override
    protected void initObjs() {
        L.tag("LifeCycles");
        L.d("Activity initObjs");
    }

    @Override
    protected void initData() {

    }

    public void click(View view) {
        Button btn = (Button) view;
        L.i("A button with ID %s was clicked to say '%s'.", btn.getId(), btn.getText());
        Toast.makeText(this, "Check logcat for a greeting!", LENGTH_SHORT).show();
    }

    public void log(View view) {
        Button btn = (Button) view;
        L.i("A button with ID %s was clicked to say '%s'.", btn.getId(), btn.getText());
        Toast.makeText(this, "Check logcat for a greeting!", LENGTH_SHORT).show();
    }

    public void permission(View view) {

        requestPermission(new PermissionCallback() {
            @Override
            public void onBeforeGranted(boolean isShouldShowAlert, String... permissions) {
                Toast.makeText(MainActivity.this, "onBeforeGranted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGranted(@Nullable String... permissions) {
                Toast.makeText(MainActivity.this, "onGradted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(@Nullable String... permissions) {
                Toast.makeText(MainActivity.this, "onRefuse", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission_group.LOCATION);


    }
}
