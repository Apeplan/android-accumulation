package com.hanzx.mvp.net;

import java.io.IOException;

/**
 * @author : Hanzx
 * @date : 2017/10/18 17:10
 * @email : iHanzhx@gmail.com
 */

public class ApiException extends IOException {
    /**
     * 错误码
     */
    public int mCode;

    public ApiException(int code, String message) {
        super(message);
        this.mCode = code;
    }
}
