package com.hanzx.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;

/**
 * describe:
 *
 * @author Hanzhx
 * @date 2017/9/27 18:00
 * @email iHanzhx@gmail.com
 */
public class IntentUtil {

    /**
     * 从外部存储【SD卡】选择图片
     */
    public static final int PICK_PHOTO_FROM_EXT_STORAGE = 20000;
    /**
     * 相机拍照
     */
    public static final int TAKE_PHOTO_FROM_CAMERA = 20001;
    /**
     * 图片裁剪
     */
    public static final int CROP_PHOTO = 20002;

    public static final int PICK_MUTI_PHOTO_FROM_EXT_STORAGE = 20003;

    public static final int TAKE_PHOTO_FROM_CAMERA_ONLY_ROTATE = 20004;

    public static final int PICK_PHOTO_FROM_EXT_STORAGE_ONLY_ROTATE = 20005;


    public static final String IMAGE_TYPE = "image/*";


    public static final String ACTION_CROP = "com.android.camera.action.CROP";


    public static final String IMAGE_MEDIA = "content://media/external/images/media/";


    private IntentUtil() {

    }

    /**
     * 从某个Activity跳转到另外一个Activity的通用函数
     *
     * @param context 当前所在activity的上下文
     * @param cls 　　　　需要跳转的activity的class
     * @param finishSelf 　　是否结束当前activity自身　true：结束　false：不结束
     * @param bundle 传递到下一个activity的值，没有时传null
     */
    public static void redirect(Context context, Class<?> cls,
            boolean finishSelf, Bundle bundle) {
        redirect(context, null, cls, finishSelf, bundle);
    }

    /**
     * 从某个Activity跳转到另外一个Activity的通用函数, forresult
     *
     * @param context 当前所在activity的上下文
     * @param cls 　　　　需要跳转的activity的class
     * @param finishSelf 　　是否结束当前activity自身　true：结束　false：不结束
     * @param bundle 传递到下一个activity的值，没有时传null
     * @param requestCode 回传code
     */
    public static void redirectForResult(Context context, Class<?> cls,
            boolean finishSelf, Bundle bundle, int requestCode) {
        redirectForResult(context, null, cls, finishSelf, bundle, requestCode);
    }

    /**
     * 从某个Fragment或Activity跳转到另外一个Activity的通用函数, forresult
     *
     * @param context 当前所在activity的上下文
     * @param fragment 当前所在fragment
     * @param cls 　　　　需要跳转的activity的class
     * @param finishSelf 　　是否结束当前activity自身　true：结束　false：不结束
     * @param bundle 传递到下一个activity的值，没有时传null
     * @param requestCode 回传code
     */
    public static void redirectForResult(Context context, Fragment fragment, Class<?> cls,
            boolean finishSelf, Bundle bundle, int requestCode) {
        Intent it = new Intent();
        it.setClass(context, cls);
        if (!(context instanceof Activity)) {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        if (bundle != null) {
            it.putExtras(bundle);
        }

        if (fragment != null) {
            fragment.startActivityForResult(it, requestCode);
        } else if (context instanceof Activity) {
            Activity activity = (Activity) context;
            activity.startActivityForResult(it, requestCode);
        }

        if (context instanceof Activity) {
            if (finishSelf) {
                ((Activity) context).finish();
            }

        }
    }

    /**
     * 从某个Activity或者Fragment跳转到另外一个Activity的通用函数
     *
     * @param context 当前所在activity的上下文
     * @param fragment 当前所在fragment
     * @param cls 　　　　需要跳转的activity的class
     * @param finishSelf 　　是否结束当前activity自身　true：结束　false：不结束
     * @param bundle 传递到下一个activity的值，没有时传null
     */
    public static void redirect(Context context, Fragment fragment, Class<?> cls,
            boolean finishSelf, Bundle bundle) {
        Intent it = new Intent();
        it.setClass(context, cls);
        if (!(context instanceof Activity)) {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        if (bundle != null) {
            it.putExtras(bundle);
        }

        if ((context instanceof Activity) && finishSelf) {
            Activity activity = (Activity) context;
            activity.finish();
        }

        if (fragment != null) {
            fragment.startActivity(it);
        } else {
            context.startActivity(it);
        }
    }

    /**
     * 从存储设备上获取图片
     */
    public static void pickPhotoFromStorage(Context context) {
        pickPhotoFromStorage(context, null, false);
    }

    /**
     * 从存储设备上获取图片
     *
     * @param context 如果fragment为null，则使用((Activity) context).startActivityForResult
     * @param fragment 如果不为null，则使用fragment.startActivityForResult
     */
    public static void pickPhotoFromStorage(Context context, Fragment fragment,
            boolean ifOnlyRotate) {
        Intent intentPick = new Intent(Intent.ACTION_GET_CONTENT);
        intentPick.setType(IntentUtil.IMAGE_TYPE);
        intentPick.addCategory(Intent.CATEGORY_OPENABLE);
        int action = ifOnlyRotate ? PICK_PHOTO_FROM_EXT_STORAGE_ONLY_ROTATE
                                  : PICK_PHOTO_FROM_EXT_STORAGE;
        if (fragment != null) {
            fragment.startActivityForResult(intentPick, action);
        } else {
            ((Activity) context).startActivityForResult(intentPick, action);
        }
    }

    /**
     * 启动照相机拍照
     */
    public static void takePhotoByCamera(Context context, Uri uri) {
        takePhotoByCamera(context, null, uri, false);
    }

    /**
     * 启动照相机拍照
     */
    public static void takePhotoByCamera(Context context, Fragment fragment, Uri uri,
            boolean ifOnlyRotate) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        int action = ifOnlyRotate ? TAKE_PHOTO_FROM_CAMERA_ONLY_ROTATE : TAKE_PHOTO_FROM_CAMERA;
        if (fragment != null) {
            fragment.startActivityForResult(intent, action);
        } else {
            ((Activity) context).startActivityForResult(intent, action);
        }
    }

    /**
     * 启动图片裁剪
     */
    public static void cropPhoto(Context context, File imageFile, String dstFile) {
        cropPhoto(context, null, imageFile, dstFile);
    }

    /**
     * 启动图片裁剪
     */
    public static void cropPhoto(Context context, Fragment fragment, File imageFile,
            String dstFile) {
        Intent intent = new Intent(ACTION_CROP);

        if (imageFile == null) {
            return;
        }

//        //清除缓存或者其他操作会导致父目录被删，从而无法创建该文件
//        FileUtil.parentFolder(dstFile);

        final int x = 500;
        final int y = 500;

        intent.setDataAndType(Uri.fromFile(imageFile), IMAGE_TYPE);
        intent.putExtra("crop", true); // crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("scale", true);
        intent.putExtra("output", Uri.fromFile(new File(dstFile)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString()); //返回格式

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        intent.putExtra("outputX", x);
        intent.putExtra("outputY", y);
        intent.putExtra("return-data", false);

        if (fragment != null) {
            fragment.startActivityForResult(intent, CROP_PHOTO);
        } else {
            ((Activity) context).startActivityForResult(intent, CROP_PHOTO);
        }
    }


    /**
     * 跳至拨号界面
     *
     * @param context 上下文
     * @param phoneNumber 电话号码
     */
    public static void openDial(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 拨打电话
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param context 上下文
     * @param phoneNumber 电话号码
     */
    public static void openCall(Context context, String phoneNumber) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
