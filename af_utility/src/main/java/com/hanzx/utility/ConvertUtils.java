package com.hanzx.utility;

import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * @author yexiuliang
 * @page
 * @data 2017-03-20
 */

public class ConvertUtils {
    private ConvertUtils() {
    }

    public static double convert2Double(@Nullable String d) {
        if (TextUtils.isEmpty(d))
            return 0;
        try {
            return Double.parseDouble(d);
        } catch (Exception p) {
            p.printStackTrace();
        }
        return -1;
    }

    public static long convert2Long(@Nullable String d) {
        if (TextUtils.isEmpty(d))
            return 0;
        try {
            return Long.parseLong(d);
        } catch (Exception p) {
            p.printStackTrace();
        }
        return -1;
    }

    public static int convert2Int(@Nullable String d) {
        if (TextUtils.isEmpty(d))
            return -1;
        try {
            return Integer.parseInt(d);
        } catch (Exception p) {
            p.printStackTrace();
        }
        return -1;
    }
}
