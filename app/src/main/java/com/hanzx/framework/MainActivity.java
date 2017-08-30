package com.hanzx.framework;

import android.Manifest;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hanzx.mvp.activity.BaseActivity;
import com.hanzx.mvp.permission.PermissionCallback;
import com.hanzx.mvp.utils.L;
import com.hanzx.statelayout.StateViewControl2;
import com.hanzx.statelayout.state.StateReplaceHelperImpl;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends BaseActivity {

    private StateViewControl2 mController2;
    private LinearLayout mRoot_view;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindViews() {
        mRoot_view = (LinearLayout) findViewById(R.id.ll_state_view);
    }

    @Override
    protected void initObjs() {
        L.tag("LifeCycles");
        L.d("Activity initObjs");
//        mController2 = new StateViewControl2(mRoot_view);
        mController2 = new StateViewControl2(new StateReplaceHelperImpl(mRoot_view));
    }

    @Override
    protected void initData() {

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
        }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission_group.LOCATION);


    }

    public void empty(View view) {
        mController2.showStateEmpty("", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void net_error(View view) {
        mController2.showStateNetworkError(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void error(View view) {
        mController2.showStateError("", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"重试",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void load(View view) {
        mController2.showStateLoading("");
    }

    public void content(View view) {
        mController2.restore();
    }
}
