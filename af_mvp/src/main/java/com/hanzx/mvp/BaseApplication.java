package com.hanzx.mvp;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import java.util.List;

/**
 * describe:
 *
 * @author Hanzx
 * @date 2017/8/27
 * @email hanzx1024@gmail.com
 */

public abstract class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initAlways();

        if (isMainProcess()) {
            initOnMainProcess();
        } else {
            initOnOtherProcess(getCurrentProcessName(), android.os.Process.myPid());
        }

    }

    /**
     * 初始化操作，总是会被调用，不区分进程
     */
    protected abstract void initAlways();

    /**
     * 初始化操作，在主进程中会被调用
     */
    protected abstract void initOnMainProcess();

    /**
     * 初始化操作，再非主进程中被调用
     *
     * @param processName 进程名
     * @param pid         进程myPid
     */
    protected abstract void initOnOtherProcess(String processName, int pid);

    /**
     * 判断是不是主进程
     *
     * @return true 表示是主进程
     */
    private boolean isMainProcess() {
        return getPackageName().equals(getCurrentProcessName());
    }

    /**
     * 返回当前进程的名称，如果出错或者操作失败返回 ""
     *
     * @return 进程名称
     */
    protected String getCurrentProcessName() {
        int myPid = Process.myPid();
        ActivityManager mgr = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (null == mgr) {
            return "";
        }
        List<ActivityManager.RunningAppProcessInfo> running = mgr.getRunningAppProcesses();
        if (null == running || running.isEmpty()) {
            return "";
        }

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : running) {
            if (myPid == runningAppProcessInfo.pid) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }
}
