package com.vtgo.vn.admin.base;

import java.io.Serializable;
import java.util.List;

public class BaseResponse<T> implements Serializable {

    protected List<T> data;

    @Override
    public String toString() {
        return "BaseResponse{"
                + "status='" + status + '\''
                + ", message='" + message + '\''
                + '}';
    }

    public int status;
    public String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
