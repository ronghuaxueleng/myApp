package com.ronghuaxueleng.bean.basebean;

import java.io.Serializable;


public class NetFailBean implements Serializable {
    private String message;

    public NetFailBean(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
