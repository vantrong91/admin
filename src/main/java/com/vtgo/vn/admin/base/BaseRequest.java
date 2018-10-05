package com.vtgo.vn.admin.base;
import lombok.Data;
//import org.apache.log4j.Logger;

@Data
public class BaseRequest<T> {

    protected Long userId;

    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long userId)
    {
        this.userId= userId;
    }
}
