package com.hanzx.utility;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * 网络工具
 * <p>
 * 需要添加网络权限：
 *
 * @code <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * </p>
 * Created by: Hanzhx
 * Created on: 2017/8/26 15:37
 * Email: iHanzhx@gmail.com
 */

public class NetworkUtils {
    /**
     * 无网络
     */
    public static final int NETWORK_NO = -1;   // no network
    /**
     * WIFI连接
     */
    public static final int NETWORK_WIFI = 1;    // wifi network
    /**
     * 4G网
     */
    public static final int NETWORK_4G = 2;    // "4G" networks
    /**
     * 3G网
     */
    public static final int NETWORK_3G = 3;    // "3G" networks
    /**
     * 2G网
     */
    public static final int NETWORK_2G = 4;    // "2G" networks
    /**
     * 未知网络
     */
    public static final int NETWORK_UNKNOWN = 5;    // unknown network
    /**
     * GSM
     */
    private static final int NETWORK_TYPE_GSM = 16;
    /**
     * SCDMA
     */
    private static final int NETWORK_TYPE_TD_SCDMA = 17;
    /**
     * IWLAN
     */
    private static final int NETWORK_TYPE_IWLAN = 18;


    /**
     * 判断网络是否可用
     *
     * @param context 上下文环境
     *
     * @return true 可用
     */
    public static boolean isAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return null != info && info.isAvailable();
    }

    /**
     * 判断网络连接是否可用
     *
     * @param context 上下文环境
     *
     * @return true 可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = getConnectivityManager(context);
        if (null != cm) {
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (null != infos) {
                for (NetworkInfo info : infos) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断网络是否连接
     *
     * @param context 上下文环境
     *
     * @return true 连接
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return null != info && info.isConnected();
    }

    /**
     * 判断WIFI是否打开
     *
     * @param context 上下文环境
     *
     * @return true WIFI可用
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager cm = getConnectivityManager(context);
        TelephonyManager telMgr = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        return ((null != cm.getActiveNetworkInfo() && cm.getActiveNetworkInfo().getState() ==
                NetworkInfo.State.CONNECTED) || telMgr.getNetworkType() == TelephonyManager
                .NETWORK_TYPE_UMTS);
    }

    /**
     * 判断网络连接方式是否为WIFI
     *
     * @param context 上下文环境
     *
     * @return true WIFI 连接
     */
    public static boolean isWIFI(Context context) {
        NetworkInfo networkINfo = getActiveNetworkInfo(context);
        return networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断wifi是否连接状态
     *
     * @param context 上下文环境
     *
     * @return true 连接
     */
    public static boolean isWIFIConnected(Context context) {
        ConnectivityManager cm = getConnectivityManager(context);
        return null != cm && null != cm.getActiveNetworkInfo()
                && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断网络是否是4G
     *
     * @param context 上下文环境
     *
     * @return true 4G网络连接
     */
    public static boolean is4G(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager
                .NETWORK_TYPE_LTE;
    }

    /**
     * 判断是否为3G网络
     *
     * @param context 上下文环境
     *
     * @return true 3G网络连接
     */
    public static boolean is3G(Context context) {
        ConnectivityManager cm = getConnectivityManager(context);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        return null != networkINfo && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * GPS是否打开
     *
     * @param context 上下文环境
     *
     * @return true GPS 已打开
     */
    public static boolean isGPSEnabled(Context context) {
        LocationManager lm = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        List<String> accessibleProviders = lm.getProviders(true);
        return null != accessibleProviders && accessibleProviders.size() > 0;
    }

    /**
     * 获取当前的网络类型(WIFI,2G,3G,4G)
     * <p>
     * NETWORK_WIFI
     * NETWORK_4G
     * NETWORK_3G
     * NETWORK_2G
     * NETWORK_UNKNOWN
     * NETWORK_NO
     * </p>
     *
     * @param context 上下文
     *
     * @return 网络类型名称
     */
    public static String getNetWorkTypeName(Context context) {
        switch (getNetWorkType(context)) {
            case NETWORK_WIFI:
                return "NETWORK_WIFI";
            case NETWORK_4G:
                return "NETWORK_4G";
            case NETWORK_3G:
                return "NETWORK_3G";
            case NETWORK_2G:
                return "NETWORK_2G";
            case NETWORK_NO:
                return "NETWORK_NO";
            default:
                return "NETWORK_UNKNOWN";
        }
    }

    /**
     * 它主要负责的是
     * 1 监视网络连接状态 包括（Wi-Fi, 2G, 3G, 4G）
     * 2 当网络状态改变时发送广播通知
     * 3 网络连接失败尝试连接其他网络
     * 4 提供API，允许应用程序获取可用的网络状态
     * <p>
     * netTyped 的结果
     *
     * @param context 上下文
     *
     * @return 网络类型
     *
     * @link #NETWORK_NO      = -1; 当前无网络连接
     * @link #NETWORK_WIFI    =  1; wifi的情况下
     * @link #NETWORK_2G      =  2; 切换到2G环境下
     * @link #NETWORK_3G      =  3; 切换到3G环境下
     * @link #NETWORK_4G      =  4; 切换到4G环境下
     * @link #NETWORK_UNKNOWN =  5; 未知网络
     */
    public static int getNetWorkType(Context context) {
        // 获取ConnectivityManager
        ConnectivityManager cm = getConnectivityManager(context);

        NetworkInfo ni = cm.getActiveNetworkInfo();// 获取当前网络状态
        int netType = NETWORK_NO;

        if (null != ni && ni.isConnectedOrConnecting()) {

            switch (ni.getType()) {// 获取当前网络的状态
                case ConnectivityManager.TYPE_WIFI:// wifi的情况下
                    netType = NETWORK_WIFI;
                    break;
                case ConnectivityManager.TYPE_MOBILE:

                    switch (ni.getSubtype()) {
                        case NETWORK_TYPE_GSM:
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            netType = NETWORK_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                        case NETWORK_TYPE_TD_SCDMA:
                            netType = NETWORK_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:

                        case NETWORK_TYPE_IWLAN:
                            netType = NETWORK_4G;
                            break;
                        default:
                            String subtypeName = ni.getSubtypeName();
                            if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                    || subtypeName.equalsIgnoreCase("WCDMA")
                                    || subtypeName.equalsIgnoreCase("CDMA2000")) {
                                netType = NETWORK_3G;
                            } else {
                                netType = NETWORK_UNKNOWN;
                            }
                    }
                    break;
                default:
                    netType = 5;
            }

        } else {
            netType = NETWORK_NO;
        }
        return netType;
    }

    /**
     * 获取移动网络运营商名称
     * <p>
     * 如中国联通、中国移动、中国电信
     * </p>
     *
     * @param context 上下文环境
     *
     * @return 运营商名称
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context
                .TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * 获取移动终端类型
     * <p>
     * <p>
     * {@link TelephonyManager#PHONE_TYPE_NONE } : 0 手机制式未知
     * {@link TelephonyManager#PHONE_TYPE_GSM  } : 1 手机制式为GSM，移动和联通
     * {@link TelephonyManager#PHONE_TYPE_CDMA } : 2 手机制式为CDMA，电信
     * {@link TelephonyManager#PHONE_TYPE_SIP  } : 3
     * </p>
     *
     * @param context 上下文环境
     *
     * @return 终端类型 {@link TelephonyManager TYPE}
     */
    public static int getPhoneType(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }

    /**
     * 打开网络设置页面
     *
     * @param context 上下文环境
     */
    public static void openNetworkSettings(Context context) {
        context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
    }

    /**
     * 返回网络活动信息
     *
     * @param context 上下文环境
     *
     * @return 网络信息：NetworkInfo
     */
    private static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * 返回连接管理器
     *
     * @param context 上下文环境
     *
     * @return 连接管理器
     */
    private static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
