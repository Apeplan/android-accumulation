package com.hanzx.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.hanzx.permission.helper.PermissionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 运行时权限请求
 * <p>
 * Created by: Hanzx
 * Created on: 2017/9/3 11:31
 * Email: hanzhanxi@gmail.com
 */

public class Permissions {
    private static final String TAG = Permissions.class.getSimpleName();

    /**
     * 检查是否已具有权限
     *
     * @param context 上下文
     * @param perms   要判断的权限
     *
     * @return true 已授权所有权限，false 至少有一个权限为被授权
     */
    public static boolean hasPermissions(Context context, @NonNull String... perms) {
        // API 23(Android6.0)以下直接返回true
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.w(TAG, "hasPermissions: API version < M, returning true by default");
            return true;
        }

        if (null == context) {
            throw new IllegalArgumentException("Can't check permissions for null context");
        }

        for (String perm : perms) {
            if (PackageManager.PERMISSION_GRANTED
                    != ContextCompat.checkSelfPermission(context, perm)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 申请权限
     *
     * @param host        请求权限的Context
     * @param rationale   申请权限的理由
     * @param requestCode 权限申请码
     * @param perms       申请的权限
     */
    public static void requestPermissions(@NonNull Activity host,
                                          @NonNull String rationale,
                                          int requestCode,
                                          @NonNull String... perms) {
        requestPermissions(host, rationale, android.R.string.ok, android.R.string.cancel,
                requestCode, perms);
    }

    /**
     * @see #requestPermissions(Activity, String, int, String...)
     */
    public static void requestPermissions(@NonNull Fragment host, @NonNull String rationale,
                                          int requestCode, @NonNull String... perms) {

        requestPermissions(host, rationale, android.R.string.ok, android.R.string.cancel,
                requestCode, perms);
    }

    /**
     * @see #requestPermissions(Activity, String, int, String...)
     */
    public static void requestPermissions(@NonNull android.app.Fragment host,
                                          @NonNull String rationale, int requestCode, @NonNull
                                                  String... perms) {

        requestPermissions(host, rationale, android.R.string.ok, android.R.string.cancel,
                requestCode, perms);
    }

    /**
     * 请求权限
     *
     * @param host           请求权限的Context
     * @param rationale      申请权限的理由
     * @param positiveButton 确定按钮文本String资源id
     * @param negativeButton 取消按钮文本String资源id
     * @param requestCode    权限请求码
     * @param perms          申请的权限
     */
    public static void requestPermissions(@NonNull Activity host,
                                          @NonNull String rationale,
                                          @StringRes int positiveButton,
                                          @StringRes int negativeButton,
                                          int requestCode,
                                          @NonNull String... perms) {
        requestPermissions(PermissionHelper.newInstance(host), rationale,
                positiveButton, negativeButton,
                requestCode, perms);
    }

    /**
     * @see #requestPermissions(Activity, String, int, int, int, String...)
     */
    public static void requestPermissions(@NonNull Fragment host,
                                          @NonNull String rationale,
                                          @StringRes int positiveButton,
                                          @StringRes int negativeButton,
                                          int requestCode,
                                          @NonNull String... perms) {
        requestPermissions(PermissionHelper.newInstance(host), rationale,
                positiveButton, negativeButton,
                requestCode, perms);
    }

    /**
     * @see #requestPermissions(Activity, String, int, int, int, String...)
     */
    public static void requestPermissions(@NonNull android.app.Fragment host,
                                          @NonNull String rationale,
                                          @StringRes int positiveButton,
                                          @StringRes int negativeButton,
                                          int requestCode,
                                          @NonNull String... perms) {
        requestPermissions(PermissionHelper.newInstance(host), rationale,
                positiveButton, negativeButton,
                requestCode, perms);
    }

    /**
     * 处理权限申请结果
     *
     * @param requestCode
     * @param perms
     * @param grantResults
     * @param receivers
     */
    public static void onRequestPermissionsResult(int requestCode,
                                                  @NonNull String[] perms,
                                                  @NonNull int[] grantResults,
                                                  @NonNull Object... receivers) {
        // 创建两个集合，存储申请权限中授权和被拒绝的权限
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < perms.length; i++) {
            String perm = perms[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        for (Object object : receivers) {
            if (object instanceof PermissionCallbacks) {
                // 判断是否有已授权的权限
                if (!granted.isEmpty()) {
                    ((PermissionCallbacks) object).onPermissionsGranted(requestCode, granted);
                }
                // 判断是否有被拒绝的权限
                if (!denied.isEmpty()) {
                    ((PermissionCallbacks) object).onPermissionsDenied(requestCode, granted);
                }
                // 如果全部授权
                if (!granted.isEmpty() && denied.isEmpty()) {
//                    runAnnotatedMethods(object,requestCode);
                    ((PermissionCallbacks) object).onAfterPermissionGranted(requestCode, granted);
                }
            }
        }
    }

    private static void requestPermissions(@NonNull PermissionHelper helper,
                                           @NonNull String rationale,
                                           @StringRes int positiveButton,
                                           @StringRes int negativeButton,
                                           int requestCode,
                                           @NonNull String... perms) {
        // 申请前检查是否已经授权
        if (hasPermissions(helper.getContext(), perms)) {
            notifyAlreadyHasPermissions(helper.getHost(), requestCode, perms);
            return;
        }
        // 去申请权限
        helper.requestPermissions(rationale, positiveButton,
                negativeButton, requestCode, perms);
    }

    /**
     * 已经被请求过的权限
     *
     * @param host        请求权限的对象
     * @param requestCode 权限请求码
     * @param perms       已被请求过的权限集合
     */
    private static void notifyAlreadyHasPermissions(@NonNull Object host,
                                                    int requestCode,
                                                    @NonNull String[] perms) {
        int[] grantResults = new int[perms.length];
        for (int i = 0; i < perms.length; i++) {
            grantResults[i] = PackageManager.PERMISSION_GRANTED;
        }

        onRequestPermissionsResult(requestCode, perms, grantResults, host);
    }

    /**
     * 检查被拒绝的权限列表中是否至少有个一权限选择了不再询问
     *
     * @param host              请求权限的Context
     * @param deniedPermissions 被拒绝的权限
     *
     * @return true 至少有一个权限选择了不再询问
     */
    public static boolean somePermissionPermanentlyDenied(@NonNull Activity host,
                                                          @NonNull List<String> deniedPermissions) {
        return PermissionHelper.newInstance(host)
                .somePermissionPermanentlyDenied(deniedPermissions);
    }

    /**
     * @see #somePermissionPermanentlyDenied(Activity, List)
     */
    public static boolean somePermissionPermanentlyDenied(@NonNull Fragment host,
                                                          @NonNull List<String> deniedPermissions) {
        return PermissionHelper.newInstance(host)
                .somePermissionPermanentlyDenied(deniedPermissions);
    }

    /**
     * @see #somePermissionPermanentlyDenied(Activity, List)
     */
    public static boolean somePermissionPermanentlyDenied(@NonNull android.app.Fragment host,
                                                          @NonNull List<String> deniedPermissions) {
        return PermissionHelper.newInstance(host)
                .somePermissionPermanentlyDenied(deniedPermissions);
    }

    /**
     * 检查权限是否被永久拒绝
     *
     * @param host             请求权限的Context
     * @param deniedPermission 被拒绝的权限
     *
     * @return true 权限被永久拒绝
     */
    public static boolean permissionPermanentlyDenied(@NonNull Activity host,
                                                      @NonNull String deniedPermission) {
        return PermissionHelper.newInstance(host).permissionPermanentlyDenied(deniedPermission);
    }

    /**
     * @see #permissionPermanentlyDenied(Activity, String)
     */
    public static boolean permissionPermanentlyDenied(@NonNull Fragment host,
                                                      @NonNull String deniedPermission) {
        return PermissionHelper.newInstance(host).permissionPermanentlyDenied(deniedPermission);
    }

    /**
     * @see #permissionPermanentlyDenied(Activity, String)
     */
    public static boolean permissionPermanentlyDenied(@NonNull android.app.Fragment host,
                                                      @NonNull String deniedPermission) {
        return PermissionHelper.newInstance(host).permissionPermanentlyDenied(deniedPermission);
    }

    /**
     * 被拒绝的权限中哪些选择了不在询问
     *
     * @param host  请求权限的Context
     * @param perms 权限列表
     *
     * @return true 如果用户先前选择了拒绝，我们应该显示一个申请权限的理由弹框
     */
    public static boolean somePermissionDenied(@NonNull Activity host,
                                               @NonNull String... perms) {
        return PermissionHelper.newInstance(host).somePermissionDenied(perms);
    }

    /**
     * @see #somePermissionDenied(Activity, String...)
     */
    public static boolean somePermissionDenied(@NonNull Fragment host,
                                               @NonNull String... perms) {
        return PermissionHelper.newInstance(host).somePermissionDenied(perms);
    }

    /**
     * @see #somePermissionDenied(Activity, String...)
     */
    public static boolean somePermissionDenied(@NonNull android.app.Fragment host,
                                               @NonNull String... perms) {
        return PermissionHelper.newInstance(host).somePermissionDenied(perms);
    }

    /**
     * 找到所有添加了 {@link AfterPermissionGranted} 注解的方法
     *
     * @param object      包含注解方法的对象
     * @param requestCode 权限请求码
     */
    private static void runAnnotatedMethods(@NonNull Object object, int requestCode) {
        Class clazz = object.getClass();
        if (isUsingAndroidAnnotations(object)) {
            clazz = clazz.getSuperclass();
        }

        while (clazz != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                AfterPermissionGranted ann = method.getAnnotation(AfterPermissionGranted.class);
                if (ann != null) {
                    // Check for annotated methods with matching request code.
                    if (ann.value() == requestCode) {
                        // Method must be void so that we can invoke it
                        if (method.getParameterTypes().length > 0) {
                            throw new RuntimeException(
                                    "Cannot execute method " + method.getName()
                                            + " because it is non-void method and/or has input "
                                            + "parameters.");
                        }

                        try {
                            // Make method accessible if private
                            if (!method.isAccessible()) {
                                method.setAccessible(true);
                            }
                            method.invoke(object);
                        } catch (IllegalAccessException e) {
                            Log.e(TAG, "runDefaultMethod:IllegalAccessException", e);
                        } catch (InvocationTargetException e) {
                            Log.e(TAG, "runDefaultMethod:InvocationTargetException", e);
                        }
                    }
                }
            }

            clazz = clazz.getSuperclass();
        }
    }

    /**
     * 是否使用注解库
     */
    private static boolean isUsingAndroidAnnotations(@NonNull Object object) {
        if (!object.getClass().getSimpleName().endsWith("_")) {
            return false;
        }
        try {
            Class clazz = Class.forName("org.androidannotations.api.view.HasViews");
            return clazz.isInstance(object);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
