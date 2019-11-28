package com.lin.cms.demo.utils;


import com.lin.cms.demo.model.UserDO;

public class LocalUser {

    private static ThreadLocal<UserDO> local = new ThreadLocal<>();

    public static UserDO getLocalUser() {
        return LocalUser.local.get();
    }

    public static void setLocalUser(UserDO user) {
        LocalUser.local.set(user);
    }

    public static <T> T getLocalUser(Class<T> clazz) {
        return (T) local.get();
    }
}
