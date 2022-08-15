package com.ronghuaxueleng.model;


public interface PermissionListener {
    void permissionSuccess(int command);

    void permissionFail(int command);
}
