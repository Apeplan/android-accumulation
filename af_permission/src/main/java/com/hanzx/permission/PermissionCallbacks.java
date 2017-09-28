package com.hanzx.permission;

import java.util.List;

/**
 * 请求权限结果的回掉接口
 * <p>
 * Created by: Simon
 * Created on: 2017/9/3 14:08
 * Email: hanzx1024@gmail.com
 */

public interface PermissionCallbacks {
    /**
     * 确认授权
     *
     * @param requestCode 请求权限的编码
     * @param perms 请求的权限
     */
    void onPermissionsGranted(int requestCode, List<String> perms);

    /**
     * 拒绝授权
     *
     * @param requestCode 请求权限的编码
     * @param perms 请求的权限
     */
    void onPermissionsDenied(int requestCode, List<String> perms);

    /**
     * 授权之后
     *
     * @param requestCode 请求权限的编码
     * @param perms 请求的权限
     */
    void onAfterPermissionGranted(int requestCode, List<String> perms);
}
