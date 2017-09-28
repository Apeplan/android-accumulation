package com.hanzx.framework;

import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hanzx.mvp.activity.BaseActivity;
import com.hanzx.permission.AfterPermissionGranted;
import com.hanzx.permission.PermissionCallbacks;
import com.hanzx.permission.Permissions;
import com.hanzx.permission.dialog.AppSettingsDialog;
import com.hanzx.statelayout.StateViewControl2;
import com.hanzx.statelayout.state.StateReplaceHelperImpl;
import com.hanzx.utility.L;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends BaseActivity implements PermissionCallbacks {
    private static final String[] LOCATION_AND_CONTACTS =
            {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS};
    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private static final String TAG = "MainActivity";
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
        mController2.setBtnText("点我点我");
        mController2.setLoadingTrip("让我再飞一会...");
    }

    @Override
    protected void initData() {

    }

    public void log(View view) {
        Button btn = (Button) view;
        L.i("A button with ID %s was clicked to say '%s'.", btn.getId(), btn.getText());
        Toast.makeText(this, "Check logcat for a greeting!", LENGTH_SHORT).show();
    }

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void permission(View view) {
        locationAndContactsTask();
    }

    //    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void locationAndContactsTask() {
        if (hasLocationAndContactsPermissions()) {
            // Have permissions, do the thing!
            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for both permissions
            Permissions.requestPermissions(this,
                    getString(R.string.rationale_location_contacts),
                    RC_LOCATION_CONTACTS_PERM,
                    LOCATION_AND_CONTACTS);
        }
    }

    private boolean hasLocationAndContactsPermissions() {
        return Permissions.hasPermissions(this, LOCATION_AND_CONTACTS);
    }

    private boolean hasCameraPermission() {
        return Permissions.hasPermissions(this, Manifest.permission.CAMERA);
    }

    private boolean hasSmsPermission() {
        return Permissions.hasPermissions(this, Manifest.permission.READ_SMS);
    }

    public void empty(View view) {
        mController2.showStateEmpty("", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void net_error(View view) {
        mController2.showStateNetException(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void error(View view) {
        mController2.showStateError("", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "重试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void load(View view) {
        mController2.showStateLoading("");
    }

    public void content(View view) {
        mController2.restore();
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (Permissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onAfterPermissionGranted(int requestCode, List<String> perms) {
        if (RC_LOCATION_CONTACTS_PERM == requestCode) {
            locationAndContactsTask();
        }

        if (RC_CAMERA_PERM == requestCode) {
            if (hasCameraPermission()) {
                // Have permission, do the thing!
                Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
            } else {
                // Ask for one permission
                Permissions.requestPermissions(this,
                        getString(R.string.rationale_camera),
                        RC_CAMERA_PERM,
                        Manifest.permission.CAMERA);
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            String yes = getString(R.string.yes);
            String no = getString(R.string.no);

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                    this,
                    getString(R.string.returned_from_app_settings_to_activity,
                            hasCameraPermission() ? yes : no,
                            hasLocationAndContactsPermissions() ? yes : no,
                            hasSmsPermission() ? yes : no),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}
