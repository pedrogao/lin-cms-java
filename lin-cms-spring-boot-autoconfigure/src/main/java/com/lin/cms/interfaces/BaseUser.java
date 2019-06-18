package com.lin.cms.interfaces;

public interface BaseUser {

    boolean ifIsAdmin();

    boolean ifIsActive();

    Integer getGroupId();

    void setPasswordEncrypt(String password);

    boolean verify(String password);
}
