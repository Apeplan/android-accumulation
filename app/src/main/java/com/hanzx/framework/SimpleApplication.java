package com.hanzx.framework;

import android.app.Application;
import android.util.Log;

import com.hanzx.utility.L;
import com.hanzx.utility.L.DebugTree;



/**
 * describe:
 *
 * Created by:  iHanzhx
 * Created on: 2017/8/27
 * Email: iHanzhx@gmail.com
 */

public class SimpleApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            L.plant(new DebugTree());
        } else {
            L.plant(new CrashReportingTree());
        }
    }

    /** A tree which logs important information for crash reporting. */
    private static class CrashReportingTree extends L.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            FakeCrashLibrary.log(priority, tag, message);

            if (t != null) {
                if (priority == Log.ERROR) {
                    FakeCrashLibrary.logError(t);
                } else if (priority == Log.WARN) {
                    FakeCrashLibrary.logWarning(t);
                }
            }
        }
    }
}
