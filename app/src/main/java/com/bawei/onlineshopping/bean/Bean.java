package com.bawei.onlineshopping.bean;

/**
 * Time: 2020/2/20
 * Author: 王冠华
 * Description:
 */
public class Bean {

    /**
     * timestamp : 2020-02-20T09:18:56.836+0000
     * status : 400
     * error : Bad Request
     * message : Required String parameter 'phone' is not present
     * path : /small/user/v1/register
     */

    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
//postman
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
