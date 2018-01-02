package com.duanzi.he.duanzi.base;

/**
 * Created by he on 2017/10/31.
 */

public class BaseResult {
    protected int status_code;
    protected String message;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
