package com.wx.utils;

import lombok.Data;

@Data
public class R {
    private boolean flag;
    private String msg;
    private Object data;

    public R(){}

    public R(boolean flag) {
        this.flag = flag;
    }

    public R(boolean flag,String msg,Object data) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }
}
